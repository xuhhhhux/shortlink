package com.xuhh.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuhh.shortlink.admin.common.convention.exception.ClientException;
import com.xuhh.shortlink.admin.common.convention.exception.ServiceException;
import com.xuhh.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.xuhh.shortlink.admin.dao.entity.UserDO;
import com.xuhh.shortlink.admin.dao.mapper.UserMapper;
import com.xuhh.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.xuhh.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.xuhh.shortlink.admin.dto.resp.UserRespDTO;
import com.xuhh.shortlink.admin.service.UserService;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.xuhh.shortlink.admin.common.constant.RedisConstant.LOCK_USER_REGISTER_KEY;
import static com.xuhh.shortlink.admin.common.enums.UserErrorCodeEnum.*;

/**
 * 用户接口实现层
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {
    @Autowired
    private RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

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
}
