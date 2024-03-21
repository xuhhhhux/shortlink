package com.xuhh.shortlink.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuhh.shortlink.admin.common.convention.result.Result;
import com.xuhh.shortlink.admin.common.convention.result.Results;
import com.xuhh.shortlink.admin.dto.req.ShortLinkBatchCreateReqDTO;
import com.xuhh.shortlink.admin.dto.resp.ShortLinkBaseInfoRespDTO;
import com.xuhh.shortlink.admin.dto.resp.ShortLinkBatchCreateRespDTO;
import com.xuhh.shortlink.admin.remote.ShortLinkActualRemoteService;
import com.xuhh.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.xuhh.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.xuhh.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.xuhh.shortlink.admin.util.EasyExcelWebUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShortLinkController {
    @Autowired
    private ShortLinkActualRemoteService shortLinkActualRemoteService;

    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        return shortLinkActualRemoteService.createShortLink(shortLinkCreateReqDTO);
    }

    /**
     * 批量创建短链接
     */
    @SneakyThrows
    @PostMapping("/api/short-link/admin/v1/create/batch")
    public void batchCreateShortLink(@RequestBody ShortLinkBatchCreateReqDTO requestParam, HttpServletResponse response) {
        Result<ShortLinkBatchCreateRespDTO> shortLinkBatchCreateRespDTOResult = shortLinkActualRemoteService.batchCreateShortLink(requestParam);
        if (shortLinkBatchCreateRespDTOResult.isSuccess()) {
            List<ShortLinkBaseInfoRespDTO> baseLinkInfos = shortLinkBatchCreateRespDTOResult.getData().getBaseLinkInfos();
            EasyExcelWebUtil.write(response, "批量创建短链接-SaaS短链接系统", ShortLinkBaseInfoRespDTO.class, baseLinkInfos);
        }
    }

    /**
     * 修改短链接
     */
    @PostMapping("/api/short-link/admin/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        shortLinkActualRemoteService.updateShortLink(requestParam);
        return Results.success();
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<Page<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        return shortLinkActualRemoteService.pageShortLink(shortLinkPageReqDTO.getGid(), shortLinkPageReqDTO.getOrderTag(), shortLinkPageReqDTO.getCurrent(), shortLinkPageReqDTO.getSize());
    }

    /**
     * 查询短链接分组内数量
     */
    @GetMapping("/api/short-link/admin/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("gids") List<String> gids) {
        return shortLinkActualRemoteService.listGroupShortLinkCount(gids);
    }

    /**
     * 根据url获取title
     */
    @GetMapping("/api/short-link/admin/v1/title")
    public Result<String> getTitleByUrl(@RequestParam("url") String url) {
        return shortLinkActualRemoteService.getTitleByUrl(url);
    }
}
