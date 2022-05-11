package com.jack.recycle.controller;

import com.jack.recycle.model.User;
import com.jack.recycle.service.LoginService;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.UserUtils;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    /**
     * 注册
     * @param user
     * @return Result
     */
    @PostMapping(value = "/register")
    public Result regist(@RequestBody User user){
        if(user.getLoginName().equals("")
            || user.getPassword().equals("")
            || user.getRealName().equals("")
            || user.getMobile().equals(""))
        {
            return new Result(Response.SC_BAD_REQUEST,"必填项不能为空");
        }
        if(!user.getAddress().equals("") && user.getAddress().split(",")[0].length()==0 && user.getAddress().split(",")[1].length() > 0 ){
            return new Result(Response.SC_BAD_REQUEST,"地址信息不完整");
        }
        user.setUuid(UUID.randomUUID().toString());
        return loginService.regist(user);
    }

    /**
     * 登录
     * @param user
     * @return Result
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody User user){
        return loginService.login(user);
    }

    /**
     * 登出
     * @return
     */
    @PostMapping(value = "/logout")
    public Result loginOut() throws Exception {
        //删除token
        User currUserInfo = UserUtils.getCurrUserInfo();
        if(currUserInfo == null){
            return new Result(Response.SC_CONFLICT,"请重新登录");
        }
        int d = loginService.logout(currUserInfo.getLoginName());
        if(d > 0){
            return new Result(Response.SC_OK,"退出成功");
        }else {
            return new Result(Response.SC_INTERNAL_SERVER_ERROR,"服务器报错");
        }
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
