package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SubjectExamResult;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SubjectExamResult entity.
 */
public interface SubjectExamResultRepository extends JpaRepository<SubjectExamResult,Long> {

}
