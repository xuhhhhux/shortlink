package com.xuhh.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuhh.shortlink.project.common.convention.result.Result;
import com.xuhh.shortlink.project.common.convention.result.Results;
import com.xuhh.shortlink.project.dto.req.ShortLinkCreateReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.xuhh.shortlink.project.service.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接管理控制层
 */
@RestController
public class ShortLinkController {
    @Autowired
    private ShortLinkService shortLinkService;

    /**
     * 新增短链接
     */
    @PostMapping("/api/short-link/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        return Results.success(shortLinkService.createShortLink(shortLinkCreateReqDTO));
    }

    /**
     * 短链接分页查询
     */
    @GetMapping("/api/short-link/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        return Results.success(shortLinkService.pageShortLink(shortLinkPageReqDTO));
    }

    /**
     * 查询短链接分组内数量
     */
    @GetMapping("/api/short-link/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("gids") List<String> gids) {
        return Results.success(shortLinkService.listGroupShortLinkCount(gids));
    }
}
