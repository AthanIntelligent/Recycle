package com.jack.recycle.mapper;

import com.jack.recycle.model.RecycleItemRecord;

public interface RecycleItemRecordDao {
    int deleteByPrimaryKey(String uuid);

    int insert(RecycleItemRecord record);

    int insertSelective(RecycleItemRecord record);

    RecycleItemRecord selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(RecycleItemRecord record);

    int updateByPrimaryKey(RecycleItemRecord record);
}