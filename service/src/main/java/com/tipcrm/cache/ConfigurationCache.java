package com.tipcrm.cache;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class ConfigurationCache {
    private static Map<String, String> configurations = Maps.newHashMap();

    public static void pushConfigurations(Map<String, String> configurationMap) {
        if (CollectionUtils.isEmpty(configurations)) {
            configurations = Maps.newHashMap();
        }
        configurations.putAll(configurationMap);
    }
    public static void pushConfiguration(String key, String value) {
        Map<String, String> configuration = new HashMap<>();
        pushConfigurations(configuration);
    }
    public static String get(String key){
        if (StringUtils.isNotBlank(key)) {
            return configurations.get(key);
        }
        return null;
    }
}
