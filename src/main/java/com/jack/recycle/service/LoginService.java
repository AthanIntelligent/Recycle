package com.jack.recycle.service;

import com.jack.recycle.model.User;
import com.jack.recycle.utils.Result;

public interface LoginService {

    Result login(User user);

    Result regist(User user);

    int logout(String uuid);
}
