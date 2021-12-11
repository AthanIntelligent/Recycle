package com.jack.recycle.mapper;

import generate.RecycleItemRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecycleItemRecordDao {
    int deleteByPrimaryKey(String uuid);

    int insert(RecycleItemRecord record);

    int insertSelective(RecycleItemRecord record);

    RecycleItemRecord selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(RecycleItemRecord record);

    int updateByPrimaryKey(RecycleItemRecord record);
}