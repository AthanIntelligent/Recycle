package com.jack.recycle.mapper;

import com.jack.recycle.model.StationRecycleType;

public interface StationRecycleTypeDao {
    int deleteByPrimaryKey(String uuid);

    int insert(StationRecycleType record);

    int insertSelective(StationRecycleType record);

    StationRecycleType selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(StationRecycleType record);

    int updateByPrimaryKey(StationRecycleType record);
}