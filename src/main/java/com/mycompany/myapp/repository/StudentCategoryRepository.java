package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StudentCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentCategory entity.
 */
public interface StudentCategoryRepository extends JpaRepository<StudentCategory,Long> {

}
