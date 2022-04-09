package com.jack.recycle.utils;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.jack.recycle.mapper.AuthDao;
import com.jack.recycle.mapper.UserDao;
import com.jack.recycle.model.Auth;
import com.jack.recycle.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


public class UserUtils {

    private static MemcachedRunner memcachedRunner = BeanUtils.getBean(MemcachedRunner.class);

    private static UserDao userDao = BeanUtils.getBean(UserDao.class);

    private static AuthDao authDao = BeanUtils.getBean(AuthDao.class);

    /**
     * 获取当前用户
     */
    public static User getCurrUserInfo(){
        String userId = (String) memcachedRunner.getClient().get("userId");
        User user = userDao.selectByPrimaryKey(userId);
        return user;
    }

    /**
     * 获取当前用户的权限
     */
    public static String getCurrUserInfoPermissions() throws IOException {
        String authStr = "";
        User currUserInfo = getCurrUserInfo();
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
