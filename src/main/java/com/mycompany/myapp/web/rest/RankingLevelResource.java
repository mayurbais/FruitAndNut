package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.RankingLevel;
import com.mycompany.myapp.repository.RankingLevelRepository;
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
 * REST controller for managing RankingLevel.
 */
@RestController
@RequestMapping("/api")
public class RankingLevelResource {

    private final Logger log = LoggerFactory.getLogger(RankingLevelResource.class);
        
    @Inject
    private RankingLevelRepository rankingLevelRepository;
    
    /**
     * POST  /rankingLevels -> Create a new rankingLevel.
     */
    @RequestMapping(value = "/rankingLevels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RankingLevel> createRankingLevel(@RequestBody RankingLevel rankingLevel) throws URISyntaxException {
        log.debug("REST request to save RankingLevel : {}", rankingLevel);
        if (rankingLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("rankingLevel", "idexists", "A new rankingLevel cannot already have an ID")).body(null);
        }
        RankingLevel result = rankingLevelRepository.save(rankingLevel);
        return ResponseEntity.created(new URI("/api/rankingLevels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rankingLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rankingLevels -> Updates an existing rankingLevel.
     */
    @RequestMapping(value = "/rankingLevels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RankingLevel> updateRankingLevel(@RequestBody RankingLevel rankingLevel) throws URISyntaxException {
        log.debug("REST request to update RankingLevel : {}", rankingLevel);
        if (rankingLevel.getId() == null) {
            return createRankingLevel(rankingLevel);
        }
        RankingLevel result = rankingLevelRepository.save(rankingLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rankingLevel", rankingLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rankingLevels -> get all the rankingLevels.
     */
    @RequestMapping(value = "/rankingLevels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RankingLevel> getAllRankingLevels() {
        log.debug("REST request to get all RankingLevels");
        return rankingLevelRepository.findAll();
            }

    /**
     * GET  /rankingLevels/:id -> get the "id" rankingLevel.
     */
    @RequestMapping(value = "/rankingLevels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RankingLevel> getRankingLevel(@PathVariable Long id) {
        log.debug("REST request to get RankingLevel : {}", id);
        RankingLevel rankingLevel = rankingLevelRepository.findOne(id);
        return Optional.ofNullable(rankingLevel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rankingLevels/:id -> delete the "id" rankingLevel.
     */
    @RequestMapping(value = "/rankingLevels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRankingLevel(@PathVariable Long id) {
        log.debug("REST request to delete RankingLevel : {}", id);
        rankingLevelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rankingLevel", id.toString())).build();
    }
}
