package com.jack.recycle.shiro;

import com.jack.recycle.mapper.AuthDao;
import com.jack.recycle.mapper.RoleDao;
import com.jack.recycle.mapper.UserDao;
import com.jack.recycle.model.Role;
import com.jack.recycle.model.User;
import org.apache.shiro.authc.Account;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

public class AccountService {
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    AuthDao authDao;

    public Collection<String> getPermission(String loginName) {
        return null;
    }

    public String getRole(String loginName) {
        User user = userDao.selectByUserName(loginName);
        Role role = roleDao.selectByPrimaryKey(user.getUserType());
        return role.getRoleName();
    }

    public User getUser(String username) {
        User user = userDao.selectByUserName(username);
        return user;
    }
}
