package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.DairyEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DairyEntry entity.
 */
public interface DairyEntryRepository extends JpaRepository<DairyEntry,Long> {

}
