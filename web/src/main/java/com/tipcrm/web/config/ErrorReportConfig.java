package com.tipcrm.web.config;
import io.airbrake.javabrake.Notice;
import io.airbrake.javabrake.Notifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErrorReportConfig {
    @Value("${javabrake.enable}")
    private Boolean enable;
    @Value("${javabrake.projectId}")
    private int projectId;
    @Value("${javabrake.projectKey}")
    private String projectKey;
    @Value("${javabrake.environment}")
    private String environment;

    @Bean
    public Notifier notifier() {
        Notifier notifier = new Notifier(projectId, projectKey);
        notifier.addFilter(
            (Notice notice) -> {
                notice.setContext("environment", environment);
                return notice;
            });
        return notifier;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getProjectKey() {
        return projectKey;
    }

    public void setProjectKey(String projectKey) {
        this.projectKey = projectKey;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
