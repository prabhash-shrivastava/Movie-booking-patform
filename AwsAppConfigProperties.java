package com.platform.movie.booking.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws.appconfig")
public class AwsAppConfigProperties {
    private String applicationId;
    private String environmentId;
    private String profileId;
    private int refreshRateSeconds;

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }

    public String getEnvironmentId() { return environmentId; }
    public void setEnvironmentId(String environmentId) { this.environmentId = environmentId; }

    public String getProfileId() { return profileId; }
    public void setProfileId(String profileId) { this.profileId = profileId; }

    public int getRefreshRateSeconds() { return refreshRateSeconds; }
    public void setRefreshRateSeconds(int refreshRateSeconds) { this.refreshRateSeconds = refreshRateSeconds; }
}
