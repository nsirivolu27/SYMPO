package com.sympo.matchingengine.web.dto;

public record MatchResponse(
    Long matchId,
    Long sponsorId,
    String sponsorName,
    Long creatorId,
    String creatorName,
    double score
) {}

