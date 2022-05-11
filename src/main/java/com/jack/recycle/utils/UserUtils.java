package com.jack.recycle.utils;

import com.jack.recycle.mapper.AuthDao;
import com.jack.recycle.mapper.UserDao;
import com.jack.recycle.model.Auth;
import com.jack.recycle.model.User;
import org.apache.catalina.connector.Response;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;


public class UserUtils {

    private static MemcachedRunner memcachedRunner = BeanUtils.getBean(MemcachedRunner.class);

    private static UserDao userDao = BeanUtils.getBean(UserDao.class);

    private static AuthDao authDao = BeanUtils.getBean(AuthDao.class);
    /**
     * 系统管理员uuid
     */
    public static final String ADMIN = "1";

    /**
     * 获取当前用户
     */
    public static User getCurrUserInfo() throws Exception {
        String userId = (String) memcachedRunner.getClient().get("userId");
        if("".equals(userId) || userId == null){
            throw new Exception("token过期");
        }
        User user = userDao.selectByPrimaryKey(userId);
        return user;
    }

    /**
     * 获取当前用户的权限
     */
    public static String getCurrUserInfoPermissions() throws Exception {
        String authStr = "";
        User currUserInfo = getCurrUserInfo();
        if(currUserInfo == null){
            return "/login";
        }
        List<Auth> auth = authDao.selectByUserType(Integer.valueOf(currUserInfo.getUserType()));
        if(auth.size() > 0){
            for(int i=0;i<auth.size();i++){
                if(i == auth.size()-1){
                    authStr += auth.get(i).getAuthName();
                }else {
                    authStr += auth.get(i).getAuthName()+",";
                }
            }
        }
        return authStr;
    }
}
