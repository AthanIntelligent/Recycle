package com.jack.recycle.controller;

import com.jack.recycle.model.Reservation;
import com.jack.recycle.model.VO.ReservationAndStation;
import com.jack.recycle.service.ReservationService;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.DateUtils;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import com.jack.recycle.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/addReservation")
    public Result addReservation(@RequestBody ReservationAndStation reservationAndStation){
        Reservation reservation = reservationAndStation.getReservation();
        //判断当前预约时间的人数是否超过10个，若超过提示"该时间段预约认输已满，请选择其他时间"
        Reservation reservationValid = new Reservation();
        reservationValid.setDay(reservation.getDay());
        reservationValid.setTime(reservation.getTime());
        List<Reservation> reservations = reservationService.dirReservation(reservationValid);
        if (reservations.size()>10){
            return new Result(StatusCode.INTERNAL_SERVER_ERROR, "ERROR", "该时间段预约人数已满，请选择其他时间");
        }else {
            reservation.setUuid(UUID.randomUUID().toString());
            reservation.setAppointmentHolder(UserUtils.getCurrUserInfo().getUuid());
            //这里stationId是传过来还是根据登录的基站人员去查（如果一个用户只能有一个基站的话）？
            reservation.setAppointmentStation(reservationAndStation.getStationId());
            reservation.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
            reservation.setCreateTime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm"));
            reservation.setIsCome("已预约");
        }
        return new Result(StatusCode.OK, "OK", reservationService.addReservation(reservation));
    }

    @PostMapping(value = "/dirReservation")
    public Result dirReservation(@RequestBody Reservation reservation) throws ParseException {
        reservation.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        List<Reservation> reservations = reservationService.dirReservation(reservation);
        String formatDate = DateUtils.getFormatDate("yyyy-MM-dd HH:mm");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date nowDate = format.parse(formatDate);
        for (Reservation reserva:reservations) {
            String time = reserva.getDay()+" "+reserva.getTime();
            try {
                Date resTime = format.parse(time);
                //每次刷新都要根据当前时间对比预约时间修改状态,超过自动设置为未到场
                if (resTime.before(nowDate)) {
                    reserva.setIsCome("未到场");
                    reservationService.updReservation(reserva);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String realName = userService.getRealNameByUuid(reserva.getAppointmentHolder());
            reserva.setAppointmentHolder(realName);
        }
        return new Result(StatusCode.OK, "OK", reservations);
    }
}
