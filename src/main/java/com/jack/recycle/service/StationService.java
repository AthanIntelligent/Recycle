package com.jack.recycle.service;

import com.jack.recycle.model.Station;
import com.jack.recycle.model.User;
import com.jack.recycle.model.VO.StationAndUser;

import java.util.List;

public interface StationService {
    StationAndUser getStationDetail(String uuid);

    int joinBaseStation(Station station);

    Station getStationDetailByUserId(String uuid);

    int openBaseStation(String uuid);

    int cancelBaseStation(String uuid);

    int reBaseStation(String uuid);

    int delBaseStation(String uuid);

    List<Station> dirStation(Station station);

    User getStationLegal(String stationUuid);

    int updStation(Station station);

    int addStation(Station station);

    Station getStationStatus(String userId);

    int updateJoinStationActive(Station station);

    List<Station> dirStationTop10(Station station);
}
