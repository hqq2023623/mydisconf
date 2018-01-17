package com.disconf.web.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.disconf.web.entity.PermissionEntity;
import com.disconf.web.service.IPermissionService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzj
 * @date 2018/1/4
 */
@Service
public class ShiroFilterChainManager {

    private static Map<String, NamedFilterList> defaultFilterChains;

    @Autowired
    private IPermissionService permissionService;

    @Resource(name = "shiroFilter")
    AbstractShiroFilter shiroFilter;

    @PostConstruct
    public void init() {
        DefaultFilterChainManager filterChainManager = getDefaultFilterChainManager();
        if (defaultFilterChains == null) {
            defaultFilterChains = new LinkedHashMap<>(filterChainManager.getFilterChains());
        }
        filterChainManager.getFilterChains().putAll(defaultFilterChains);
        //2、循环URL Filter 注册filter chain
        List<PermissionEntity> urlFilters = permissionService.selectAll();
        for (PermissionEntity urlFilter : urlFilters) {
            String url = urlFilter.getUrl();
            if (!StringUtils.isEmpty(urlFilter.getName())) {
                filterChainManager.addToChain(url, "perms", urlFilter.getName());
            }

        }
        filterChainManager.addToChain("/**", "authc");
    }

    public void initFilterChains(List<PermissionEntity> permissions) {
        DefaultFilterChainManager filterChainManager = getDefaultFilterChainManager();
        //1、首先删除以前老的filter chain并注册默认的
        filterChainManager.getFilterChains().clear();
        if(defaultFilterChains != null) {
            filterChainManager.getFilterChains().putAll(defaultFilterChains);
        }
        //2、循环URL Filter 注册filter chain
        for (PermissionEntity urlFilter : permissions) {
            String url = urlFilter.getUrl();
            //注册perms filter
            if (!StringUtils.isEmpty(urlFilter.getName())) {
                filterChainManager.addToChain(url, "perms", urlFilter.getName());
            }
        }

        filterChainManager.addToChain("/**", "authc");
    }

    private DefaultFilterChainManager getDefaultFilterChainManager(){
        PathMatchingFilterChainResolver pathMatchingFilterChainResolver = (PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager filterChainManager = ((DefaultFilterChainManager) pathMatchingFilterChainResolver.getFilterChainManager());
        return filterChainManager;
    }
}
