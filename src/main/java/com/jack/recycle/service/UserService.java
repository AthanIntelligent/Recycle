package com.jack.recycle.service;

import com.jack.recycle.model.User;
import com.jack.recycle.utils.Result;


public interface UserService {
    User getLoginUser(String loginName);
}
