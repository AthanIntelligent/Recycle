package com.jack.recycle.mapper;

import com.jack.recycle.model.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface UserDao {
    int deleteByPrimaryKey(String uuid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUserName(String username);

    List<User> selectAllUser();

    List<User> dirUserInfo(User record);

    int updateToken(String token, String loginName);

    String getRealNameByUuid(String uuid);

    String getUuidByRealName(String realName);

    List<User> selectUserByType(List<String> typeIds);

    List<User> selectUserByUUids(List<String> uuids);
}