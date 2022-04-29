package com.jack.recycle.service.impl;

import com.jack.recycle.mapper.ReservationDao;
import com.jack.recycle.model.Reservation;
import com.jack.recycle.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationDao reservationDao;

    @Override
    public int addReservation(Reservation reservation) {
        int insert = reservationDao.insert(reservation);
        return insert;
    }

    @Override
    public List<Reservation> dirReservation(Reservation reservation) {
        List<Reservation> reservations = reservationDao.selectAll(reservation);
        return reservations;
    }
}
