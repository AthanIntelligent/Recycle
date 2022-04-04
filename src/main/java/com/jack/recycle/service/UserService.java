package com.jack.recycle.service;

import com.jack.recycle.model.User;
import com.jack.recycle.utils.Result;

import java.util.List;
import java.util.Map;


public interface UserService {
    User getLoginUser(String loginName);

    User getUserInfoById(String id);

    List<User> dirUserInfo(Integer currPage, Integer pageSize, Map<String, Object> map);

    int updUserInfo(User user);

    int deleteUserInfo(String uuid);
}
