package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DairyTemplate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DairyTemplate entity.
 */
public interface DairyTemplateRepository extends JpaRepository<DairyTemplate,Long> {

}
