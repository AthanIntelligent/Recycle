package com.jack.recycle.model;

import java.io.Serializable;
import lombok.Data;

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

    private static final long serialVersionUID = 1L;
}