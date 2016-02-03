package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Notice;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Notice entity.
 */
public interface NoticeRepository extends JpaRepository<Notice,Long> {

}
