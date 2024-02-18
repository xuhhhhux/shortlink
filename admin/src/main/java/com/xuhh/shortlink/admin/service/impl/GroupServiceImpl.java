package com.xuhh.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuhh.shortlink.admin.common.biz.user.UserContext;
import com.xuhh.shortlink.admin.common.convention.exception.ClientException;
import com.xuhh.shortlink.admin.dao.entity.GroupDO;
import com.xuhh.shortlink.admin.dao.mapper.GroupMapper;
import com.xuhh.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.xuhh.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.xuhh.shortlink.admin.service.GroupService;
import com.xuhh.shortlink.admin.util.RandomGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短链接分组接口实现层
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {
    @Override
    public void saveGroup(String groupName) {
        String gid = null;
        GroupDO checkGroupDO = null;
        do {
            gid = RandomGenerator.generateRandom();
            // TODO 获取用户名
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getGid, gid)
                    .eq(GroupDO::getUsername, UserContext.getUsername());
            checkGroupDO = baseMapper.selectOne(queryWrapper);
        } while (checkGroupDO != null);
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .username(UserContext.getUsername())
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
        List<GroupDO> groupDOS = baseMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(groupDOS, ShortLinkGroupRespDTO.class);
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
}
