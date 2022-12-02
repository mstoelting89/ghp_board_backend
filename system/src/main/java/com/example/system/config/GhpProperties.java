package com.example.system.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("ghp")
@Getter
@Setter
public class GhpProperties {
    public String uploadDir;
    public String emailActivationLink;
    public String emailPasswordResetLink;
    public String emailFromValue;
    public String emailLogoPath;
    public String contactEmail;
    public String dashboardLink;
}
