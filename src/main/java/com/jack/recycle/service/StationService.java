package com.jack.recycle.service;

import com.jack.recycle.model.Station;
import com.jack.recycle.model.User;
import com.jack.recycle.model.VO.StationAndUser;

import java.util.List;

public interface StationService {
    StationAndUser getStationDetail(String uuid);

    int joinBaseStation(Station station);

    Station getStationDetailByUserId(String stationLegal);

    int openBaseStation(String uuid);

    int cancelBaseStation(String uuid);

    int reBaseStation(String uuid);

    int delBaseStation(String uuid);

    List<Station> dirStation(Station station);

    User getStationLegal(String stationUuid);
}
