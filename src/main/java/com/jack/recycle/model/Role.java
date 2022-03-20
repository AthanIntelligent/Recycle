package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

/**
 * role
 * @author 
 */
@Data
public class Role implements Serializable {
    /**
     * 角色id
     */
    private Integer uuid;

    /**
     * 角色名字
     */
    private String roleName;

    private static final long serialVersionUID = 1L;
}