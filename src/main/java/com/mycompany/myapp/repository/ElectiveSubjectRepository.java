package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ElectiveSubject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ElectiveSubject entity.
 */
public interface ElectiveSubjectRepository extends JpaRepository<ElectiveSubject,Long> {

}
