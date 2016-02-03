package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ModuleLOV;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ModuleLOV entity.
 */
public interface ModuleLOVRepository extends JpaRepository<ModuleLOV,Long> {

}
