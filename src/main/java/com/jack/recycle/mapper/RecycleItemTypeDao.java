package com.jack.recycle.mapper;

import com.jack.recycle.model.RecycleItemType;

public interface RecycleItemTypeDao {
    int deleteByPrimaryKey(String uuid);

    int insert(RecycleItemType record);

    int insertSelective(RecycleItemType record);

    RecycleItemType selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(RecycleItemType record);

    int updateByPrimaryKey(RecycleItemType record);
}