package com.sympo.matchingengine.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record SponsorRequest(
    @NotBlank String brandName,
    @NotBlank String targetCategory,
    @NotBlank String targetRegion,
    @NotNull Integer minAudienceSize,
    @NotNull Integer minEngagementRate,
    Set<String> preferredAudienceTags
) {}

