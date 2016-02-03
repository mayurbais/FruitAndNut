package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Building;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Building entity.
 */
public interface BuildingRepository extends JpaRepository<Building,Long> {

}
