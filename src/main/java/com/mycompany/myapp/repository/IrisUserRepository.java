package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.IrisUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the IrisUser entity.
 */
public interface IrisUserRepository extends JpaRepository<IrisUser,Long> {

	IrisUser findOneByUserId(Long id);

}
