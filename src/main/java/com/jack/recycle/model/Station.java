package com.jack.recycle.model;

import java.io.Serializable;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Station implements Serializable {
    private String uuid;

    /**
     * 商家名称
     */
    private String factoryName;

    /**
     * 商家地址
     */
    private String factoryAddress;

    /**
     * 法人
     */
    private String factoryMan;

    /**
     * 电话号
     */
    private String telephone;

    /**
     * 身份证
     */
    private String identityCard;

    private static final long serialVersionUID = 1L;
}