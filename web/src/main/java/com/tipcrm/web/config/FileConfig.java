package com.tipcrm.web.config;
import com.tipcrm.config.FileConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {
    @Value("${uploadfile.base-dir}")
    private String baseUrl;

    @Value("${file.max-file-size.avatar}")
    private Long avatarMaxSize;

    @Value("${file.max-file-size.file}")
    private Long fileMaxSize;

    @Bean
    public FileConfiguration fileConfiguration() {
        FileConfiguration fileConfiguration = new FileConfiguration();
        fileConfiguration.setBaseUrl(baseUrl);
        fileConfiguration.setAvatarMaxSize(avatarMaxSize);
        fileConfiguration.setFileMaxSize(fileMaxSize);
        return fileConfiguration;
    }
}
