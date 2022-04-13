package com.jack.recycle.service;

import com.jack.recycle.model.User;
import com.jack.recycle.utils.Result;

import java.util.List;
import java.util.Map;


public interface UserService {
    User getLoginUser(String loginName);

    User getUserInfoById(String id);

    int updUserInfo(User user);

    int deleteUserInfo(String uuid);

    List<User> dirUserInfo(User user);
}
