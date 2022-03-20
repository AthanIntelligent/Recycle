package com.jack.recycle.mapper;

import com.jack.recycle.model.User;

public interface UserDao {
    int deleteByPrimaryKey(Integer uuid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uuid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}