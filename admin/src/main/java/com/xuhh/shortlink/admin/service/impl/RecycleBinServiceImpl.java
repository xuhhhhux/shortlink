package com.xuhh.shortlink.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuhh.shortlink.admin.common.biz.user.UserContext;
import com.xuhh.shortlink.admin.common.convention.exception.ServiceException;
import com.xuhh.shortlink.admin.common.convention.result.Result;
import com.xuhh.shortlink.admin.dao.entity.GroupDO;
import com.xuhh.shortlink.admin.dao.mapper.GroupMapper;
import com.xuhh.shortlink.admin.remote.ShortLinkActualRemoteService;
import com.xuhh.shortlink.admin.remote.ShortLinkRemoteService;
import com.xuhh.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.xuhh.shortlink.admin.service.RecycleBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后管短链接接口实现层
 */
@Service
public class RecycleBinServiceImpl implements RecycleBinService {
    private ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    @Autowired
    private ShortLinkActualRemoteService shortLinkActualRemoteService;

    @Autowired
    private GroupMapper groupMapper;


    @Override
    public Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0);
        List<GroupDO> groupDOs = groupMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(groupDOs)) {
            throw new ServiceException("用户分组信息");
        }
        shortLinkRecycleBinPageReqDTO.setGids(groupDOs.stream().map(GroupDO::getGid).toList());
        return shortLinkActualRemoteService.pageRecycleBinShortLink(shortLinkRecycleBinPageReqDTO.getGids(),
                shortLinkRecycleBinPageReqDTO.getCurrent(),
                shortLinkRecycleBinPageReqDTO.getSize());
    }
}
