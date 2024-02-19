package com.xuhh.shortlink.admin.dto.req;

import lombok.Data;

@Data
public class UserLoginReqDTO {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * token
     */
    private String token;
}