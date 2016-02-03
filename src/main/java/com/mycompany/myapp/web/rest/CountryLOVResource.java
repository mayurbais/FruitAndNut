package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.CountryLOV;
import com.mycompany.myapp.repository.CountryLOVRepository;
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
 * REST controller for managing CountryLOV.
 */
@RestController
@RequestMapping("/api")
public class CountryLOVResource {

    private final Logger log = LoggerFactory.getLogger(CountryLOVResource.class);
        
    @Inject
    private CountryLOVRepository countryLOVRepository;
    
    /**
     * POST  /countryLOVs -> Create a new countryLOV.
     */
    @RequestMapping(value = "/countryLOVs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CountryLOV> createCountryLOV(@RequestBody CountryLOV countryLOV) throws URISyntaxException {
        log.debug("REST request to save CountryLOV : {}", countryLOV);
        if (countryLOV.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("countryLOV", "idexists", "A new countryLOV cannot already have an ID")).body(null);
        }
        CountryLOV result = countryLOVRepository.save(countryLOV);
        return ResponseEntity.created(new URI("/api/countryLOVs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("countryLOV", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /countryLOVs -> Updates an existing countryLOV.
     */
    @RequestMapping(value = "/countryLOVs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CountryLOV> updateCountryLOV(@RequestBody CountryLOV countryLOV) throws URISyntaxException {
        log.debug("REST request to update CountryLOV : {}", countryLOV);
        if (countryLOV.getId() == null) {
            return createCountryLOV(countryLOV);
        }
        CountryLOV result = countryLOVRepository.save(countryLOV);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("countryLOV", countryLOV.getId().toString()))
            .body(result);
    }

    /**
     * GET  /countryLOVs -> get all the countryLOVs.
     */
    @RequestMapping(value = "/countryLOVs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CountryLOV> getAllCountryLOVs() {
        log.debug("REST request to get all CountryLOVs");
        return countryLOVRepository.findAll();
            }

    /**
     * GET  /countryLOVs/:id -> get the "id" countryLOV.
     */
    @RequestMapping(value = "/countryLOVs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CountryLOV> getCountryLOV(@PathVariable Long id) {
        log.debug("REST request to get CountryLOV : {}", id);
        CountryLOV countryLOV = countryLOVRepository.findOne(id);
        return Optional.ofNullable(countryLOV)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /countryLOVs/:id -> delete the "id" countryLOV.
     */
    @RequestMapping(value = "/countryLOVs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCountryLOV(@PathVariable Long id) {
        log.debug("REST request to delete CountryLOV : {}", id);
        countryLOVRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("countryLOV", id.toString())).build();
    }
}
