package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TimeTable;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TimeTable entity.
 */
public interface TimeTableRepository extends JpaRepository<TimeTable,Long> {

    @Query("select distinct timeTable from TimeTable timeTable left join fetch timeTable.timeSlots left join fetch timeTable.classRoomSessions left join fetch timeTable.sections")
    List<TimeTable> findAllWithEagerRelationships();

    @Query("select timeTable from TimeTable timeTable left join fetch timeTable.timeSlots left join fetch timeTable.classRoomSessions left join fetch timeTable.sections where timeTable.id =:id")
    TimeTable findOneWithEagerRelationships(@Param("id") Long id);

}
