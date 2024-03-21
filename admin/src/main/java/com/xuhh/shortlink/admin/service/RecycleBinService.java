package com.xuhh.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuhh.shortlink.admin.common.convention.result.Result;
import com.xuhh.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;

/**
 * 后管短链接回收站接口层
 */
public interface RecycleBinService {
    /**
     * 后管短链接回收站分页查询
     * @param shortLinkRecycleBinPageReqDTO
     * @return
     */
    Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO);
}
