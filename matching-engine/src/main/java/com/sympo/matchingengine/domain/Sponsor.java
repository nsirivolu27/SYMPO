package com.sympo.matchingengine.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Sponsor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brandName;
    private String targetCategory;
    private String targetRegion;
    private Integer minAudienceSize;
    private Integer minEngagementRate;

    @ElementCollection
    private Set<String> preferredAudienceTags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTargetCategory() {
        return targetCategory;
    }

    public void setTargetCategory(String targetCategory) {
        this.targetCategory = targetCategory;
    }

    public String getTargetRegion() {
        return targetRegion;
    }

    public void setTargetRegion(String targetRegion) {
        this.targetRegion = targetRegion;
    }

    public Integer getMinAudienceSize() {
        return minAudienceSize;
    }

    public void setMinAudienceSize(Integer minAudienceSize) {
        this.minAudienceSize = minAudienceSize;
    }

    public Integer getMinEngagementRate() {
        return minEngagementRate;
    }

    public void setMinEngagementRate(Integer minEngagementRate) {
        this.minEngagementRate = minEngagementRate;
    }

    public Set<String> getPreferredAudienceTags() {
        return preferredAudienceTags;
    }

    public void setPreferredAudienceTags(Set<String> preferredAudienceTags) {
        this.preferredAudienceTags = preferredAudienceTags;
    }
}

