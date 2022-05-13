package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.ManufacturersDao;
import com.jack.recycle.model.Manufacturers;
import com.jack.recycle.service.ManufacturersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ManufacturersServiceImpl implements ManufacturersService {
    @Autowired
    ManufacturersDao manufacturersDao;
    @Override
    public int addManufacturer(Manufacturers manufacturer) {
        return manufacturersDao.insert(manufacturer);
    }

    @Override
    public int removeManufacturer(String uuid) {
        return manufacturersDao.deleteByPrimaryKey(uuid);
    }

    @Override
    public Manufacturers getManufacturer(String uuid) {
        return manufacturersDao.selectByPrimaryKey(uuid);
    }

    @Override
    public List<Manufacturers> dirManufacturer(Manufacturers manufacturer) {
        return manufacturersDao.selectAll(manufacturer);
    }
}
