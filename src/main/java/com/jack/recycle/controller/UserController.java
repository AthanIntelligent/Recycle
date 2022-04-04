package com.jack.recycle.controller;

import com.jack.recycle.model.User;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.Result;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查看用户详情
     * @param uuid
     * @return
     */
    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo(String uuid) {
        return new Result(Response.SC_OK,"success",userService.getUserInfoById(uuid));
    }

    /**
     * 分页获取用户信息
     * @param currPage
     * @param pageSize
     * @param map
     * @return
     */
    @GetMapping(value = "/dirUserInfo")
    public Result dirUserInfo(Integer currPage, Integer pageSize, Map<String,Object> map){
        if(currPage == null || currPage <= 0){
            currPage = 1;
        }
        if(pageSize == null || pageSize <= 0){
            pageSize = 10;
        }
        return new Result(Response.SC_OK,"success",userService.dirUserInfo(currPage,pageSize,map));
    }

    /**
     * 修改用户资料
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/updUserInfo")
    public Result updUserInfo(User user) throws Exception {
        String userId = user.getUuid();
        User userInfo = userService.getUserInfoById(userId);
        if(!checkField(userInfo,user))
        {
            int d = userService.updUserInfo(user);
            if(d<=0){
                throw new Exception("修改失败,服务器异常");
            }
        }else {
            return new Result(Response.SC_OK,"请填写修改信息",user);
        }
        return new Result(Response.SC_OK,"修改成功");
    }

    /**
     * 删除用户信息
     * @param uuid
     * @return
     */
    @GetMapping(value = "/delUserInfo")
    public Result deleteUserInfo(String uuid) throws Exception {
       int d = userService.deleteUserInfo(uuid);
       if(d <= 0){
           throw new Exception("删除失败,服务器异常");
       }
       return new Result(Response.SC_OK,"删除成功");
    }

    private boolean checkField(User userInfo, User user) {
        if(user.toString().equals(userInfo.toString())){
            return true;
        }
        return false;
    }
}
