package com.jack.recycle.controller;

import com.jack.recycle.model.Station;
import com.jack.recycle.model.GoodsOfStation;
import com.jack.recycle.model.VO.StationAndGoodsIds;
import com.jack.recycle.model.VO.StationAndUser;
import com.jack.recycle.service.StationService;
import com.jack.recycle.service.GoodsOfStationService;
import com.jack.recycle.utils.DateUtils;
import com.jack.recycle.utils.Result;
import com.jack.recycle.utils.UserUtils;
import org.apache.catalina.connector.Response;
import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/station")
public class BaseStationController {
    @Autowired
    StationService stationService;

    @Autowired
    GoodsOfStationService typeOfStationService;

    /**
     * 查看基站列表
     */
    @PostMapping(value = "/dirStation")
    public Result dirStation(@RequestBody Station station){
        if ("开启".equals(station.getOpenFlag()))
            station.setOpenFlag("1");
        if ("关闭".equals(station.getOpenFlag()))
            station.setOpenFlag("2");
        return new Result(Response.SC_OK,"success",stationService.dirStation(station));
    }

    /**
     * 获取基站法人信息
     */
    @GetMapping(value = "/getStationLegal")
    public Result getStationLegal(String userId){
        return new Result(Response.SC_OK,"success",stationService.getStationLegal(userId));
    }

    /**
     * 查看基站详情
     */
    @GetMapping(value = "/getStationDetail")
    public Result getStationDetail(String uuid){
        return new Result(Response.SC_OK,"success",stationService.getStationDetail(uuid));
    }

    /**
     * 查看基站状态
     */
    @GetMapping(value = "/getStationStatus")
    public Result getStationStatus(String userId){
        return new Result(Response.SC_OK,"success",stationService.getStationStatus(userId));
    }

    /**
     * 修改基站信息（管理员可修改状态）
     */
    @PostMapping(value = "/updStation")
    public Result updStation(@RequestBody Station station){
        Station stationDetailByUserId = stationService.getStationDetailByUserId(station.getUuid());
        int i = 0;
        if (!station.equals(stationDetailByUserId)) {
            i = stationService.updStation(station);
        }
        return new Result(Response.SC_OK,"success",i);
    }

    /**
     * 注册基站
     */
    @PostMapping(value = "/addStation")
    public Result addStation(@RequestBody StationAndGoodsIds stationAndGoodsIds) throws Exception {
        Station station = stationAndGoodsIds.getStation();
        String goodsIds = stationAndGoodsIds.getGoodsIds();
        if (StringUtils.isEmpty(station.getStationName())||StringUtils.isEmpty(station.getStationAddress())) {
            return new Result(Response.SC_INTERNAL_SERVER_ERROR,"必填参数不能为空",null);
        }
        station.setUuid(UUID.randomUUID().toString());
        station.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        station.setOpenFlag("2");
        station.setCheck("审核中");
        station.setCreateTime(DateUtils.getFormatDate("yyyy-MM-dd hh:mm:ss"));
        GoodsOfStation typeOfStation = new GoodsOfStation();
        typeOfStation.setUuid(UUID.randomUUID().toString());
        typeOfStation.setStationId(station.getUuid());
        typeOfStation.setStationLegal(UserUtils.getCurrUserInfo().getUuid());
        if (!StringHelper.isNullOrEmptyString(goodsIds)) {
            typeOfStation.setGoodsIds(goodsIds);
        }
        typeOfStationService.addGoodsOfStation(typeOfStation);
        stationService.addStation(station);
        //添加基站后给前端返回基站id,用于查看最后一步的状态
        return new Result(Response.SC_OK,"success",station.getUuid());
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

    /**
     * /station/updateJoinStationActive
     */
    @PostMapping(value = "/updateJoinStationActive")
    public Result updateJoinStationActive(@RequestBody Station station){
       return new Result(Response.SC_OK,"success",stationService.updateJoinStationActive(station));
    }
}
