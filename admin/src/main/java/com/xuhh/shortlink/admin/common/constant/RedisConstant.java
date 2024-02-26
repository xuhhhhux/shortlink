package com.xuhh.shortlink.admin.common.constant;

/**
 * Redis常量类
 */
public class RedisConstant {
    /**
     * 用户注册分布式锁
     */
    public static final String LOCK_USER_REGISTER_KEY = "short-link:lock:user-register:";

    /**
     * 分组创建分布式锁
     */
    public static final String LOCK_GROUP_CREATE_KEY = "short-link:lock:group-create:%s";

    /**
     * 用户登录
     */
    public static final String USER_LOGIN_KEY = "short-link:login:";
}
