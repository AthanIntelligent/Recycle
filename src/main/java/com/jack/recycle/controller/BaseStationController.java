package com.jack.recycle.controller;

import com.jack.recycle.model.Goods;
import com.jack.recycle.model.Station;
import com.jack.recycle.model.VO.StationAndUser;
import com.jack.recycle.service.StationService;
import com.jack.recycle.utils.Result;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class BaseStationController {
    @Autowired
    StationService stationService;

    /**
     * 查看基站详情
     */
    @GetMapping(value = "/getStationDetail")
    public Result getStationDetail(String uuid){
        return new Result(Response.SC_OK,"success",stationService.getStationDetail(uuid));
    }

    /**
     * 提交入驻基站资料
     * @param station
     * @return
     */
    @PostMapping(value = "/joinBaseStation")
    public Result joinBaseStation(Station station) throws Exception {
        Station subStation = stationService.getStationDetailByUserId(station.getStationLegal());
        if(subStation != null){
            return new Result(Response.SC_OK,subStation.getCheck(),subStation);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        station.setUuid(UUID.randomUUID().toString());
        station.setCreateTime(simpleDateFormat.format(new Date()));
        station.setOpenFlag("2");//1是 2否
        station.setCheck("审核中");//审核中 //已入驻 //审核失败
        int d = stationService.joinBaseStation(station);
        if(d <= 0){
            throw new Exception("基站资料提交失败，服务器错误");
        }
        return new Result(Response.SC_OK,"success");
    }

    /**
     * 开启基站
     * @param uuid
     * @return
     */
    @GetMapping(value = "/openBaseStation")
    public Result openBaseStation(String uuid) throws Exception {
        int d = stationService.openBaseStation(uuid);
        if(d <= 0){
            throw new Exception("服务器错误");
        }
        return new Result(Response.SC_OK,"success",stationService.getStationDetail(uuid));
    }
    /**
     * 驳回申请
     */
    @GetMapping(value = "/reBaseStation")
    public Result reBaseStation(String uuid) throws Exception {
        int d = stationService.reBaseStation(uuid);
        if(d <= 0){
            throw new Exception("服务器错误");
        }
        return new Result(Response.SC_OK,"success",stationService.getStationDetail(uuid));
    }
    /**
     * 撤销申请,客户端接口
     */
    @GetMapping(value = "/cancelBaseStation")
    public Result cancelBaseStation(String uuid) throws Exception {
        StationAndUser stationAll = stationService.getStationDetail(uuid);
        Station station = stationAll.getStation();
        if(station.getCheck().equals("审核中") || station.getCheck().equals("审核失败")){
            int d = stationService.cancelBaseStation(station.getUuid());
            if(d <= 0){
                throw new Exception("服务器错误");
            }else {
                return new Result(Response.SC_OK,"撤销成功");
            }
        }
        return new Result(Response.SC_INTERNAL_SERVER_ERROR,"撤销失败");
    }
    /**
     * 注销基站，客户端接口
     */
    @GetMapping(value = "/delBaseStation")
    public Result delBaseStation(String uuid,String reason) throws Exception {
        StationAndUser stationAll = stationService.getStationDetail(uuid);
        Station station = stationAll.getStation();
        if(station.getCheck().equals("已入驻")){
            //查看当前基站当前是否存在回收物品通告
            int d = stationService.delBaseStation(station.getUuid());
            if(d <= 0){
                throw new Exception("服务器错误");
            }else {
                return new Result(Response.SC_OK,"撤销成功");
            }
        }
        return new Result(Response.SC_INTERNAL_SERVER_ERROR,"撤销失败");
    }
}
