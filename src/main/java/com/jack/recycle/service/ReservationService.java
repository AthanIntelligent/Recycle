package com.jack.recycle.service;

import com.jack.recycle.model.Reservation;

import java.util.List;

public interface ReservationService {
    int addReservation(Reservation reservation);

    List<Reservation> dirReservation(Reservation reservation);
}
