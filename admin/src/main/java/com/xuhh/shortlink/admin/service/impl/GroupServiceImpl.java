package com.xuhh.shortlink.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuhh.shortlink.admin.common.convention.exception.ClientException;
import com.xuhh.shortlink.admin.dao.entity.GroupDO;
import com.xuhh.shortlink.admin.dao.mapper.GroupMapper;
import com.xuhh.shortlink.admin.service.GroupService;
import com.xuhh.shortlink.admin.util.RandomGenerator;
import org.springframework.stereotype.Service;

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
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getGid, gid)
                    .eq(GroupDO::getUsername, null);
            checkGroupDO = baseMapper.selectOne(queryWrapper);
        } while (checkGroupDO != null);
        GroupDO groupDO = GroupDO.builder()
                .gid(gid)
                .name(groupName)
                .build();
        int count = baseMapper.insert(groupDO);
        if (count < 1) {
            throw new ClientException("新增短链接分组失败");
        }
    }
}
