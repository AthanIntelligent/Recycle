package com.jack.recycle.model.VO;

import com.jack.recycle.model.Station;
import lombok.Data;

@Data
public class StationAndGoodsIds {
    private Station station;
    private String goodsIds;
}
