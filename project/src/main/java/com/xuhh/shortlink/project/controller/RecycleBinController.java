package com.xuhh.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuhh.shortlink.project.common.convention.result.Result;
import com.xuhh.shortlink.project.common.convention.result.Results;
import com.xuhh.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import com.xuhh.shortlink.project.dto.req.RecycleBinRemoveReqDTO;
import com.xuhh.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.xuhh.shortlink.project.service.RecycleBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接控制层
 */
@RestController
public class RecycleBinController {
    @Autowired
    private RecycleBinService recycleBinService;

    @PostMapping("/api/short-link/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        recycleBinService.saveRecycleBin(recycleBinSaveReqDTO);
        return Results.success();
    }

    /**
     * 短链接回收站分页查询
     */
    @GetMapping("/api/short-link/v1/recycle-bin/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO) {
        return Results.success(recycleBinService.pageShortLink(shortLinkRecycleBinPageReqDTO));
    }

    /**
     * 回收站恢复短链接
     */
    @PostMapping("/api/short-link/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        recycleBinService.recoverRecycleBin(recycleBinRecoverReqDTO);
        return Results.success();
    }

    /**
     * 回收站删除短链接
     */
    @PostMapping("/api/short-link/v1/recycle-bin/remove")
    public Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO recycleBinRemoveReqDTO) {
        recycleBinService.removeRecycleBin(recycleBinRemoveReqDTO);
        return Results.success();
    }
}
