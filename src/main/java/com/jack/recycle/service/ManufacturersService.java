package com.jack.recycle.service;

import com.jack.recycle.model.Manufacturers;

import java.util.List;

public interface ManufacturersService {
    int addManufacturer(Manufacturers manufacturer);
    int removeManufacturer(String uuid);
    Manufacturers getManufacturer(String uuid);
    List<Manufacturers> dirManufacturer(Manufacturers manufacturer);
}
