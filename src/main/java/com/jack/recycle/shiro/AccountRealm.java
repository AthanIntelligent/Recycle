package com.jack.recycle.shiro;
import com.jack.recycle.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class AccountRealm extends AuthorizingRealm{

    @Autowired
    private AccountService accountService;

    /**
     * 授权
     * @param pc
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
        //只接受主体是User的，防止和用户端混淆权限
        if (!(pc.getPrimaryPrincipal() instanceof User)){
            return null;
        }
        //获取认证的主体，也就是认证返回的信息
        User user = (User) pc.getPrimaryPrincipal();
        //创建一个存储权限信息的类
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加权限
        if (!CollectionUtils.isEmpty(accountService.getPermission(user.getLoginName()))){
            info.addStringPermissions(accountService.getPermission(user.getLoginName()));
        }
        //添加角色
        String roleName = accountService.getRole(user.getLoginName());
        if (!"".equals(roleName)){
            info.addRole(roleName);
        }
        return info;
    }


    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        //视传入token而定，这里传入的是UsernamePasswordToken
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        //获取用户的逻辑
        User user = accountService.getUser(userToken.getUsername());
        //用户不存在，抛出异常
        if (user == null)throw new AuthenticationException("用户不存在");
        //密码错误，抛出异常
        if (!user.getPassword().equals(userToken.getPassword()))throw new AuthenticationException("密码错误");
        //认证信息类
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        //设置用户主体，像是授权、获取登录用户信息的对象就是在这里传入的
        info.setPrincipals(new SimplePrincipalCollection(user,getName()));
        //这里传入密码是为了修改密码后认证信息失效
        info.setCredentials(user.getPassword());
        return info;
    }
}