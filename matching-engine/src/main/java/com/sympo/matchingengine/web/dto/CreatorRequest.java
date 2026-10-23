package com.sympo.matchingengine.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record CreatorRequest(
    @NotBlank String displayName,
    @NotBlank String primaryCategory,
    @NotBlank String region,
    @NotNull Integer engagementRate,
    @NotNull Integer audienceSize,
    Set<String> audienceTags
) {}

