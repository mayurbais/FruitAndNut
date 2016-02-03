package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.LanguageLOV;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LanguageLOV entity.
 */
public interface LanguageLOVRepository extends JpaRepository<LanguageLOV,Long> {

}
