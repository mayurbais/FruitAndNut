package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CurrancyLOV;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CurrancyLOV entity.
 */
public interface CurrancyLOVRepository extends JpaRepository<CurrancyLOV,Long> {

}
