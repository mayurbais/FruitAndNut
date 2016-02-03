package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Exam;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Exam entity.
 */
public interface ExamRepository extends JpaRepository<Exam,Long> {

}
