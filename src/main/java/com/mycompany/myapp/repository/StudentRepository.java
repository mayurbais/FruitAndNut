package com.mycompany.myapp.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mycompany.myapp.domain.Parent;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.TimeTable;

/**
 * Spring Data JPA repository for the Student entity.
 */
public interface StudentRepository extends JpaRepository<Student,Long> {

	@Query("SELECT s FROM Student s INNER JOIN s.parents p WHERE p IN (:parents)")
	List<Student> findByParents(@Param("parents") Collection<Parent> parents);
	
    @Query("select distinct student from Student student left join fetch student.parents")
    List<Student> findAllWithEagerRelationships();

    @Query("select student from Student student left join fetch student.parents where student.id =:id")
    Student findOneWithEagerRelationships(@Param("id") Long id);

}
