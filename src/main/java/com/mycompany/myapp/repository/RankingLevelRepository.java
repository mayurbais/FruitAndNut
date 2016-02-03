package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RankingLevel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RankingLevel entity.
 */
public interface RankingLevelRepository extends JpaRepository<RankingLevel,Long> {

}
