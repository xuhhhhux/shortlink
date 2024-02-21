package com.xuhh.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuhh.shortlink.project.dao.entity.ShortLinkDO;
import com.xuhh.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import com.xuhh.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/**
 * 短链接回收站接口层
 */
public interface RecycleBinService extends IService<ShortLinkDO> {
    /**
     * 删除短链接至回收站
     * @param recycleBinSaveReqDTO
     */
    void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO);

    /**
     * 短链接回收站分页查询
     * @param shortLinkRecycleBinPageReqDTO
     * @return
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO);

    /**
     * 回收站恢复短链接
     * @param recycleBinRecoverReqDTO
     * @return
     */
    void recoverRecycleBin(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO);
}
