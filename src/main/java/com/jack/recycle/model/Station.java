package com.jack.recycle.model;

import java.io.Serializable;
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
}