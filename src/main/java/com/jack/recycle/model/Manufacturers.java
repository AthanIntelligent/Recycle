package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * manufacturers
 * @author 
 */
@Data
public class Manufacturers implements Serializable {
    private String uuid;

    private String factureName;

    private String facturePhone;

    private String recycleGoodsAndPrice;

    private String factureImage;

    private String createTime;

    private String factureAddress;

    @Transient
    private Boolean isVisible;

    private static final long serialVersionUID = 1L;
}