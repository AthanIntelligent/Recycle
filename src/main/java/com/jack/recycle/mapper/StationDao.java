package com.jack.recycle.mapper;

import com.jack.recycle.model.Station;

public interface StationDao {
    int deleteByPrimaryKey(String uuid);

    int insert(Station record);

    int insertSelective(Station record);

    Station selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(Station record);

    int updateByPrimaryKeyWithBLOBs(Station record);

    int updateByPrimaryKey(Station record);

    Station selectByStationLegal(String stationLegal);

    int openBaseStation(String uuid);

    int reBaseStation(String uuid);
}