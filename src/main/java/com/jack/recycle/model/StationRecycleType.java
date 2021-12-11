package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

/**
 * station_recycle_type
 * @author 
 */
@Data
public class StationRecycleType implements Serializable {
    private String uuid;

    /**
     * 基站id
     */
    private String jzId;

    /**
     * 基站类型id
     */
    private String recycleTypeId;

    private static final long serialVersionUID = 1L;
}