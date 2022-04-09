package com.jack.recycle.mapper;

import com.jack.recycle.model.Auth;

import java.util.List;

public interface AuthDao {
    int deleteByPrimaryKey(Integer uuid);

    int insert(Auth record);

    int insertSelective(Auth record);

    Auth selectByPrimaryKey(Integer uuid);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Auth> selectByUserType(Integer userType);
}