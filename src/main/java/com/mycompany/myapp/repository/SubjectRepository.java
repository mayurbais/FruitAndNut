package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Subject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Subject entity.
 */
public interface SubjectRepository extends JpaRepository<Subject,Long> {

}
