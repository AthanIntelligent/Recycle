package com.jack.recycle.model;

import lombok.Data;

import java.io.Serializable;

/**
 * reservation
 * @author
 */
@Data
public class Reservation implements Serializable {
    /**
     * 主键id
     */
    private String uuid;

    /**
     * 预约日期
     */
    private String day;

    /**
     * 预约时间点
     */
    private String time;


    /**
     * 预约人
     */
    private String appointmentHolder;


    /**
     * 预约基站
     */
    private String appointmentStation;

    /**
     * 基站法人id
     */
    private String stationLegal;

    /**
     * 到访状态  已预约  已签到  已撤销  已失效
     */
    private String isCome;

    /**
     * 创建时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;
}
