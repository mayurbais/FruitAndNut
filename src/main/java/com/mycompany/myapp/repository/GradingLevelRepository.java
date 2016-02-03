package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GradingLevel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GradingLevel entity.
 */
public interface GradingLevelRepository extends JpaRepository<GradingLevel,Long> {

}
