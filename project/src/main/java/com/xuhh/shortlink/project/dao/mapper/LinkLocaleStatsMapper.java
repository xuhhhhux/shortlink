package com.xuhh.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuhh.shortlink.project.dao.entity.LinkLocaleStatsDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 地区统计持久层
 */
public interface LinkLocaleStatsMapper extends BaseMapper<LinkLocaleStatsDO> {
    @Update("INSERT INTO t_link_locale_stats ( full_short_url, gid, date, cnt, province, city, adcode, country, create_time, update_time, del_flag ) " +
            "VALUES ( #{linkLocaleStats.fullShortUrl}, #{linkLocaleStats.gid}, #{linkLocaleStats.date}, #{linkLocaleStats.cnt}, #{linkLocaleStats.province}, #{linkLocaleStats.city}, #{linkLocaleStats.adcode}, #{linkLocaleStats.country}, NOW(), NOW(), 0 ) " +
            "ON DUPLICATE KEY UPDATE cnt = cnt + #{linkLocaleStats.cnt};")
    void shortLinkStats(@Param("linkLocaleStats") LinkLocaleStatsDO linkLocaleStatsDO);
}
