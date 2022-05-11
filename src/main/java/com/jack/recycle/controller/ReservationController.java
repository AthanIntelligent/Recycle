package com.jack.recycle.controller;

import com.jack.recycle.model.Reservation;
import com.jack.recycle.model.VO.ReservationAndStation;
import com.jack.recycle.model.VO.ReservationVO;
import com.jack.recycle.model.VO.StationAndUser;
import com.jack.recycle.service.ReservationService;
import com.jack.recycle.service.StationService;
import com.jack.recycle.service.UserService;
import com.jack.recycle.utils.DateUtils;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.StatusCode;
import com.jack.recycle.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    private StationService stationService;

    @PostMapping(value = "/addReservation")
    public Result addReservation(@RequestBody Reservation reservation) throws Exception {
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
            reservation.setCreateTime(DateUtils.getFormatDate("yyyy-MM-dd HH:mm"));
        }
        return new Result(StatusCode.OK, "OK", reservationService.addReservation(reservation));
    }

    @PostMapping(value = "/dirReservation")
    public Result dirReservation(@RequestBody Reservation reservation) throws Exception {
        //判断用户类型
        if (UserUtils.getCurrUserInfo().getUserType().equals("2"))
            reservation.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        if (UserUtils.getCurrUserInfo().getUserType().equals("1"))
            reservation.setAppointmentHolder(UserUtils.getCurrUserInfo().getUuid());
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
        if (UserUtils.getCurrUserInfo().getUserType().equals("2"))
            return new Result(StatusCode.OK, "OK", reservations);

        List<ReservationVO> reservationVOList = new ArrayList<>();
        if (UserUtils.getCurrUserInfo().getUserType().equals("1")) {
            for (Reservation reserva:reservations) {
                ReservationVO reservationVO = new ReservationVO();
                reservationVO.setUuid(reserva.getUuid());
                reservationVO.setDay(reserva.getDay());
                reservationVO.setTime(reserva.getTime());
                reservationVO.setAppointmentHolder(reserva.getAppointmentHolder());
                reservationVO.setIsCome(reserva.getIsCome());
                reservationVO.setCreateTime(reserva.getCreateTime());
                reservationVO.setStationLegal(reserva.getStationLegal());
                StationAndUser stationDetail = stationService.getStationDetail(reserva.getAppointmentStation());
                reservationVO.setStationName(stationDetail.getStation().getStationName());
                reservationVO.setStationAddress(stationDetail.getStation().getStationAddress());
                reservationVO.setMobile(stationDetail.getUser().getMobile());
                reservationVO.setStationLegal(stationDetail.getUser().getRealName());
                reservationVOList.add(reservationVO);
            }
            return new Result(StatusCode.OK, "OK", reservationVOList);
        }
        return new Result(StatusCode.OK, "OK", null);
    }

    @PostMapping(value = "/updReservation")
    public Result updReservation(@RequestBody Reservation reservation) {
        reservation.setIsCome("已撤销");
        return new Result(StatusCode.OK, "OK", reservationService.updReservation(reservation));
    }
}
