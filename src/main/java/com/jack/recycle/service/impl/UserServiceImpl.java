package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.UserDao;
import com.jack.recycle.model.User;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getLoginUser(String loginName) {
        return userDao.selectByUserName(loginName);
    }

    @Override
    public User getUserInfoById(String id) {
        return userDao.selectByPrimaryKey(id);
    }

    @Override
    public List<User> dirUserInfo(Integer currPage, Integer pageSize, Map<String, Object> map) {
        map.put("currPage",currPage);
        map.put("pageSize",pageSize);
        return userDao.dirUserInfo(map);
    }

    @Override
    public int updUserInfo(User user) {
        return userDao.updateByPrimaryKeySelective(user);
    }

    @Override
    public int deleteUserInfo(String uuid) {
        return userDao.deleteByPrimaryKey(uuid);
    }
}
