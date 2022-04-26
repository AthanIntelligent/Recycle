package com.jack.recycle.model.VO;

import com.jack.recycle.model.Goods;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsTypeAndGoods implements Serializable {
    String goodsType;
    List<Goods> goodsList;
}
