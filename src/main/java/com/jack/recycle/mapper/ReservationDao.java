package com.jack.recycle.mapper;

import com.jack.recycle.model.Reservation;

import java.util.List;

public interface ReservationDao {
    int insert(Reservation reservation);
    List<Reservation> selectAll(Reservation reservation);
}
