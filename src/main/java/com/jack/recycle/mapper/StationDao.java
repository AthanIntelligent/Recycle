package com.jack.recycle.mapper;

import com.jack.recycle.model.Station;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StationDao {
    int deleteByPrimaryKey(String uuid);

    int insert(Station record);

    int insertSelective(Station record);

    Station selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKey(Station record);
}