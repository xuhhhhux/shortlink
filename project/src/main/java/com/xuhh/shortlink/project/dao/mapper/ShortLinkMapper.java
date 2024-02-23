package com.xuhh.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xuhh.shortlink.project.dao.entity.ShortLinkDO;
import com.xuhh.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.xuhh.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 短链接管理持久层
 */
public interface ShortLinkMapper extends BaseMapper<ShortLinkDO> {
    /**
     * 更新历史PV,UV,UIP
     */
    @Update("update t_link set total_pv = total_pv + #{pv}, total_uv = total_uv + #{uv}, total_uip = total_uip + #{uip} where full_short_url = #{fullShortUrl} and gid = #{gid}")
    void updateStats(
            @Param("fullShortUrl") String fullShortUrl,
            @Param("gid") String gid,
            @Param("pv") Integer pv,
            @Param("uv") Integer uv,
            @Param("uip") Integer uip
    );

    /**
     * 分页统计短链接
     */
    IPage<ShortLinkDO> pageLink(ShortLinkPageReqDTO shortLinkPageReqDTO);
}
