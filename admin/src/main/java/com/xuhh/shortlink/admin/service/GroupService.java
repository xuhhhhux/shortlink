package com.xuhh.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuhh.shortlink.admin.dao.entity.GroupDO;
import com.xuhh.shortlink.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.xuhh.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.xuhh.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;

import java.util.List;

/**
 * 短链接分组接口层
 */
public interface GroupService extends IService<GroupDO> {
    /**
     * 新增短链接分组
     * @param groupName 分组名称
     */
    void saveGroup(String groupName);

    /**
     * 新增短链接分组
     * @param username 用户名
     * @param groupName 分组名称
     */
    void saveGroup(String username, String groupName);

    /**
     * 查询短链接分组
     */
    List<ShortLinkGroupRespDTO> listGroup();

    /**
     * 修改短链接分组
     * @param shortLinkGroupUpdateReqDTO 短链接分组修改参数
     */
    void updateGroup(ShortLinkGroupUpdateReqDTO shortLinkGroupUpdateReqDTO);

    /**
     * 删掉短链接分组
     * @param gid 分组标识
     */
    void deleteGroup(String gid);

    /**
     * 短链接分组排序
     * @param shortLinkGroupSortReqDTOS 需要修改的排序参数
     */
    void sortGroup(List<ShortLinkGroupSortReqDTO> shortLinkGroupSortReqDTOS);
}
