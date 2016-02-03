package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.CurrancyLOV;
import com.mycompany.myapp.repository.CurrancyLOVRepository;
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
 * REST controller for managing CurrancyLOV.
 */
@RestController
@RequestMapping("/api")
public class CurrancyLOVResource {

    private final Logger log = LoggerFactory.getLogger(CurrancyLOVResource.class);
        
    @Inject
    private CurrancyLOVRepository currancyLOVRepository;
    
    /**
     * POST  /currancyLOVs -> Create a new currancyLOV.
     */
    @RequestMapping(value = "/currancyLOVs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CurrancyLOV> createCurrancyLOV(@RequestBody CurrancyLOV currancyLOV) throws URISyntaxException {
        log.debug("REST request to save CurrancyLOV : {}", currancyLOV);
        if (currancyLOV.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("currancyLOV", "idexists", "A new currancyLOV cannot already have an ID")).body(null);
        }
        CurrancyLOV result = currancyLOVRepository.save(currancyLOV);
        return ResponseEntity.created(new URI("/api/currancyLOVs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("currancyLOV", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /currancyLOVs -> Updates an existing currancyLOV.
     */
    @RequestMapping(value = "/currancyLOVs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CurrancyLOV> updateCurrancyLOV(@RequestBody CurrancyLOV currancyLOV) throws URISyntaxException {
        log.debug("REST request to update CurrancyLOV : {}", currancyLOV);
        if (currancyLOV.getId() == null) {
            return createCurrancyLOV(currancyLOV);
        }
        CurrancyLOV result = currancyLOVRepository.save(currancyLOV);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("currancyLOV", currancyLOV.getId().toString()))
            .body(result);
    }

    /**
     * GET  /currancyLOVs -> get all the currancyLOVs.
     */
    @RequestMapping(value = "/currancyLOVs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CurrancyLOV> getAllCurrancyLOVs() {
        log.debug("REST request to get all CurrancyLOVs");
        return currancyLOVRepository.findAll();
            }

    /**
     * GET  /currancyLOVs/:id -> get the "id" currancyLOV.
     */
    @RequestMapping(value = "/currancyLOVs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CurrancyLOV> getCurrancyLOV(@PathVariable Long id) {
        log.debug("REST request to get CurrancyLOV : {}", id);
        CurrancyLOV currancyLOV = currancyLOVRepository.findOne(id);
        return Optional.ofNullable(currancyLOV)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /currancyLOVs/:id -> delete the "id" currancyLOV.
     */
    @RequestMapping(value = "/currancyLOVs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCurrancyLOV(@PathVariable Long id) {
        log.debug("REST request to delete CurrancyLOV : {}", id);
        currancyLOVRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("currancyLOV", id.toString())).build();
    }
}
