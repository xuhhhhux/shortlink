package com.xuhh.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuhh.shortlink.admin.common.biz.user.UserContext;
import com.xuhh.shortlink.admin.common.convention.exception.ClientException;
import com.xuhh.shortlink.admin.common.convention.result.Result;
import com.xuhh.shortlink.admin.dao.entity.GroupDO;
import com.xuhh.shortlink.admin.dao.mapper.GroupMapper;
import com.xuhh.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.xuhh.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.xuhh.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.xuhh.shortlink.admin.remote.ShortLinkRemoteService;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.xuhh.shortlink.admin.service.GroupService;
import com.xuhh.shortlink.admin.util.RandomGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 短链接分组接口实现层
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {
    private ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };

    @Override
    public void saveGroup(String groupName) {
        saveGroup(UserContext.getUsername(), groupName);
    }

    @Override
    public void saveGroup(String username, String groupName) {
        String gid = null;
        GroupDO checkGroupDO = null;
        do {
            gid = RandomGenerator.generateRandom();
            // TODO 获取用户名
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getGid, gid)
                    .eq(GroupDO::getUsername, username);
            checkGroupDO = baseMapper.selectOne(queryWrapper);
        } while (checkGroupDO != null);
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .username(username)
                .name(groupName)
                .sortOrder(0)
                .build();
        int count = baseMapper.insert(groupDO);
        if (count < 1) {
            throw new ClientException("新增短链接分组失败");
        }
    }

    @Override
    public List<ShortLinkGroupRespDTO> listGroup() {
        // TODO 获取用户名
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime)
                .eq(GroupDO::getDelFlag, 0);
        List<GroupDO> groupDOs = baseMapper.selectList(queryWrapper);

        Result<List<ShortLinkGroupCountQueryRespDTO>> listResult = shortLinkRemoteService.listGroupShortLinkCount(groupDOs.stream().map(GroupDO::getGid).toList());
        List<ShortLinkGroupRespDTO> shortLinkGroupRespDTOs = BeanUtil.copyToList(groupDOs, ShortLinkGroupRespDTO.class);
        shortLinkGroupRespDTOs.forEach(
                each -> {
                    Optional<ShortLinkGroupCountQueryRespDTO> first = listResult.getData().stream().filter(item -> Objects.equals(item.getGid(), each.getGid())).findFirst();
                    first.ifPresent(item -> each.setShortLinkCount(first.get().getShortLinkCount()));
                }
        );

        return shortLinkGroupRespDTOs;
    }

    @Override
    public void updateGroup(ShortLinkGroupUpdateReqDTO shortLinkGroupUpdateReqDTO) {
        LambdaQueryWrapper<GroupDO> updateWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getGid, shortLinkGroupUpdateReqDTO.getGid())
                .eq(GroupDO::getUsername, UserContext.getUsername());
        GroupDO groupDO = new GroupDO();
        groupDO.setName(shortLinkGroupUpdateReqDTO.getName());
        baseMapper.update(groupDO, updateWrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, gid);
        GroupDO groupDO = new GroupDO();
        groupDO.setDelFlag(1);
        baseMapper.update(groupDO, queryWrapper);
    }

    @Override
    public void sortGroup(List<ShortLinkGroupSortReqDTO> shortLinkGroupSortReqDTOS) {
        shortLinkGroupSortReqDTOS.forEach(each -> {
            // TODO 用户名
            GroupDO groupDO = GroupDO.builder()
                    .sortOrder(each.getSortOrder())
                    .build();
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getGid, each.getGid())
                    .eq(GroupDO::getDelFlag, 0)
                    .eq(GroupDO::getUsername, UserContext.getUsername());
            baseMapper.update(groupDO, queryWrapper);
        });
    }
}
