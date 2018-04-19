package com.tipcrm.service.impl;
import com.tipcrm.cache.ConfigurationCache;
import com.tipcrm.constant.ConfigurationItems;
import com.tipcrm.dao.entity.Configuration;
import com.tipcrm.dao.repository.ConfigurationRepository;
import com.tipcrm.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Override
    public Configuration get(String key) {
        Configuration configuration = ConfigurationCache.get(key);
        if (configuration != null) {
            return configuration;
        }
        configuration = configurationRepository.findByKey(key);
        if (configuration == null) {
            return null;
        }
        ConfigurationCache.pushConfiguration(configuration);
        return configuration;
    }

    @Override
    public void set(String key, String value) {
        Configuration configuration = get(key);
        configuration.setValue(value);
        configurationRepository.save(configuration);
        ConfigurationCache.pushConfiguration(configuration);
    }

    @Override
    public Integer generateNewWorkNo() {
        Configuration configuration = get(ConfigurationItems.WORK_NO.name());
        Integer workNo = Integer.valueOf(configuration.getValue());
        configuration.setValue(String.valueOf(workNo + 1));
        configurationRepository.save(configuration);
        ConfigurationCache.pushConfiguration(configuration);
        return workNo;
    }
}
