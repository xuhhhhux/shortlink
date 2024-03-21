package com.xuhh.shortlink.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuhh.shortlink.admin.common.convention.result.Result;
import com.xuhh.shortlink.admin.common.convention.result.Results;
import com.xuhh.shortlink.admin.remote.ShortLinkActualRemoteService;
import com.xuhh.shortlink.admin.remote.dto.req.RecycleBinRecoverReqDTO;
import com.xuhh.shortlink.admin.remote.dto.req.RecycleBinRemoveReqDTO;
import com.xuhh.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import com.xuhh.shortlink.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.xuhh.shortlink.admin.service.RecycleBinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecycleBinController {

    @Autowired
    private ShortLinkActualRemoteService shortLinkActualRemoteService;

    @Autowired
    private RecycleBinService recycleBinService;

    /**
     * 删除短链接到回收站
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        return shortLinkActualRemoteService.saveRecycleBin(recycleBinSaveReqDTO);
    }

    /**
     * 短链接回收站分页查询
     */
    @GetMapping("/api/short-link/admin/v1/recycle-bin/page")
    public Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO) {
        return recycleBinService.pageRecycleBinShortLink(shortLinkRecycleBinPageReqDTO);
    }

    /**
     * 回收站恢复短链接
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        shortLinkActualRemoteService.recoverRecycleBin(recycleBinRecoverReqDTO);
        return Results.success();
    }


    /**
     * 回收站删除短链接
     */
    @PostMapping("/api/short-link/admin/v1/recycle-bin/remove")
    public Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO recycleBinRemoveReqDTO) {
        shortLinkActualRemoteService.removeRecycleBin(recycleBinRemoveReqDTO);
        return Results.success();
    }
}
