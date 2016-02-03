package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ExamResult;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ExamResult entity.
 */
public interface ExamResultRepository extends JpaRepository<ExamResult,Long> {

}
