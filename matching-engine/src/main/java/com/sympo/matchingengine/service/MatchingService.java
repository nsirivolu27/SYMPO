package com.sympo.matchingengine.service;

import com.sympo.matchingengine.domain.Creator;
import com.sympo.matchingengine.domain.MarketplaceMatch;
import com.sympo.matchingengine.domain.Sponsor;
import com.sympo.matchingengine.repository.CreatorRepository;
import com.sympo.matchingengine.repository.MarketplaceMatchRepository;
import com.sympo.matchingengine.repository.SponsorRepository;
import com.sympo.matchingengine.web.dto.ActivityEvent;
import com.sympo.matchingengine.web.dto.MatchResponse;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MatchingService {
    private final SponsorRepository sponsorRepository;
    private final CreatorRepository creatorRepository;
    private final MarketplaceMatchRepository matchRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MatchingService(
        SponsorRepository sponsorRepository,
        CreatorRepository creatorRepository,
        MarketplaceMatchRepository matchRepository,
        SimpMessagingTemplate messagingTemplate
    ) {
        this.sponsorRepository = sponsorRepository;
        this.creatorRepository = creatorRepository;
        this.matchRepository = matchRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public List<MatchResponse> generateMatches(Long sponsorId) {
        Sponsor sponsor = sponsorRepository.findById(sponsorId).orElseThrow();

        List<MarketplaceMatch> persisted = creatorRepository.findAll().stream()
            .map(creator -> toMatch(sponsor, creator))
            .filter(match -> match.getScore() >= 45.0)
            .sorted(Comparator.comparing(MarketplaceMatch::getScore).reversed())
            .limit(10)
            .map(matchRepository::save)
            .toList();

        List<MatchResponse> response = persisted.stream().map(this::toResponse).toList();
        messagingTemplate.convertAndSend(
            "/topic/activity",
            new ActivityEvent("MATCH_GENERATED", "Generated " + response.size() + " matches for " + sponsor.getBrandName(), Instant.now())
        );
        messagingTemplate.convertAndSend("/topic/matches", response);
        return response;
    }

    public List<MatchResponse> recentMatches() {
        return matchRepository.findTop20ByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
    }

    private MarketplaceMatch toMatch(Sponsor sponsor, Creator creator) {
        double score = 0;

        if (sponsor.getTargetCategory().equalsIgnoreCase(creator.getPrimaryCategory())) {
            score += 35;
        }
        if (sponsor.getTargetRegion().equalsIgnoreCase(creator.getRegion())) {
            score += 20;
        }
        if (creator.getAudienceSize() >= sponsor.getMinAudienceSize()) {
            score += 20;
        }
        if (creator.getEngagementRate() >= sponsor.getMinEngagementRate()) {
            score += 15;
        }

        Set<String> overlap = creator.getAudienceTags().stream()
            .filter(tag -> sponsor.getPreferredAudienceTags().contains(tag))
            .collect(Collectors.toSet());
        score += Math.min(overlap.size() * 5, 10);

        MarketplaceMatch match = new MarketplaceMatch();
        match.setSponsor(sponsor);
        match.setCreator(creator);
        match.setScore(score);
        return match;
    }

    private MatchResponse toResponse(MarketplaceMatch match) {
        return new MatchResponse(
            match.getId(),
            match.getSponsor().getId(),
            match.getSponsor().getBrandName(),
            match.getCreator().getId(),
            match.getCreator().getDisplayName(),
            match.getScore()
        );
    }
}
