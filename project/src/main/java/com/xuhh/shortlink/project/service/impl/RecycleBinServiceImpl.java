package com.xuhh.shortlink.project.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuhh.shortlink.project.dao.entity.ShortLinkDO;
import com.xuhh.shortlink.project.dao.mapper.ShortLinkMapper;
import com.xuhh.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import com.xuhh.shortlink.project.dto.req.RecycleBinRemoveReqDTO;
import com.xuhh.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.xuhh.shortlink.project.service.RecycleBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.xuhh.shortlink.project.common.constant.RedisConstant.GOTO_IS_NULL_SHORT_LINK_KEY;
import static com.xuhh.shortlink.project.common.constant.RedisConstant.GOTO_SHORT_LINK_KEY;

/**
 * 短链接回收站接口实现层
 */
@Service
public class RecycleBinServiceImpl extends ServiceImpl<ShortLinkMapper, ShortLinkDO> implements RecycleBinService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, recycleBinSaveReqDTO.getGid())
                .eq(ShortLinkDO::getFullShortUrl, recycleBinSaveReqDTO.getFullShortUrl())
                .eq(ShortLinkDO::getEnableStatus, 0)
                .eq(ShortLinkDO::getEnableStatus, 0);
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .enableStatus(1)
                .build();
        baseMapper.update(shortLinkDO, updateWrapper);
        stringRedisTemplate.delete(String.format(GOTO_SHORT_LINK_KEY, recycleBinSaveReqDTO.getFullShortUrl()));

    }

    @Override
    public IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> queryWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .in(ShortLinkDO::getGid, shortLinkRecycleBinPageReqDTO.getGids())
                .eq(ShortLinkDO::getDelFlag, 0)
                .eq(ShortLinkDO::getEnableStatus, 1);
        IPage<ShortLinkDO> resultPage = baseMapper.selectPage(shortLinkRecycleBinPageReqDTO, queryWrapper);
        return resultPage.convert(each -> BeanUtil.toBean(each, ShortLinkPageRespDTO.class));
    }

    @Override
    public void recoverRecycleBin(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, recycleBinRecoverReqDTO.getGid())
                .eq(ShortLinkDO::getFullShortUrl, recycleBinRecoverReqDTO.getFullShortUrl())
                .eq(ShortLinkDO::getEnableStatus, 1)
                .eq(ShortLinkDO::getDelFlag, 0);
        ShortLinkDO shortLinkDO = ShortLinkDO.builder()
                .enableStatus(0)
                .build();
        baseMapper.update(shortLinkDO, updateWrapper);
        stringRedisTemplate.delete(String.format(GOTO_IS_NULL_SHORT_LINK_KEY, recycleBinRecoverReqDTO.getFullShortUrl()));
    }

    @Override
    public void removeRecycleBin(RecycleBinRemoveReqDTO recycleBinRemoveReqDTO) {
        LambdaQueryWrapper<ShortLinkDO> updateWrapper = Wrappers.lambdaQuery(ShortLinkDO.class)
                .eq(ShortLinkDO::getGid, recycleBinRemoveReqDTO.getGid())
                .eq(ShortLinkDO::getFullShortUrl, recycleBinRemoveReqDTO.getFullShortUrl())
                .eq(ShortLinkDO::getEnableStatus, 1)
                .eq(ShortLinkDO::getDelFlag, 0);
        baseMapper.delete(updateWrapper);
    }
}
