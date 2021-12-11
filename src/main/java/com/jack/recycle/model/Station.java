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
    private String jzUserId;

    /**
     * 基站地址
     */
    private String jzAddress;

    /**
     * 基站名称
     */
    private String jzName;

    /**
     * 可预约时间段
     */
    private String orderTime;

    /**
     * 基站图片
     */
    private String picture;

    /**
     * 创建时间
     */
    private String createTime;

    private static final long serialVersionUID = 1L;
}