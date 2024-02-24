package com.xuhh.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xuhh.shortlink.project.dao.entity.ShortLinkDO;
import com.xuhh.shortlink.project.dto.req.ShortLinkBatchCreateReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkUpdateReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkBatchCreateRespDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

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
     * 批量创建短链接
     *
     * @param requestParam 批量创建短链接请求参数
     * @return 批量创建短链接返回参数
     */
    ShortLinkBatchCreateRespDTO batchCreateShortLink(ShortLinkBatchCreateReqDTO requestParam);

    /**
     * 修改短链接
     *
     * @param requestParam 修改短链接请求参数
     */
    void updateShortLink(ShortLinkUpdateReqDTO requestParam);

    /**
     * 短链接分页查询
     * @param shortLinkPageReqDTO 短链接分页查询请求对象
     * @return 短链接分页结果
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO);

    /**
     * 查询短链接分组内数量
     * @param gids 分组标识数组
     * @return
     */
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> gids);

    /**
     * 短链接跳转
     * @param shortUri 短链接后缀
     * @param request
     * @param response
     */
    void restoreUrl(String shortUri, HttpServletRequest request, HttpServletResponse response);
}
