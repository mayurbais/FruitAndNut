package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.PrevSchoolInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PrevSchoolInfo entity.
 */
public interface PrevSchoolInfoRepository extends JpaRepository<PrevSchoolInfo,Long> {

}
