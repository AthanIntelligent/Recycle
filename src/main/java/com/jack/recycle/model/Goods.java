package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

/**
 * auth
 * @author 
 */
@Data
public class Goods implements Serializable {
    private Integer uuid;

    /**
     * 权限名
     */
    private String authName;

    /**
     * 角色id
     */
    private Integer roleId;

    private static final long serialVersionUID = 1L;
}