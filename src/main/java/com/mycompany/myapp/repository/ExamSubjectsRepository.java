package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExamSubjects;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExamSubjects entity.
 */
public interface ExamSubjectsRepository extends JpaRepository<ExamSubjects,Long> {

}
