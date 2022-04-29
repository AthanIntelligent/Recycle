package com.jack.recycle.controller;

import com.jack.recycle.model.Reservation;
import com.jack.recycle.service.ReservationService;
import com.jack.recycle.utils.DateUtils;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import com.jack.recycle.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping(value = "/addReservation")
    public Result addReservation(@RequestBody Reservation reservation){
        reservation.setUuid(UUID.randomUUID().toString());
        reservation.setAppointmentHolder(UserUtils.getCurrUserInfo().getUuid());
        reservation.setCreateTime(DateUtils.getFormatDate("yyyy-MM-dd hh:mm"));
        reservation.setIsCome("已预约");
        return new Result(StatusCode.OK, "OK", reservationService.addReservation(reservation));
    }

    @PostMapping(value = "/dirReservation")
    public Result dirReservation(@RequestBody Reservation reservation){
        List<Reservation> reservations = reservationService.dirReservation(reservation);
        //需要循环list把预约人id等id转换成真实姓名
        return new Result(StatusCode.OK, "OK", reservations);
    }
}
