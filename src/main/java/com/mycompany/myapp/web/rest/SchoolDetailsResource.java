package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.SchoolDetails;
import com.mycompany.myapp.repository.SchoolDetailsRepository;
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
 * REST controller for managing SchoolDetails.
 */
@RestController
@RequestMapping("/api")
public class SchoolDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SchoolDetailsResource.class);
        
    @Inject
    private SchoolDetailsRepository schoolDetailsRepository;
    
    /**
     * POST  /schoolDetailss -> Create a new schoolDetails.
     */
    @RequestMapping(value = "/schoolDetailss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SchoolDetails> createSchoolDetails(@Valid @RequestBody SchoolDetails schoolDetails) throws URISyntaxException {
        log.debug("REST request to save SchoolDetails : {}", schoolDetails);
        if (schoolDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("schoolDetails", "idexists", "A new schoolDetails cannot already have an ID")).body(null);
        }
        SchoolDetails result = schoolDetailsRepository.save(schoolDetails);
        return ResponseEntity.created(new URI("/api/schoolDetailss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("schoolDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schoolDetailss -> Updates an existing schoolDetails.
     */
    @RequestMapping(value = "/schoolDetailss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SchoolDetails> updateSchoolDetails(@Valid @RequestBody SchoolDetails schoolDetails) throws URISyntaxException {
        log.debug("REST request to update SchoolDetails : {}", schoolDetails);
        if (schoolDetails.getId() == null) {
            return createSchoolDetails(schoolDetails);
        }
        SchoolDetails result = schoolDetailsRepository.save(schoolDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("schoolDetails", schoolDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schoolDetailss -> get all the schoolDetailss.
     */
    @RequestMapping(value = "/schoolDetailss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SchoolDetails> getAllSchoolDetailss() {
        log.debug("REST request to get all SchoolDetailss");
        return schoolDetailsRepository.findAll();
            }

    /**
     * GET  /schoolDetailss/:id -> get the "id" schoolDetails.
     */
    @RequestMapping(value = "/schoolDetailss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SchoolDetails> getSchoolDetails(@PathVariable Long id) {
        log.debug("REST request to get SchoolDetails : {}", id);
        SchoolDetails schoolDetails = schoolDetailsRepository.findOne(id);
        return Optional.ofNullable(schoolDetails)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /schoolDetailss/:id -> delete the "id" schoolDetails.
     */
    @RequestMapping(value = "/schoolDetailss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSchoolDetails(@PathVariable Long id) {
        log.debug("REST request to delete SchoolDetails : {}", id);
        schoolDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("schoolDetails", id.toString())).build();
    }
}
