package com.xuhh.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuhh.shortlink.project.dao.entity.LinkLocaleStatsDO;
import com.xuhh.shortlink.project.dao.entity.LinkStatsTodayDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * 今日访问统计持久层
 */
public interface LinkStatsTodayMapper extends BaseMapper<LinkStatsTodayDO> {
    /**
     * 记录今日访问数据
     * @param linkStatsTodayDO
     */
    @Insert("INSERT INTO t_link_stats_today (full_short_url, gid, date, today_pv, today_uv, today_uip, create_time, update_time, del_flag) " +
            "VALUES( #{linkStatsTodayDO.fullShortUrl}, #{linkStatsTodayDO.gid}, #{linkStatsTodayDO.date}, #{linkStatsTodayDO.todayPv}, #{linkStatsTodayDO.todayUv}, #{linkStatsTodayDO.todayUip}, NOW(), NOW(), 0) " +
            "ON DUPLICATE KEY UPDATE today_pv = today_pv +  #{linkStatsTodayDO.todayPv}, today_uv = today_uv +  #{linkStatsTodayDO.todayUv}, today_uip = today_uip +  #{linkStatsTodayDO.todayUip};")
    void shortLinkTodayState(@Param("linkStatsTodayDO") LinkStatsTodayDO linkStatsTodayDO);
}
