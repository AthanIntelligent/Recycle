package com.jack.recycle.mapper;

import com.jack.recycle.model.Goods;

import java.util.List;


public interface GoodsDao {
    int deleteByPrimaryKey(String uuid);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(String uuid);

    Goods selectByName(String name);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> selectAll(Goods record);

    List<Goods> selectByIds(List<String> ids);

}