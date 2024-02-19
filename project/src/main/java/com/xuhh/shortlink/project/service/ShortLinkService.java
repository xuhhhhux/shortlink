package com.xuhh.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuhh.shortlink.project.dao.entity.ShortLinkDO;
import com.xuhh.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkCreateRespDTO;

/**
 * 短链接管理接口层
 */
public interface ShortLinkService extends IService<ShortLinkDO> {

    /**
     * 创建短链接
     * @param shortLinkCreateReqDTO 短链接创建请求对象
     * @return 短链接创建响应对象
     */
    ShortLinkCreateRespDTO createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO);
}
