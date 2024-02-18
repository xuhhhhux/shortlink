package com.xuhh.shortlink.admin.dto.req;

import lombok.Data;

@Data
public class ShortLinkGroupUpdateReqDTO {
    /**
     * 分组标识
     */
    private String gid;
    /**
     * 分组名称
     */
    private String name;
}
