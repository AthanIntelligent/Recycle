package com.jack.recycle.mapper;

import com.jack.recycle.model.Goods;

import java.util.List;


public interface GoodsDao {
    int deleteByPrimaryKey(String uuid);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKeyWithBLOBs(Goods record);

    int updateByPrimaryKey(Goods record);

    List<Goods> selectAll();

    List<Goods> selectByIds(List<String> ids);
}