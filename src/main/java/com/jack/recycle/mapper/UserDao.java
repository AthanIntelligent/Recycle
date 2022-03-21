package com.jack.recycle.mapper;

import com.jack.recycle.model.User;
import org.apache.ibatis.annotations.Select;


public interface UserDao {
    int deleteByPrimaryKey(String uuid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserName(String username);
}