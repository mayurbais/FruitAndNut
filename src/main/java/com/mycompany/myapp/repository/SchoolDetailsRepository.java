package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SchoolDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SchoolDetails entity.
 */
public interface SchoolDetailsRepository extends JpaRepository<SchoolDetails,Long> {

}
