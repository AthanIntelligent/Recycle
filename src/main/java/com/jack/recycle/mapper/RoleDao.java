package com.jack.recycle.mapper;

import com.jack.recycle.model.Role;

public interface RoleDao {
    int deleteByPrimaryKey(Integer uuid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer uuid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}