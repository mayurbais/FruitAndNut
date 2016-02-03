package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Attendance;
import com.mycompany.myapp.repository.AttendanceRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Attendance.
 */
@RestController
@RequestMapping("/api")
public class AttendanceResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceResource.class);
        
    @Inject
    private AttendanceRepository attendanceRepository;
    
    /**
     * POST  /attendances -> Create a new attendance.
     */
    @RequestMapping(value = "/attendances",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attendance> createAttendance(@Valid @RequestBody Attendance attendance) throws URISyntaxException {
        log.debug("REST request to save Attendance : {}", attendance);
        if (attendance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("attendance", "idexists", "A new attendance cannot already have an ID")).body(null);
        }
        Attendance result = attendanceRepository.save(attendance);
        return ResponseEntity.created(new URI("/api/attendances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("attendance", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attendances -> Updates an existing attendance.
     */
    @RequestMapping(value = "/attendances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attendance> updateAttendance(@Valid @RequestBody Attendance attendance) throws URISyntaxException {
        log.debug("REST request to update Attendance : {}", attendance);
        if (attendance.getId() == null) {
            return createAttendance(attendance);
        }
        Attendance result = attendanceRepository.save(attendance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("attendance", attendance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attendances -> get all the attendances.
     */
    @RequestMapping(value = "/attendances",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Attendance> getAllAttendances() {
        log.debug("REST request to get all Attendances");
        return attendanceRepository.findAll();
            }

    /**
     * GET  /attendances/:id -> get the "id" attendance.
     */
    @RequestMapping(value = "/attendances/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Attendance> getAttendance(@PathVariable Long id) {
        log.debug("REST request to get Attendance : {}", id);
        Attendance attendance = attendanceRepository.findOne(id);
        return Optional.ofNullable(attendance)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /attendances/:id -> delete the "id" attendance.
     */
    @RequestMapping(value = "/attendances/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        log.debug("REST request to delete Attendance : {}", id);
        attendanceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("attendance", id.toString())).build();
    }
}
