package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DairyDescription;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DairyDescription entity.
 */
public interface DairyDescriptionRepository extends JpaRepository<DairyDescription,Long> {

}
