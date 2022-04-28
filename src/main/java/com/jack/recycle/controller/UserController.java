package com.jack.recycle.controller;

import com.jack.recycle.model.User;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.MemcachedRunner;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.UserUtils;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MemcachedRunner memcachedRunner;

    /**
     * 查看用户详情
     * @param
     * @return
     */
    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo() {
        Map<String,Object> map = new HashMap<>();
        String userId = (String) memcachedRunner.getClient().get("userId");
        map.put("userInfo",userService.getUserInfoById(userId));
        try {
            map.put("permissions",UserUtils.getCurrUserInfoPermissions());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(Response.SC_OK,"success",map);
    }

    /**
     * 分页获取用户信息
     * @param user
     * @return
     */
    @PostMapping(value = "/dirUserInfo")
    public Result dirUserInfo(@RequestBody User user){
        List<User> users = userService.dirUserInfo(user);
        return new Result(Response.SC_OK,"success",users);
    }

    /**
     * 修改用户资料
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/updUserInfo")
    public Result updUserInfo(@RequestBody User user) throws Exception {
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

    /**
     * 系统管理员获取所有基站人员 userType=2 包括自己userType=3
     * @param
     * @return
     */
    @GetMapping(value = "/dirStationUserList")
    public Result dirStationUserList(){
        List<String> ids = new ArrayList<>();
        ids.add("2");
        ids.add("3");
        List<User> users = userService.dirStationUserList(ids);
        return new Result(Response.SC_OK,"success",users);
    }
}
