package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.StationDao;
import com.jack.recycle.mapper.UserDao;
import com.jack.recycle.model.Station;
import com.jack.recycle.model.User;
import com.jack.recycle.model.VO.StationAndUser;
import com.jack.recycle.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {
    @Autowired
    StationDao stationDao;
    @Autowired
    UserDao userDao;

    /**
     * 查询基站详情
     * @param uuid
     * @return
     */
    @Override
    public StationAndUser getStationDetail(String uuid) {
        StationAndUser stationAndUser = new StationAndUser();
        Station station = stationDao.selectByPrimaryKey(uuid);
        stationAndUser.setStation(station);
        User user = userDao.selectByPrimaryKey(station.getStationLegal());
        stationAndUser.setUser(user);
        return stationAndUser;
    }

    /**
     * 提交入驻基站资料
     * @param station
     * @return
     */
    @Override
    public int joinBaseStation(Station station) {
        return stationDao.insert(station);
    }

    @Override
    public Station getStationDetailByUserId(String stationLegal) {
        return stationDao.selectByStationLegal(stationLegal);
    }

    @Override
    public int openBaseStation(String uuid) {
        return stationDao.openBaseStation(uuid);
    }

    @Override
    public int cancelBaseStation(String uuid) {
        return stationDao.deleteByPrimaryKey(uuid);
    }

    @Override
    public int reBaseStation(String uuid) {
        return stationDao.reBaseStation(uuid);
    }

    @Override
    public int delBaseStation(String uuid) {
        return stationDao.deleteByPrimaryKey(uuid);
    }

    @Override
    public List<Station> dirStation(Station station) {
        return stationDao.selectAllStation(station);
    }

    @Override
    public User getStationLegal(String userId) {
        User user = userDao.selectByPrimaryKey(userId);
        return user;
    }

    @Override
    public int updStation(Station station) {
        int i = stationDao.updateByPrimaryKeySelective(station);
        return i;
    }

    @Override
    public int addStation(Station station) {
        int insert = stationDao.insert(station);
        return insert;
    }

    @Override
    public Station getStationStatus(String userId) {
        return stationDao.selectByStationLegal(userId);
    }

    @Override
    public int updateJoinStationActive(Station station) {
        return stationDao.updateJoinStationActive(station);
    }

    @Override
    public List<Station> dirStationTop10(Station station) {
        return stationDao.selectTop10Station(station);
    }


}
