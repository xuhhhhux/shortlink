package com.xuhh.shortlink.project.controller;

import com.xuhh.shortlink.project.common.convention.result.Result;
import com.xuhh.shortlink.project.common.convention.result.Results;
import com.xuhh.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkStatsRespDTO;
import com.xuhh.shortlink.project.service.ShortLinkStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接监控控制层
 */
@RestController
public class ShortLinkStatsController {

    @Autowired
    private ShortLinkStatsService shortLinkStatsService;

    /**
     * 访问单个短链接指定时间内监控数据
     */
    @GetMapping("/api/short-link/v1/stats")
    public Result<ShortLinkStatsRespDTO> shortLinkStats(ShortLinkStatsReqDTO requestParam) {
        return Results.success(shortLinkStatsService.oneShortLinkStats(requestParam));
    }
}