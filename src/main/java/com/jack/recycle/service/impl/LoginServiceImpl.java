package com.jack.recycle.service.impl;

import com.jack.recycle.model.User;
import com.jack.recycle.service.LoginService;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.Result;
import org.apache.catalina.connector.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserService userService;

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
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(),user.getPassword());
        try{
            subject.login(token);
        }catch (AuthenticationException e){
            return new Result(Response.SC_BAD_GATEWAY,e.getMessage());
        }
        return new Result(Response.SC_OK,"成功",userService.getLoginUser(user.getLoginName()));
    }

    @Override
    public Result regist(User user) {
        return null;
    }
}
