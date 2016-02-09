package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DairyDescription;
import com.mycompany.myapp.repository.DairyDescriptionRepository;
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
 * REST controller for managing DairyDescription.
 */
@RestController
@RequestMapping("/api")
public class DairyDescriptionResource {

    private final Logger log = LoggerFactory.getLogger(DairyDescriptionResource.class);
        
    @Inject
    private DairyDescriptionRepository dairyDescriptionRepository;
    
    /**
     * POST  /dairyDescriptions -> Create a new dairyDescription.
     */
    @RequestMapping(value = "/dairyDescriptions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DairyDescription> createDairyDescription(@RequestBody DairyDescription dairyDescription) throws URISyntaxException {
        log.debug("REST request to save DairyDescription : {}", dairyDescription);
        if (dairyDescription.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dairyDescription", "idexists", "A new dairyDescription cannot already have an ID")).body(null);
        }
        DairyDescription result = dairyDescriptionRepository.save(dairyDescription);
        return ResponseEntity.created(new URI("/api/dairyDescriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dairyDescription", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dairyDescriptions -> Updates an existing dairyDescription.
     */
    @RequestMapping(value = "/dairyDescriptions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DairyDescription> updateDairyDescription(@RequestBody DairyDescription dairyDescription) throws URISyntaxException {
        log.debug("REST request to update DairyDescription : {}", dairyDescription);
        if (dairyDescription.getId() == null) {
            return createDairyDescription(dairyDescription);
        }
        DairyDescription result = dairyDescriptionRepository.save(dairyDescription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dairyDescription", dairyDescription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dairyDescriptions -> get all the dairyDescriptions.
     */
    @RequestMapping(value = "/dairyDescriptions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DairyDescription> getAllDairyDescriptions() {
        log.debug("REST request to get all DairyDescriptions");
        return dairyDescriptionRepository.findAll();
            }

    /**
     * GET  /dairyDescriptions/:id -> get the "id" dairyDescription.
     */
    @RequestMapping(value = "/dairyDescriptions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DairyDescription> getDairyDescription(@PathVariable Long id) {
        log.debug("REST request to get DairyDescription : {}", id);
        DairyDescription dairyDescription = dairyDescriptionRepository.findOne(id);
        return Optional.ofNullable(dairyDescription)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dairyDescriptions/:id -> delete the "id" dairyDescription.
     */
    @RequestMapping(value = "/dairyDescriptions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDairyDescription(@PathVariable Long id) {
        log.debug("REST request to delete DairyDescription : {}", id);
        dairyDescriptionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dairyDescription", id.toString())).build();
    }
}
