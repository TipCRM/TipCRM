package com.tipcrm.cache;
import java.util.ArrayList;
import java.util.List;

import com.tipcrm.dao.entity.Configuration;
import org.springframework.util.CollectionUtils;

public class ConfigurationCache {
    private static List<Configuration> configurations = new ArrayList<>();

    public static void pushConfigurations(List<Configuration> configurations) {
        if (CollectionUtils.isEmpty(configurations)) {
            configurations = new ArrayList<>();
        }
        configurations.forEach(ConfigurationCache::pushConfiguration);
    }

    public static void pushConfiguration(Configuration configuration) {
        for (Configuration config : configurations) {
            if (config.getKey().equals(configuration.getKey())) {
                config.setValue(configuration.getValue());
                return;
            }
        }
        configurations.add(configuration);
    }

    public static Configuration get(String key) {
        for (Configuration configuration : configurations) {
            if (configuration.getKey().equals(key)) {
                return configuration;
            }
        }
        return null;
    }
}
