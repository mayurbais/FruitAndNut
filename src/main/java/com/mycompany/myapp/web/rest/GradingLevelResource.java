package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.GradingLevel;
import com.mycompany.myapp.repository.GradingLevelRepository;
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
 * REST controller for managing GradingLevel.
 */
@RestController
@RequestMapping("/api")
public class GradingLevelResource {

    private final Logger log = LoggerFactory.getLogger(GradingLevelResource.class);
        
    @Inject
    private GradingLevelRepository gradingLevelRepository;
    
    /**
     * POST  /gradingLevels -> Create a new gradingLevel.
     */
    @RequestMapping(value = "/gradingLevels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GradingLevel> createGradingLevel(@RequestBody GradingLevel gradingLevel) throws URISyntaxException {
        log.debug("REST request to save GradingLevel : {}", gradingLevel);
        if (gradingLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("gradingLevel", "idexists", "A new gradingLevel cannot already have an ID")).body(null);
        }
        GradingLevel result = gradingLevelRepository.save(gradingLevel);
        return ResponseEntity.created(new URI("/api/gradingLevels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gradingLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gradingLevels -> Updates an existing gradingLevel.
     */
    @RequestMapping(value = "/gradingLevels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GradingLevel> updateGradingLevel(@RequestBody GradingLevel gradingLevel) throws URISyntaxException {
        log.debug("REST request to update GradingLevel : {}", gradingLevel);
        if (gradingLevel.getId() == null) {
            return createGradingLevel(gradingLevel);
        }
        GradingLevel result = gradingLevelRepository.save(gradingLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gradingLevel", gradingLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gradingLevels -> get all the gradingLevels.
     */
    @RequestMapping(value = "/gradingLevels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GradingLevel> getAllGradingLevels() {
        log.debug("REST request to get all GradingLevels");
        return gradingLevelRepository.findAll();
            }

    /**
     * GET  /gradingLevels/:id -> get the "id" gradingLevel.
     */
    @RequestMapping(value = "/gradingLevels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GradingLevel> getGradingLevel(@PathVariable Long id) {
        log.debug("REST request to get GradingLevel : {}", id);
        GradingLevel gradingLevel = gradingLevelRepository.findOne(id);
        return Optional.ofNullable(gradingLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gradingLevels/:id -> delete the "id" gradingLevel.
     */
    @RequestMapping(value = "/gradingLevels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGradingLevel(@PathVariable Long id) {
        log.debug("REST request to delete GradingLevel : {}", id);
        gradingLevelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gradingLevel", id.toString())).build();
    }
}
