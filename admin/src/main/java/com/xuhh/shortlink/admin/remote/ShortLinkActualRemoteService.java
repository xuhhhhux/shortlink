package com.xuhh.shortlink.admin.remote;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuhh.shortlink.admin.common.convention.result.Result;
import com.xuhh.shortlink.admin.dto.req.ShortLinkBatchCreateReqDTO;
import com.xuhh.shortlink.admin.dto.resp.ShortLinkBatchCreateRespDTO;
import com.xuhh.shortlink.admin.remote.dto.req.*;
import com.xuhh.shortlink.admin.remote.dto.resp.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("short-link-project")
public interface ShortLinkActualRemoteService {
    /**
     * 创建短链接
     * @param shortLinkCreateReqDTO
     * @return
     */
    @PostMapping("/api/short-link/v1/create")
    Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO);

    /**
     * 批量创建短链接
     *
     * @param requestParam 批量创建短链接请求参数
     * @return 短链接批量创建响应
     */
    @PostMapping("/api/short-link/v1/create/batch")
    Result<ShortLinkBatchCreateRespDTO> batchCreateShortLink(@RequestBody ShortLinkBatchCreateReqDTO requestParam);

    /**
     * 修改短链接
     *
     * @param requestParam 修改短链接请求参数
     */
    @PostMapping("/api/short-link/v1/update")
    void updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam);

    /**
     * 分页查询短链接
     * @param gid
     * @param orderTag 排序类型
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/api/short-link/v1/page")
    Result<Page<ShortLinkPageRespDTO>> pageShortLink(@RequestParam("gid") String gid,
                                                     @RequestParam("orderTag") String orderTag,
                                                     @RequestParam("current") Long current,
                                                     @RequestParam("size") Long size);

    /**
     * 查询短链接分组内数量
     * @param gids 分组标识数组
     * @return
     */
    @GetMapping("/api/short-link/v1/count")
    Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("gids") List<String> gids);

    /**
     * 根据url查询title
     * @param url
     * @return
     */
    @GetMapping("/api/short-link/v1/title")
    Result<String> getTitleByUrl(@RequestParam("url") String url);

    /**
     * 删除短链接到回收站
     * @param recycleBinSaveReqDTO
     * @return
     */
    @PostMapping("/api/short-link/v1/recycle-bin/save")
    Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO);

    /**
     * 短链接回收站分页查询
     * @param gids
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/api/short-link/v1/recycle-bin/page")
    Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(@RequestParam("gids") List<String> gids,
                                                                        @RequestParam("current") Long current,
                                                                        @RequestParam("size") Long size);

    /**
     * 回收站恢复短链接
     * @param recycleBinRecoverReqDTO
     * @return
     */
    @PostMapping("/api/short-link/v1/recycle-bin/recover")
    Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO recycleBinRecoverReqDTO);

    /**
     * 回收站删除短链接
     * @param recycleBinRemoveReqDTO
     * @return
     */
    @PostMapping("/api/short-link/v1/recycle-bin/remove")
    Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO recycleBinRemoveReqDTO);

    /**
     * 访问单个短链接指定时间内监控数据
     * @param fullShortUrl
     * @param gid
     * @param startDate
     * @param endDate
     * @return 短链接监控信息
     */
    @GetMapping("/api/short-link/v1/stats")
    Result<ShortLinkStatsRespDTO> oneShortLinkStats(@RequestParam("fullShortUrl") String fullShortUrl,
                                                            @RequestParam("gid") String gid,
                                                            @RequestParam("startDate") String startDate,
                                                            @RequestParam("endDate") String endDate);

    /**
     * 访问分组短链接指定时间内监控数据
     * @param gid
     * @param startDate
     * @param endDate
     * @return 分组短链接监控信息
     */
    @GetMapping("/api/short-link/v1/stats/group")
    Result<ShortLinkStatsRespDTO> groupShortLinkStats(@RequestParam("gid") String gid,
                                                      @RequestParam("startDate") String startDate,
                                                      @RequestParam("endDate") String endDate);

    /**
     * 访问单个短链接指定时间内监控访问记录数据
     * @param fullShortUrl
     * @param gid
     * @param startDate
     * @param endDate
     * @return 短链接监控访问记录信息
     */
    @GetMapping("/api/short-link/v1/stats/access-record")
    Result<Page<ShortLinkStatsAccessRecordRespDTO>> shortLinkStatsAccessRecord(@RequestParam("fullShortUrl") String fullShortUrl,
                                                                                        @RequestParam("gid") String gid,
                                                                                        @RequestParam("startDate") String startDate,
                                                                                        @RequestParam("endDate") String endDate);

    /**
     * 访问分组短链接指定时间内监控访问记录数据
     * @param gid
     * @param startDate
     * @param endDate
     * @return 分组短链接监控访问记录信息
     */
    @GetMapping("/api/short-link/v1/stats/access-record/group")
    Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(@RequestParam("gid") String gid,
                                                                                             @RequestParam("startDate") String startDate,
                                                                                             @RequestParam("endDate") String endDate);
}
