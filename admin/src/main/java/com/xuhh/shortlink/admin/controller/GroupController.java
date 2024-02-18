package com.xuhh.shortlink.admin.controller;

import com.xuhh.shortlink.admin.common.convention.result.Result;
import com.xuhh.shortlink.admin.common.convention.result.Results;
import com.xuhh.shortlink.admin.dto.req.ShortLinkGroupSaveReqDTO;
import com.xuhh.shortlink.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.xuhh.shortlink.admin.dto.resp.ShortLinkGroupRespDTO;
import com.xuhh.shortlink.admin.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接分组控制层
 */
@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    /**
     * 新增短链接分组
     */
    @PostMapping("/api/short-link/v1/group")
    public Result<Void> save(@RequestBody ShortLinkGroupSaveReqDTO shortLinkGroupSaveReqDTO) {
        groupService.saveGroup(shortLinkGroupSaveReqDTO.getName());
        return Results.success();
    }

    /**
     * 查询短链接分组
     */
    @GetMapping("/api/short-link/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup() {
        return Results.success(groupService.listGroup());
    }

    /**
     * 修改短链接分组
     */
    @PutMapping("/api/short-link/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO shortLinkGroupUpdateReqDTO) {
        groupService.updateGroup(shortLinkGroupUpdateReqDTO);
        return Results.success();
    }
}
