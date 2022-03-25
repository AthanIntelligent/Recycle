package com.jack.recycle.model.VO;

import com.jack.recycle.model.Station;
import com.jack.recycle.model.User;
import lombok.Data;

@Data
public class StationAndUser {
    private Station station;
    private User user;
}
