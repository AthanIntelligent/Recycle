package com.jack.recycle.controller;

import com.jack.recycle.model.User;
import com.jack.recycle.service.LoginService;
import com.jack.recycle.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recycle/api")
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * 注册
     * @param user
     * @return Result
     */
    @PostMapping(value = "/regist")
    public Result regist(User user){
        return loginService.regist(user);
    }

    /**
     * 登录
     * @param user
     * @return Result
     */
    @PostMapping(value = "/login")
    public Result login(User user){
        return loginService.login(user);
    }

    /**
     * 模拟登录页面
     * @return 结果信息
     */
    @GetMapping("loginView")
    public String loginView(){
        return "请登录";
    }

    /**
     * 权限不足
     * @return
     */
    @GetMapping("unauthorized")
    public String unauthorized(){
        return "权限不足";
    }
}
