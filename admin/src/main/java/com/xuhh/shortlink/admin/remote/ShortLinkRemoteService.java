package com.xuhh.shortlink.admin.remote;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuhh.shortlink.admin.common.convention.result.Result;
import com.xuhh.shortlink.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.xuhh.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.xuhh.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;

import java.util.HashMap;
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
        requestMap.put("current", shortLinkPageReqDTO.getCurrent());
        requestMap.put("size", shortLinkPageReqDTO.getSize());
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/page", requestMap);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }
}
