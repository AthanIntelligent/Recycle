package com.jack.recycle.service;

import com.jack.recycle.model.Station;
import com.jack.recycle.model.VO.StationAndUser;

public interface StationService {
    StationAndUser getStationDetail(String uuid);

    int joinBaseStation(Station station);

    Station getStationDetailByUserId(String stationLegal);

    int openBaseStation(String uuid);

    int cancelBaseStation(String uuid);

    int reBaseStation(String uuid);

    int delBaseStation(String uuid);
}
