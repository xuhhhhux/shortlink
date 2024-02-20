package com.xuhh.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuhh.shortlink.admin.common.convention.result.Result;
import com.xuhh.shortlink.admin.remote.ShortLinkRemoteService;
import com.xuhh.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.xuhh.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShortLinkController {
    private ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };
    /**
     * 创建短链接
     */
    @PostMapping("/api/short-link/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        return shortLinkRemoteService.createShortLink(shortLinkCreateReqDTO);
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/api/short-link/admin/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        return shortLinkRemoteService.pageShortLink(shortLinkPageReqDTO);
    }

    /**
     * 查询短链接分组内数量
     */
    @GetMapping("/api/short-link/admin/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("gids") List<String> gids) {
        return shortLinkRemoteService.listGroupShortLinkCount(gids);
    }
}
