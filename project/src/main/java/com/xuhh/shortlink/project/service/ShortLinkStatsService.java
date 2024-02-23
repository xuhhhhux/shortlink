package com.xuhh.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuhh.shortlink.project.common.convention.result.Result;
import com.xuhh.shortlink.project.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.xuhh.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkStatsRespDTO;

/**
 * 短链接监控接口层
 */
public interface ShortLinkStatsService {
    /**
     * 获取单个短链接监控数据
     *
     * @param requestParam 获取短链接监控数据入参
     * @return 短链接监控数据
     */
    ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO requestParam);

    /**
     * 访问单个短链接指定时间访问记录监控数据
     * @param shortLinkStatsAccessRecordReqDTO
     * @return 短链接访问记录监控数据
     */
    IPage<ShortLinkStatsAccessRecordRespDTO> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO shortLinkStatsAccessRecordReqDTO);
}
