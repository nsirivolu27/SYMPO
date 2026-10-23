package com.sympo.matchingengine.repository;

import com.sympo.matchingengine.domain.MarketplaceMatch;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketplaceMatchRepository extends JpaRepository<MarketplaceMatch, Long> {
    List<MarketplaceMatch> findTop20ByOrderByCreatedAtDesc();
}
