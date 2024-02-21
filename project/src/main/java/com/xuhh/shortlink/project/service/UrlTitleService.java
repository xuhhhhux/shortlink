package com.xuhh.shortlink.project.service;

/**
 * URL标题接口层
 */
public interface UrlTitleService {
    /**
     * 根据url获取title
     * @param url
     * @return
     */
    String getTitleByUrl(String url);
}
