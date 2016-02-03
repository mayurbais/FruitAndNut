package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.LanguageLOV;
import com.mycompany.myapp.repository.LanguageLOVRepository;
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
 * REST controller for managing LanguageLOV.
 */
@RestController
@RequestMapping("/api")
public class LanguageLOVResource {

    private final Logger log = LoggerFactory.getLogger(LanguageLOVResource.class);
        
    @Inject
    private LanguageLOVRepository languageLOVRepository;
    
    /**
     * POST  /languageLOVs -> Create a new languageLOV.
     */
    @RequestMapping(value = "/languageLOVs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LanguageLOV> createLanguageLOV(@RequestBody LanguageLOV languageLOV) throws URISyntaxException {
        log.debug("REST request to save LanguageLOV : {}", languageLOV);
        if (languageLOV.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("languageLOV", "idexists", "A new languageLOV cannot already have an ID")).body(null);
        }
        LanguageLOV result = languageLOVRepository.save(languageLOV);
        return ResponseEntity.created(new URI("/api/languageLOVs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("languageLOV", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /languageLOVs -> Updates an existing languageLOV.
     */
    @RequestMapping(value = "/languageLOVs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LanguageLOV> updateLanguageLOV(@RequestBody LanguageLOV languageLOV) throws URISyntaxException {
        log.debug("REST request to update LanguageLOV : {}", languageLOV);
        if (languageLOV.getId() == null) {
            return createLanguageLOV(languageLOV);
        }
        LanguageLOV result = languageLOVRepository.save(languageLOV);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("languageLOV", languageLOV.getId().toString()))
            .body(result);
    }

    /**
     * GET  /languageLOVs -> get all the languageLOVs.
     */
    @RequestMapping(value = "/languageLOVs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LanguageLOV> getAllLanguageLOVs() {
        log.debug("REST request to get all LanguageLOVs");
        return languageLOVRepository.findAll();
            }

    /**
     * GET  /languageLOVs/:id -> get the "id" languageLOV.
     */
    @RequestMapping(value = "/languageLOVs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LanguageLOV> getLanguageLOV(@PathVariable Long id) {
        log.debug("REST request to get LanguageLOV : {}", id);
        LanguageLOV languageLOV = languageLOVRepository.findOne(id);
        return Optional.ofNullable(languageLOV)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /languageLOVs/:id -> delete the "id" languageLOV.
     */
    @RequestMapping(value = "/languageLOVs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLanguageLOV(@PathVariable Long id) {
        log.debug("REST request to delete LanguageLOV : {}", id);
        languageLOVRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("languageLOV", id.toString())).build();
    }
}
