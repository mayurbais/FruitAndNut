package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Events;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Events entity.
 */
public interface EventsRepository extends JpaRepository<Events,Long> {

	List<Events> findAllBySectionId(Long sectionId);

}
