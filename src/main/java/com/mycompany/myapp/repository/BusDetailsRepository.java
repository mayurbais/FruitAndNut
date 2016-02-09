package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BusDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BusDetails entity.
 */
public interface BusDetailsRepository extends JpaRepository<BusDetails,Long> {

}
