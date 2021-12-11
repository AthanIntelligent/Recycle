package com.jack.recycle.mapper;

import com.jack.recycle.model.StationIncome;

public interface StationIncomeDao {
    int deleteByPrimaryKey(String uuid);

    int insert(StationIncome record);

    int insertSelective(StationIncome record);

    StationIncome selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(StationIncome record);

    int updateByPrimaryKey(StationIncome record);
}