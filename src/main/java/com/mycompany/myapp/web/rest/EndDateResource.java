package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.EndDate;
import com.mycompany.myapp.repository.EndDateRepository;
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
 * REST controller for managing EndDate.
 */
@RestController
@RequestMapping("/api")
public class EndDateResource {

    private final Logger log = LoggerFactory.getLogger(EndDateResource.class);
        
    @Inject
    private EndDateRepository endDateRepository;
    
    /**
     * POST  /endDates -> Create a new endDate.
     */
    @RequestMapping(value = "/endDates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EndDate> createEndDate(@RequestBody EndDate endDate) throws URISyntaxException {
        log.debug("REST request to save EndDate : {}", endDate);
        if (endDate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("endDate", "idexists", "A new endDate cannot already have an ID")).body(null);
        }
        EndDate result = endDateRepository.save(endDate);
        return ResponseEntity.created(new URI("/api/endDates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("endDate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /endDates -> Updates an existing endDate.
     */
    @RequestMapping(value = "/endDates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EndDate> updateEndDate(@RequestBody EndDate endDate) throws URISyntaxException {
        log.debug("REST request to update EndDate : {}", endDate);
        if (endDate.getId() == null) {
            return createEndDate(endDate);
        }
        EndDate result = endDateRepository.save(endDate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("endDate", endDate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /endDates -> get all the endDates.
     */
    @RequestMapping(value = "/endDates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EndDate> getAllEndDates() {
        log.debug("REST request to get all EndDates");
        return endDateRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /endDates/:id -> get the "id" endDate.
     */
    @RequestMapping(value = "/endDates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EndDate> getEndDate(@PathVariable Long id) {
        log.debug("REST request to get EndDate : {}", id);
        EndDate endDate = endDateRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(endDate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /endDates/:id -> delete the "id" endDate.
     */
    @RequestMapping(value = "/endDates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEndDate(@PathVariable Long id) {
        log.debug("REST request to delete EndDate : {}", id);
        endDateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("endDate", id.toString())).build();
    }
}
