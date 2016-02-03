package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Session;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Session entity.
 */
public interface SessionRepository extends JpaRepository<Session,Long> {

}
