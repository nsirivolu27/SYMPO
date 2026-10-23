package com.sympo.matchingengine.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String displayName;
    private String primaryCategory;
    private String region;
    private Integer engagementRate;
    private Integer audienceSize;

    @ElementCollection
    private Set<String> audienceTags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPrimaryCategory() {
        return primaryCategory;
    }

    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getEngagementRate() {
        return engagementRate;
    }

    public void setEngagementRate(Integer engagementRate) {
        this.engagementRate = engagementRate;
    }

    public Integer getAudienceSize() {
        return audienceSize;
    }

    public void setAudienceSize(Integer audienceSize) {
        this.audienceSize = audienceSize;
    }

    public Set<String> getAudienceTags() {
        return audienceTags;
    }

    public void setAudienceTags(Set<String> audienceTags) {
        this.audienceTags = audienceTags;
    }
}

