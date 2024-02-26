package com.xuhh.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuhh.shortlink.admin.common.convention.exception.ClientException;
import com.xuhh.shortlink.admin.common.convention.exception.ServiceException;
import com.xuhh.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.xuhh.shortlink.admin.dao.entity.UserDO;
import com.xuhh.shortlink.admin.dao.mapper.UserMapper;
import com.xuhh.shortlink.admin.dto.req.UserLoginReqDTO;
import com.xuhh.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.xuhh.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.xuhh.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.xuhh.shortlink.admin.dto.resp.UserRespDTO;
import com.xuhh.shortlink.admin.service.GroupService;
import com.xuhh.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.xuhh.shortlink.admin.common.constant.RedisConstant.LOCK_USER_REGISTER_KEY;
import static com.xuhh.shortlink.admin.common.constant.RedisConstant.USER_LOGIN_KEY;
import static com.xuhh.shortlink.admin.common.enums.UserErrorCodeEnum.*;

/**
 * 用户接口实现层
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Autowired
    private RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RedissonClient redissonClient;

    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ServiceException(UserErrorCodeEnum.USER_NULL);
        }
        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public Boolean hasUsername(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO userRegisterReqDTO) {
        if (!hasUsername(userRegisterReqDTO.getUsername())) {
            throw new ClientException(USER_NAME_EXIST);
        }
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + userRegisterReqDTO.getUsername());
        try {
            if (lock.tryLock()) {
                int count = baseMapper.insert(BeanUtil.toBean(userRegisterReqDTO, UserDO.class));
                if (count < 1) {
                    throw new ServiceException(USER_SAVE_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(userRegisterReqDTO.getUsername());
                groupService.saveGroup(userRegisterReqDTO.getUsername(), "默认分组");
                return;
            }
            throw new ClientException(USER_NAME_EXIST);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(UserUpdateReqDTO userUpdateReqDTO) {
        // TODO 判断是否是用户自己
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class).eq(UserDO::getUsername, userUpdateReqDTO.getUsername());
        int count = baseMapper.update(BeanUtil.toBean(userUpdateReqDTO, UserDO.class), queryWrapper);
        if (count < 1) {
            throw new ServiceException(USER_UPDATE_ERROR);
        }
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO userLoginReqDTO) {
        String username = userLoginReqDTO.getUsername();
        String password = userLoginReqDTO.getPassword();
        if (hasUsername(username)) {
            throw new ClientException(USER_NAME_NOT_EXIST);
        }

        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getPassword, password)
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException(USER_PASSWORD_ERROR);
        }
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(USER_LOGIN_KEY + username))) {
            throw new ClientException(USER_ALREADY_LOGIN);
        }
        Map<Object, Object> entries = stringRedisTemplate.opsForHash().entries(USER_LOGIN_KEY + username);
        if (CollUtil.isNotEmpty(entries)) {
            String token = entries.keySet()
                    .stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElseThrow(() -> new ClientException("用户登录错误"));
            return new UserLoginRespDTO(token);
        }

        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put(USER_LOGIN_KEY + username, token, JSON.toJSONString(userDO));
        // todo 登录有效期
        stringRedisTemplate.expire(USER_LOGIN_KEY + username, 30L, TimeUnit.DAYS);

        return new UserLoginRespDTO(token);
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY + username, token) != null;
    }

    @Override
    public void logout(String username, String token) {
        if (checkLogin(username, token)) {
            stringRedisTemplate.delete(USER_LOGIN_KEY + username);
            return;
        }
        throw new ClientException("用户Token不存在或用户未登录");
    }
}
