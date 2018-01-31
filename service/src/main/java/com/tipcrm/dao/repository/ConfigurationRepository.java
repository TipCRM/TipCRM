package com.tipcrm.dao.repository;
import com.tipcrm.dao.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer>{

    Configuration findByKey(String key);
}
