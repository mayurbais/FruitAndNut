package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Attendance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Attendance entity.
 */
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {

}
