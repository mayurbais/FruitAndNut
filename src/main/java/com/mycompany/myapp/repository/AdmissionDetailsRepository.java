package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AdmissionDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AdmissionDetails entity.
 */
public interface AdmissionDetailsRepository extends JpaRepository<AdmissionDetails,Long> {

}
