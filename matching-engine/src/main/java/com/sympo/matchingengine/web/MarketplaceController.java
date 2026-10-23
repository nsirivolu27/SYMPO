package com.sympo.matchingengine.web;

import com.sympo.matchingengine.domain.Creator;
import com.sympo.matchingengine.domain.Sponsor;
import com.sympo.matchingengine.repository.CreatorRepository;
import com.sympo.matchingengine.repository.SponsorRepository;
import com.sympo.matchingengine.service.MatchingService;
import com.sympo.matchingengine.web.dto.CreatorRequest;
import com.sympo.matchingengine.web.dto.MatchResponse;
import com.sympo.matchingengine.web.dto.SponsorRequest;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MarketplaceController {
    private final CreatorRepository creatorRepository;
    private final SponsorRepository sponsorRepository;
    private final MatchingService matchingService;

    public MarketplaceController(
        CreatorRepository creatorRepository,
        SponsorRepository sponsorRepository,
        MatchingService matchingService
    ) {
        this.creatorRepository = creatorRepository;
        this.sponsorRepository = sponsorRepository;
        this.matchingService = matchingService;
    }

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

    @GetMapping("/creators")
    public List<Creator> creators() {
        return creatorRepository.findAll();
    }

    @GetMapping("/sponsors")
    public List<Sponsor> sponsors() {
        return sponsorRepository.findAll();
    }

    @GetMapping("/matches")
    public List<MatchResponse> recentMatches() {
        return matchingService.recentMatches();
    }

    @PostMapping("/creators")
    public Creator createCreator(@Valid @RequestBody CreatorRequest request) {
        Creator creator = new Creator();
        creator.setDisplayName(request.displayName());
        creator.setPrimaryCategory(request.primaryCategory());
        creator.setRegion(request.region());
        creator.setEngagementRate(request.engagementRate());
        creator.setAudienceSize(request.audienceSize());
        creator.setAudienceTags(request.audienceTags() == null ? new HashSet<>() : new HashSet<>(request.audienceTags()));
        return creatorRepository.save(creator);
    }

    @PostMapping("/sponsors")
    public Sponsor createSponsor(@Valid @RequestBody SponsorRequest request) {
        Sponsor sponsor = new Sponsor();
        sponsor.setBrandName(request.brandName());
        sponsor.setTargetCategory(request.targetCategory());
        sponsor.setTargetRegion(request.targetRegion());
        sponsor.setMinAudienceSize(request.minAudienceSize());
        sponsor.setMinEngagementRate(request.minEngagementRate());
        sponsor.setPreferredAudienceTags(
            request.preferredAudienceTags() == null ? new HashSet<>() : new HashSet<>(request.preferredAudienceTags())
        );
        return sponsorRepository.save(sponsor);
    }

    @PostMapping("/sponsors/{sponsorId}/match")
    public List<MatchResponse> generateMatches(@PathVariable Long sponsorId) {
        return matchingService.generateMatches(sponsorId);
    }
}
