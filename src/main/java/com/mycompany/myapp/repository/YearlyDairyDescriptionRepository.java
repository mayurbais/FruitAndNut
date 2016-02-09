package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.YearlyDairyDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the YearlyDairyDescription entity.
 */
public interface YearlyDairyDescriptionRepository extends JpaRepository<YearlyDairyDescription,Long> {

}
