package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.CustomAdmissionDetails;
import com.mycompany.myapp.repository.CustomAdmissionDetailsRepository;
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
 * REST controller for managing CustomAdmissionDetails.
 */
@RestController
@RequestMapping("/api")
public class CustomAdmissionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CustomAdmissionDetailsResource.class);
        
    @Inject
    private CustomAdmissionDetailsRepository customAdmissionDetailsRepository;
    
    /**
     * POST  /customAdmissionDetailss -> Create a new customAdmissionDetails.
     */
    @RequestMapping(value = "/customAdmissionDetailss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomAdmissionDetails> createCustomAdmissionDetails(@Valid @RequestBody CustomAdmissionDetails customAdmissionDetails) throws URISyntaxException {
        log.debug("REST request to save CustomAdmissionDetails : {}", customAdmissionDetails);
        if (customAdmissionDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("customAdmissionDetails", "idexists", "A new customAdmissionDetails cannot already have an ID")).body(null);
        }
        CustomAdmissionDetails result = customAdmissionDetailsRepository.save(customAdmissionDetails);
        return ResponseEntity.created(new URI("/api/customAdmissionDetailss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("customAdmissionDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customAdmissionDetailss -> Updates an existing customAdmissionDetails.
     */
    @RequestMapping(value = "/customAdmissionDetailss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomAdmissionDetails> updateCustomAdmissionDetails(@Valid @RequestBody CustomAdmissionDetails customAdmissionDetails) throws URISyntaxException {
        log.debug("REST request to update CustomAdmissionDetails : {}", customAdmissionDetails);
        if (customAdmissionDetails.getId() == null) {
            return createCustomAdmissionDetails(customAdmissionDetails);
        }
        CustomAdmissionDetails result = customAdmissionDetailsRepository.save(customAdmissionDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("customAdmissionDetails", customAdmissionDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customAdmissionDetailss -> get all the customAdmissionDetailss.
     */
    @RequestMapping(value = "/customAdmissionDetailss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CustomAdmissionDetails> getAllCustomAdmissionDetailss() {
        log.debug("REST request to get all CustomAdmissionDetailss");
        return customAdmissionDetailsRepository.findAll();
            }

    /**
     * GET  /customAdmissionDetailss/:id -> get the "id" customAdmissionDetails.
     */
    @RequestMapping(value = "/customAdmissionDetailss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CustomAdmissionDetails> getCustomAdmissionDetails(@PathVariable Long id) {
        log.debug("REST request to get CustomAdmissionDetails : {}", id);
        CustomAdmissionDetails customAdmissionDetails = customAdmissionDetailsRepository.findOne(id);
        return Optional.ofNullable(customAdmissionDetails)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /customAdmissionDetailss/:id -> delete the "id" customAdmissionDetails.
     */
    @RequestMapping(value = "/customAdmissionDetailss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCustomAdmissionDetails(@PathVariable Long id) {
        log.debug("REST request to delete CustomAdmissionDetails : {}", id);
        customAdmissionDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("customAdmissionDetails", id.toString())).build();
    }
}
