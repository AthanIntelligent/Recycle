package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.UserDao;
import com.jack.recycle.model.User;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getLoginUser(String loginName) {
        return userDao.selectByUserName(loginName);
    }
}
