package com.jack.recycle.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@HeadRowHeight(25)      // 表头行高
@ContentRowHeight(20)   // 表体行高
@ColumnWidth(20)        // 列宽
@HeadFontStyle(fontHeightInPoints = 16)
@ContentFontStyle(fontHeightInPoints = 14)
public class GoodsExcel {
    @ExcelProperty(value = "*回收物品类型",index = 0)
    @NotNull(message = "回收物品类型不能为空")
    private String goodsType;

    @ExcelProperty(value = "*回收物品名称",index = 1)
    @NotNull(message = "回收物品名称不能为空")
    private String goodsName;

    @ExcelProperty(value = "*物品详细介绍",index = 2)
    @NotNull(message = "物品详细介绍不能为空")
    private String recycleDetail;

    @ExcelProperty(value = "单价",index = 3)
    private String perMoney;

    @ExcelProperty(value = "新旧程度",index = 4)
    private String remark;

//    @ExcelProperty(value = "图片",index = 5)
//    private String pic;
}
