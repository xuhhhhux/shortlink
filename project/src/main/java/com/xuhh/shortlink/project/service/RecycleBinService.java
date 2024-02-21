package com.xuhh.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuhh.shortlink.project.dao.entity.ShortLinkDO;
import com.xuhh.shortlink.project.dto.req.RecycleBinSaveReqDTO;

/**
 * 短链接回收站接口层
 */
public interface RecycleBinService extends IService<ShortLinkDO> {
    void saveRecycleBin(RecycleBinSaveReqDTO recycleBinSaveReqDTO);
}
