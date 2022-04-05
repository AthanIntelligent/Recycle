package com.jack.recycle.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    private String uuid;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * token
     */
    private String token;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 用户类型（1：普通用户；2：基站人员；0：管理员）
     */
    private Integer userType;

    /**
     * 性别
     */
    private String sex;

    /**
     * 注册时间
     */
    private String createTime;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身份证号
     */
    private String id;


    private static final long serialVersionUID = 1L;
}