package com.jack.recycle.model;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

/**
 * station
 * @author 
 */
@Data
public class Station implements Serializable {
    private String uuid;

    /**
     * 基站人员id
     */
    private String stationLegal;

    /**
     * 基站地址
     */
    private String stationAddress;

    /**
     * 基站名称
     */
    private String stationName;

    /**
     * 开启状态
     */
    private String openFlag;

    /**
     * 审核状态
     */
    private String check;

    /**
     * 创建时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return Objects.equals(uuid, station.uuid) &&
                Objects.equals(stationLegal, station.stationLegal) &&
                Objects.equals(stationAddress, station.stationAddress) &&
                Objects.equals(stationName, station.stationName) &&
                Objects.equals(openFlag, station.openFlag) &&
                Objects.equals(check, station.check) &&
                Objects.equals(createTime, station.createTime);
    }
}