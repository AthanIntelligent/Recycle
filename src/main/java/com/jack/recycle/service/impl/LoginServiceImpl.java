package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.UserDao;
import com.jack.recycle.model.User;
import com.jack.recycle.service.LoginService;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.MemcachedRunner;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.TokenUtils;
import org.apache.catalina.connector.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    private MemcachedRunner memcachedRunner;

    @Override
    public Result login(User user) {
        Subject subject = SecurityUtils.getSubject();
        if (!StringUtils.hasText(user.getLoginName())){
            return new Result(Response.SC_OK,"用户名不能为空");
        }
        if (!StringUtils.hasText(user.getPassword())){
            return new Result(Response.SC_OK,"密码不能为空");
        }
        //创建一个用户名密码令牌
        UsernamePasswordToken token2 = new UsernamePasswordToken(user.getLoginName(),user.getPassword());
        try{
            subject.login(token2);
        }catch (AuthenticationException e){
            return new Result(Response.SC_BAD_GATEWAY,e.getMessage());
        }
        //生成token
        String token = TokenUtils.token(user.getLoginName(), user.getPassword());
        userDao.updateToken(token,user.getLoginName());
        User loginUser = userService.getLoginUser(user.getLoginName());
        memcachedRunner.getClient().set("userId",3000,loginUser.getUuid());
        return new Result(Response.SC_OK,"成功",loginUser);
    }

    @Override
    public Result regist(User user) {
        User user1 = userDao.selectByUserName(user.getLoginName());
        if(user1 != null){
            return new Result(Response.SC_BAD_REQUEST,"用户已存在,请直接登录");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:dd");
        user.setCreateTime(simpleDateFormat.format(new Date()));
        //基站人员
        if(user.getUserType().equals("2")){
            user.setOpenFlag(2);
        }else {
            user.setOpenFlag(1);
        }
        int d = userDao.insert(user);
        if(d > 0){
            return new Result(Response.SC_OK,"注册成功");
        }else {
            return new Result(Response.SC_INTERNAL_SERVER_ERROR,"注册失败");
        }
    }

    @Override
    public int logout(String loginName) {
       return userDao.updateToken(null,loginName);
    }
}
