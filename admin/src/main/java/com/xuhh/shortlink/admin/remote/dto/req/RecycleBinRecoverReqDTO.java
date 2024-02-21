package com.xuhh.shortlink.admin.remote.dto.req;

import lombok.Data;

@Data
public class RecycleBinRecoverReqDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;
}
