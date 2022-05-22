package com.jack.recycle.model.VO;

import com.jack.recycle.model.Trade;
import com.jack.recycle.model.TradeGoods;
import lombok.Data;

import java.util.List;

@Data
public class TradeAndGoods {
    private Trade trade;
    private List<TradeGoods> tradeGoods;
}
