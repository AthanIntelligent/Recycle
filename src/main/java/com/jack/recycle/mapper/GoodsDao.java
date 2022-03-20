package com.jack.recycle.mapper;

import com.jack.recycle.model.Goods;

public interface GoodsDao {
    int deleteByPrimaryKey(Integer uuid);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer uuid);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
}