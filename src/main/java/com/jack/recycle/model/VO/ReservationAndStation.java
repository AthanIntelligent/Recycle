package com.jack.recycle.model.VO;


import com.jack.recycle.model.Reservation;
import lombok.Data;

@Data
public class ReservationAndStation {
    private Reservation reservation;
    private String stationLegal;
    private String stationId;
}
