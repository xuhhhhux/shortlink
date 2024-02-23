package com.xuhh.shortlink.admin.remote;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuhh.shortlink.admin.common.convention.result.Result;
import com.xuhh.shortlink.admin.common.convention.result.Results;
import com.xuhh.shortlink.admin.remote.dto.req.*;
import com.xuhh.shortlink.admin.remote.dto.resp.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短链接中台远程调用
 */
public interface ShortLinkRemoteService {
    // TODO SpringCloud远程调用

    /**
     * 创建短链接
     * @param shortLinkCreateReqDTO
     * @return
     */
    default Result<ShortLinkCreateRespDTO> createShortLink(ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        String resultStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/create", JSON.toJSONString(shortLinkCreateReqDTO));
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     * 分页查询短链接
     * @param shortLinkPageReqDTO
     * @return
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO shortLinkPageReqDTO) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", shortLinkPageReqDTO.getGid());
        requestMap.put("orderTag", shortLinkPageReqDTO.getOrderTag());
        requestMap.put("current", shortLinkPageReqDTO.getCurrent());
        requestMap.put("size", shortLinkPageReqDTO.getSize());
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/page", requestMap);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     * 查询短链接分组内数量
     * @param gids 分组标识数组
     * @return
     */
    default Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(List<String> gids) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gids", gids);
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/count", requestMap);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     * 根据url查询title
     * @param url
     * @return
     */
    default Result<String> getTitleByUrl(@RequestParam("url") String url) {
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/title?url=" + url);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     * 删除短链接到回收站
     * @param recycleBinSaveReqDTO
     * @return
     */
    default Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO recycleBinSaveReqDTO) {
        String resultStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/save", JSON.toJSONString(recycleBinSaveReqDTO));
        return Results.success();
    }

    /**
     * 短链接回收站分页查询
     * @param shortLinkRecycleBinPageReqDTO
     * @return
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(ShortLinkRecycleBinPageReqDTO shortLinkRecycleBinPageReqDTO) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gids", shortLinkRecycleBinPageReqDTO.getGids());
        requestMap.put("current", shortLinkRecycleBinPageReqDTO.getCurrent());
        requestMap.put("size", shortLinkRecycleBinPageReqDTO.getSize());
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/page", requestMap);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     * 回收站恢复短链接
     * @param recycleBinRecoverReqDTO
     * @return
     */
    default Result<Void> recoverRecycleBin(RecycleBinRecoverReqDTO recycleBinRecoverReqDTO) {
        String resultStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/recover", JSON.toJSONString(recycleBinRecoverReqDTO));
        return Results.success();
    }

    /**
     * 回收站删除短链接
     * @param recycleBinRemoveReqDTO
     * @return
     */
    default Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveReqDTO recycleBinRemoveReqDTO) {
        String resultStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/remove", JSON.toJSONString(recycleBinRemoveReqDTO));
        return Results.success();
    }

    /**
     * 访问单个短链接指定时间内监控数据
     *
     * @param requestParam 访问短链接监控请求参数
     * @return 短链接监控信息
     */
    default Result<ShortLinkStatsRespDTO> oneShortLinkStats(ShortLinkStatsReqDTO requestParam) {
        String resultBodyStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/stats", BeanUtil.beanToMap(requestParam));
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }

    /**
     * 访问分组短链接指定时间内监控数据
     *
     * @param requestParam 访分组问短链接监控请求参数
     * @return 分组短链接监控信息
     */
    default Result<ShortLinkStatsRespDTO> groupShortLinkStats(ShortLinkGroupStatsReqDTO requestParam) {
        String resultBodyStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/stats/group", BeanUtil.beanToMap(requestParam));
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }

    /**
     * 访问单个短链接指定时间内监控访问记录数据
     *
     * @param requestParam 访问短链接监控访问记录请求参数
     * @return 短链接监控访问记录信息
     */
    default Result<IPage<ShortLinkStatsAccessRecordRespDTO>> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam) {
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(requestParam, false, true);
        stringObjectMap.remove("orders");
        stringObjectMap.remove("records");
        String resultBodyStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/stats/access-record", stringObjectMap);
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }

    /**
     * 访问分组短链接指定时间内监控访问记录数据
     *
     * @param requestParam 访问分组短链接监控访问记录请求参数
     * @return 分组短链接监控访问记录信息
     */
    default Result<IPage<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam) {
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(requestParam, false, true);
        stringObjectMap.remove("orders");
        stringObjectMap.remove("records");
        String resultBodyStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/stats/access-record/group", stringObjectMap);
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }
}
