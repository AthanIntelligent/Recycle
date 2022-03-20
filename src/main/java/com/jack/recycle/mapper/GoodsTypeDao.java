package com.jack.recycle.mapper;

import com.jack.recycle.model.GoodsType;

public interface GoodsTypeDao {
    int deleteByPrimaryKey(Integer uuid);

    int insert(GoodsType record);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(Integer uuid);

    int updateByPrimaryKeySelective(GoodsType record);

    int updateByPrimaryKey(GoodsType record);
}