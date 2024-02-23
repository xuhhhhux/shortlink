package com.xuhh.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuhh.shortlink.project.common.convention.result.Result;
import com.xuhh.shortlink.project.common.convention.result.Results;
import com.xuhh.shortlink.project.dto.req.ShortLinkGroupStatsReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkStatsAccessRecordRespDTO;
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

    /**
     * 访问分组短链接指定时间内监控数据
     */
    @GetMapping("/api/short-link/v1/stats/group")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStats(ShortLinkGroupStatsReqDTO requestParam) {
        return Results.success(shortLinkStatsService.groupShortLinkStats(requestParam));
    }

    /**
     * 访问单个短链接指定时间访问记录监控数据
     */
    @GetMapping("/api/short-link/v1/stats/access-record")
    public Result<IPage<ShortLinkStatsAccessRecordRespDTO>> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO shortLinkStatsAccessRecordReqDTO) {
        return Results.success(shortLinkStatsService.shortLinkStatsAccessRecord(shortLinkStatsAccessRecordReqDTO));
    }
}