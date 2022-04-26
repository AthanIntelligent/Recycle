package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.TypeOfStationDao;
import com.jack.recycle.model.TypeOfStation;
import com.jack.recycle.service.TypeOfStationService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class TypeOfStationServiceImpl implements TypeOfStationService {
    @Autowired
    TypeOfStationDao typeOfStationDao;

    @Override
    public int addTypeOfStation(TypeOfStation typeOfStation) {
        int insert = typeOfStationDao.insert(typeOfStation);
        return insert;
    }
}
