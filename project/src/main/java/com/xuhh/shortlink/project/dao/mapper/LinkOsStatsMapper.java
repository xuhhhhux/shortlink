package com.xuhh.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuhh.shortlink.project.dao.entity.LinkOsStatsDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface LinkOsStatsMapper extends BaseMapper<LinkOsStatsDO> {
    @Update("INSERT INTO t_link_os_stats ( full_short_url, gid, date, cnt, os, create_time, update_time, del_flag ) " +
            "VALUES ( #{linkOsStatsDO.fullShortUrl}, #{linkOsStatsDO.gid}, #{linkOsStatsDO.date}, #{linkOsStatsDO.cnt}, #{linkOsStatsDO.os}, NOW(), NOW(), 0 ) " +
            "ON DUPLICATE KEY UPDATE cnt = cnt + #{linkOsStatsDO.cnt};")
    void shortLinkStats(@Param("linkOsStatsDO") LinkOsStatsDO linkOsStatsDO);
}
