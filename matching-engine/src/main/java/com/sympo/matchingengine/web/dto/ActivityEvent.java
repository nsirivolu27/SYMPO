package com.sympo.matchingengine.web.dto;

import java.time.Instant;

public record ActivityEvent(
    String type,
    String message,
    Instant createdAt
) {}

