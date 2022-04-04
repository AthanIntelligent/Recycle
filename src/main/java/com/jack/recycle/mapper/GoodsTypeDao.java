package com.jack.recycle.mapper;

import com.jack.recycle.model.GoodsType;

import java.util.List;

public interface GoodsTypeDao {
    int deleteByPrimaryKey(String uuid);

    int insert(GoodsType record);

    int insertSelective(GoodsType record);

    GoodsType selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(GoodsType record);

    int updateByPrimaryKey(GoodsType record);

    List<GoodsType> selectAll();
}