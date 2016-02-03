package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CustomAdmissionDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomAdmissionDetails entity.
 */
public interface CustomAdmissionDetailsRepository extends JpaRepository<CustomAdmissionDetails,Long> {

}
