package com.zqz.shop.system;

import com.zqz.shop.entity.System;
import com.zqz.shop.service.business.SystemConfigBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 该类用于自动初始化数据库配置到BaseConfig中，以便BaseConfig的子类调用
 */
@Component
class ConfigService {
    private static ConfigService systemConfigService;
    @Autowired
    private SystemConfigBusService configBusService;

    private ConfigService() {

    }

    static ConfigService getSystemConfigService() {
        return systemConfigService;
    }

    @PostConstruct
    public void init() {
        systemConfigService = this;
        systemConfigService.initConfigs();
    }

    /**
     * 根据 prefix 重置该 prefix 下所有设置
     *
     * @param prefix
     */
    public void reloadConfig(String prefix) {
        List<System> list = configBusService.queryAll();
        for (System item : list) {
            // 符合条件，添加
            if (item.getKeyName().startsWith(prefix)) {
                BaseConfig.addConfig(item.getKeyName(), item.getKeyValue());
            }
        }
    }

    /**
     * 读取全部配置
     */
    private void initConfigs() {
        List<System> list = configBusService.queryAll();
        for (System item : list) {
            BaseConfig.addConfig(item.getKeyName(), item.getKeyValue());
        }
    }
}
