package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.BusDetails;
import com.mycompany.myapp.repository.BusDetailsRepository;
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
 * REST controller for managing BusDetails.
 */
@RestController
@RequestMapping("/api")
public class BusDetailsResource {

    private final Logger log = LoggerFactory.getLogger(BusDetailsResource.class);
        
    @Inject
    private BusDetailsRepository busDetailsRepository;
    
    /**
     * POST  /busDetailss -> Create a new busDetails.
     */
    @RequestMapping(value = "/busDetailss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BusDetails> createBusDetails(@RequestBody BusDetails busDetails) throws URISyntaxException {
        log.debug("REST request to save BusDetails : {}", busDetails);
        if (busDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("busDetails", "idexists", "A new busDetails cannot already have an ID")).body(null);
        }
        BusDetails result = busDetailsRepository.save(busDetails);
        return ResponseEntity.created(new URI("/api/busDetailss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("busDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /busDetailss -> Updates an existing busDetails.
     */
    @RequestMapping(value = "/busDetailss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BusDetails> updateBusDetails(@RequestBody BusDetails busDetails) throws URISyntaxException {
        log.debug("REST request to update BusDetails : {}", busDetails);
        if (busDetails.getId() == null) {
            return createBusDetails(busDetails);
        }
        BusDetails result = busDetailsRepository.save(busDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("busDetails", busDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /busDetailss -> get all the busDetailss.
     */
    @RequestMapping(value = "/busDetailss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BusDetails> getAllBusDetailss() {
        log.debug("REST request to get all BusDetailss");
        return busDetailsRepository.findAll();
            }

    /**
     * GET  /busDetailss/:id -> get the "id" busDetails.
     */
    @RequestMapping(value = "/busDetailss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BusDetails> getBusDetails(@PathVariable Long id) {
        log.debug("REST request to get BusDetails : {}", id);
        BusDetails busDetails = busDetailsRepository.findOne(id);
        return Optional.ofNullable(busDetails)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /busDetailss/:id -> delete the "id" busDetails.
     */
    @RequestMapping(value = "/busDetailss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBusDetails(@PathVariable Long id) {
        log.debug("REST request to delete BusDetails : {}", id);
        busDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("busDetails", id.toString())).build();
    }
}
