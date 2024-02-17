package com.xuhh.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuhh.shortlink.admin.dao.entity.UserDO;
import com.xuhh.shortlink.admin.dto.req.UserLoginReqDTO;
import com.xuhh.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.xuhh.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.xuhh.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.xuhh.shortlink.admin.dto.resp.UserRespDTO;

/**
 * 用户接口层
 */
public interface UserService extends IService<UserDO> {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 查询用户名是否存在
     * @param username 用户名
     * @return true : 存在, false : 不存在
     */
    Boolean hasUsername(String username);

    /**
     * 用户注册
     * @param userRegisterReqDTO 用户注册信息
     */
    void register(UserRegisterReqDTO userRegisterReqDTO);

    /**
     * 根据用户名修改用户信息
     * @param userUpdateReqDTO 用户修改信息
     */
    void update(UserUpdateReqDTO userUpdateReqDTO);

    /**
     * 用户登录
     * @param userLoginReqDTO 用户登录信息
     * @return
     */
    UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO);

    /**
     * 检查用户是否登录
     * @param username 用户名
     * @param token token
     */
    Boolean checkLogin(String username, String token);

    /**
     * 用户退出登录
     * @param username 用户名
     * @param token token
     */
    void logout(String username, String token);
}
