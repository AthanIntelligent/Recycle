package com.jack.recycle.controller;

import com.jack.recycle.model.User;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.MemcachedRunner;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.UserUtils;
import com.zhenzi.sms.ZhenziSmsClient;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MemcachedRunner memcachedRunner;

    HttpSession session=null;

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
        } catch (Exception e) {
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
     * 系统管理员获取所有基站人员 userType=2 包括自己userType=3；基站人员获取自己和系统管理员添加的
     * @param
     * @return
     */
    @GetMapping(value = "/dirStationUserList")
    public Result dirStationUserList() throws Exception {
        List<User> users;
        List<String> ids = new ArrayList<>();
        //如果是系统管理员 就查用户类型是2和3的
        if (UserUtils.ADMIN.equals(UserUtils.getCurrUserInfo().getUuid())) {
            ids.add("2");
            ids.add("3");
            users = userService.dirStationUserList(ids);
        }else {
            // 如果是基站人员 就查自己和系统管理员的uuid
            ids.add(UserUtils.getCurrUserInfo().getUuid());
            ids.add(UserUtils.ADMIN);
            users = userService.dirStationUserListUU(ids);
        }
        return new Result(Response.SC_OK,"success",users);
    }

    @PostMapping(value = "/sendPhoneMessage")
    public String getVerifyCode(@RequestBody String phone, HttpServletRequest request) {

        try {
            phone = phone.substring(0,11);
            System.out.println("phone:"+phone);
            //生成6位验证码
            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
//            map.put("phone",phone);
//            map.put("message","您的验证码为:" + verifyCode + "，该码有效期为5分钟，该码只能使用一次！");
            //发送短信

            ZhenziSmsClient client = new ZhenziSmsClient("https://sms_developer.zhenzikj.com", "108428", "cad8b700-7c40-4760-b584-9a6020a5afe3");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("number", phone);
            params.put("templateId","4175");
            String[] templateParams = new String[2];
            templateParams[0] = verifyCode;
            templateParams[1] = "5分钟";
            params.put("templateParams", templateParams);
            String result = client.send(params);
            //将验证码存到session中,同时存入创建时间
            //以json存放，这里使用的是阿里的fastjson
            session = request.getSession();
            Map<String,String> map=new HashMap<>();
            map.put("Code", verifyCode);
            map.put("createTime", System.currentTimeMillis()+"");
            // 将认证码存入SESSION
            session.setAttribute("verifyCode", map);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
    @PostMapping("/verifyPhoneCode")
    public String loginCheck(@RequestBody String verifyCode,HttpServletRequest request){
        //用户输入的验证码的值
        verifyCode = verifyCode.substring(0,6);
        Map<String,String> kaptchaExpected = (Map<String,String>) session.getAttribute(
                "verifyCode");
        System.out.println(kaptchaExpected.get("Code"));
        System.out.println("kapchaReceived="+verifyCode);
        //校验验证码是否正确
        if (verifyCode == null || !verifyCode.equals(kaptchaExpected.get("Code"))) {
            return "kaptcha_error";//返回验证码错误
        }
        return "success"; //校验通过返回成功
    }
}
