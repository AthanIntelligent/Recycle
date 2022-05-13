package com.jack.recycle.mapper;

import com.jack.recycle.model.Manufacturers;

import java.util.List;

public interface ManufacturersDao {
    int deleteByPrimaryKey(String uuid);

    int insert(Manufacturers record);

    int insertSelective(Manufacturers record);

    Manufacturers selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Manufacturers record);

    int updateByPrimaryKey(Manufacturers record);

    List<Manufacturers> selectAll(Manufacturers manufacturer);
}