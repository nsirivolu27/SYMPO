package com.sympo.matchingengine.config;

import com.sympo.matchingengine.domain.Creator;
import com.sympo.matchingengine.domain.Sponsor;
import com.sympo.matchingengine.repository.CreatorRepository;
import com.sympo.matchingengine.repository.SponsorRepository;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedData(CreatorRepository creatorRepository, SponsorRepository sponsorRepository) {
        return args -> {
            if (creatorRepository.count() > 0 || sponsorRepository.count() > 0) {
                return;
            }

            Creator creator1 = new Creator();
            creator1.setDisplayName("Nia Moves");
            creator1.setPrimaryCategory("Fitness");
            creator1.setRegion("US-East");
            creator1.setEngagementRate(8);
            creator1.setAudienceSize(95000);
            creator1.setAudienceTags(Set.of("wellness", "students", "lifestyle"));

            Creator creator2 = new Creator();
            creator2.setDisplayName("CodeWithArman");
            creator2.setPrimaryCategory("Tech");
            creator2.setRegion("US-East");
            creator2.setEngagementRate(6);
            creator2.setAudienceSize(120000);
            creator2.setAudienceTags(Set.of("developers", "students", "productivity"));

            Creator creator3 = new Creator();
            creator3.setDisplayName("Maya Eats");
            creator3.setPrimaryCategory("Food");
            creator3.setRegion("US-West");
            creator3.setEngagementRate(9);
            creator3.setAudienceSize(87000);
            creator3.setAudienceTags(Set.of("foodies", "travel", "lifestyle"));

            creatorRepository.saveAll(java.util.List.of(creator1, creator2, creator3));

            Sponsor sponsor1 = new Sponsor();
            sponsor1.setBrandName("PulseFuel");
            sponsor1.setTargetCategory("Fitness");
            sponsor1.setTargetRegion("US-East");
            sponsor1.setMinAudienceSize(50000);
            sponsor1.setMinEngagementRate(5);
            sponsor1.setPreferredAudienceTags(Set.of("wellness", "lifestyle"));

            Sponsor sponsor2 = new Sponsor();
            sponsor2.setBrandName("StackSprint");
            sponsor2.setTargetCategory("Tech");
            sponsor2.setTargetRegion("US-East");
            sponsor2.setMinAudienceSize(75000);
            sponsor2.setMinEngagementRate(4);
            sponsor2.setPreferredAudienceTags(Set.of("developers", "students"));

            sponsorRepository.saveAll(java.util.List.of(sponsor1, sponsor2));
        };
    }
}

