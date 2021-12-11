package com.jack.recycle.service;

import com.jack.recycle.model.User;
import com.jack.recycle.utils.Result;


public interface UserService {

    Result login(User user);

    Result regist(User user);
}
