package com.xuhh.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuhh.shortlink.project.dao.entity.ShortLinkDO;
import com.xuhh.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkPageRespDTO;

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

    /**
     * 短链接分页查询
     * @param shortLinkPageReqDTO 短链接分页查询请求对象
     * @return 短链接分页结果
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);
}
