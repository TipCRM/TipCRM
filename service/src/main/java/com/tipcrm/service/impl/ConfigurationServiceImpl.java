package com.tipcrm.service.impl;
import com.tipcrm.cache.ConfigurationCache;
import com.tipcrm.dao.entity.Configuration;
import com.tipcrm.dao.repository.ConfigurationRepository;
import com.tipcrm.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService{

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public String get(String key) {
        String value = ConfigurationCache.get(key);
        if (value != null) {
            return value;
        }
        Configuration configuration = configurationRepository.findByKey(key);
        if (configuration == null) {
            return null;
        }
        ConfigurationCache.pushConfiguration(key, configuration.getValue());
        return configuration.getValue();
    }
}
