package com.jack.recycle.mapper;

import com.jack.recycle.model.Station;

public interface StationDao {
    int deleteByPrimaryKey(Integer uuid);

    int insert(Station record);

    int insertSelective(Station record);

    Station selectByPrimaryKey(Integer uuid);

    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKey(Station record);
}