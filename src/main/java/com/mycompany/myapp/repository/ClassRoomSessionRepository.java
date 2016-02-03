package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ClassRoomSession;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ClassRoomSession entity.
 */
public interface ClassRoomSessionRepository extends JpaRepository<ClassRoomSession,Long> {

}
