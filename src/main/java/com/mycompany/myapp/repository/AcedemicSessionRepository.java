package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AcedemicSession;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AcedemicSession entity.
 */
public interface AcedemicSessionRepository extends JpaRepository<AcedemicSession,Long> {

}
