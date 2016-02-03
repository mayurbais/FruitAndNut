package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CountryLOV;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CountryLOV entity.
 */
public interface CountryLOVRepository extends JpaRepository<CountryLOV,Long> {

}
