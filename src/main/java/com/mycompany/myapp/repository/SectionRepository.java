package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Section;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Section entity.
 */
public interface SectionRepository extends JpaRepository<Section,Long> {

}
