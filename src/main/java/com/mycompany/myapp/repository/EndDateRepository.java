package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EndDate;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EndDate entity.
 */
public interface EndDateRepository extends JpaRepository<EndDate,Long> {

    @Query("select distinct endDate from EndDate endDate left join fetch endDate.timeSlots left join fetch endDate.classRoomSessions left join fetch endDate.sections")
    List<EndDate> findAllWithEagerRelationships();

    @Query("select endDate from EndDate endDate left join fetch endDate.timeSlots left join fetch endDate.classRoomSessions left join fetch endDate.sections where endDate.id =:id")
    EndDate findOneWithEagerRelationships(@Param("id") Long id);

}
