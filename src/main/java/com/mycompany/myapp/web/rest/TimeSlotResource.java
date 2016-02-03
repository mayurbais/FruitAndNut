package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.TimeSlot;
import com.mycompany.myapp.repository.TimeSlotRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TimeSlot.
 */
@RestController
@RequestMapping("/api")
public class TimeSlotResource {

    private final Logger log = LoggerFactory.getLogger(TimeSlotResource.class);
        
    @Inject
    private TimeSlotRepository timeSlotRepository;
    
    /**
     * POST  /timeSlots -> Create a new timeSlot.
     */
    @RequestMapping(value = "/timeSlots",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeSlot> createTimeSlot(@RequestBody TimeSlot timeSlot) throws URISyntaxException {
        log.debug("REST request to save TimeSlot : {}", timeSlot);
        if (timeSlot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("timeSlot", "idexists", "A new timeSlot cannot already have an ID")).body(null);
        }
        TimeSlot result = timeSlotRepository.save(timeSlot);
        return ResponseEntity.created(new URI("/api/timeSlots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeSlot", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timeSlots -> Updates an existing timeSlot.
     */
    @RequestMapping(value = "/timeSlots",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeSlot> updateTimeSlot(@RequestBody TimeSlot timeSlot) throws URISyntaxException {
        log.debug("REST request to update TimeSlot : {}", timeSlot);
        if (timeSlot.getId() == null) {
            return createTimeSlot(timeSlot);
        }
        TimeSlot result = timeSlotRepository.save(timeSlot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeSlot", timeSlot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timeSlots -> get all the timeSlots.
     */
    @RequestMapping(value = "/timeSlots",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TimeSlot> getAllTimeSlots() {
        log.debug("REST request to get all TimeSlots");
        return timeSlotRepository.findAll();
            }

    /**
     * GET  /timeSlots/:id -> get the "id" timeSlot.
     */
    @RequestMapping(value = "/timeSlots/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeSlot> getTimeSlot(@PathVariable Long id) {
        log.debug("REST request to get TimeSlot : {}", id);
        TimeSlot timeSlot = timeSlotRepository.findOne(id);
        return Optional.ofNullable(timeSlot)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /timeSlots/:id -> delete the "id" timeSlot.
     */
    @RequestMapping(value = "/timeSlots/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long id) {
        log.debug("REST request to delete TimeSlot : {}", id);
        timeSlotRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("timeSlot", id.toString())).build();
    }
}
