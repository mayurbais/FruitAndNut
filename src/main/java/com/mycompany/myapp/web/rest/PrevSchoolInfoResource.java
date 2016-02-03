package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.PrevSchoolInfo;
import com.mycompany.myapp.repository.PrevSchoolInfoRepository;
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
 * REST controller for managing PrevSchoolInfo.
 */
@RestController
@RequestMapping("/api")
public class PrevSchoolInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrevSchoolInfoResource.class);
        
    @Inject
    private PrevSchoolInfoRepository prevSchoolInfoRepository;
    
    /**
     * POST  /prevSchoolInfos -> Create a new prevSchoolInfo.
     */
    @RequestMapping(value = "/prevSchoolInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrevSchoolInfo> createPrevSchoolInfo(@RequestBody PrevSchoolInfo prevSchoolInfo) throws URISyntaxException {
        log.debug("REST request to save PrevSchoolInfo : {}", prevSchoolInfo);
        if (prevSchoolInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prevSchoolInfo", "idexists", "A new prevSchoolInfo cannot already have an ID")).body(null);
        }
        PrevSchoolInfo result = prevSchoolInfoRepository.save(prevSchoolInfo);
        return ResponseEntity.created(new URI("/api/prevSchoolInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prevSchoolInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prevSchoolInfos -> Updates an existing prevSchoolInfo.
     */
    @RequestMapping(value = "/prevSchoolInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrevSchoolInfo> updatePrevSchoolInfo(@RequestBody PrevSchoolInfo prevSchoolInfo) throws URISyntaxException {
        log.debug("REST request to update PrevSchoolInfo : {}", prevSchoolInfo);
        if (prevSchoolInfo.getId() == null) {
            return createPrevSchoolInfo(prevSchoolInfo);
        }
        PrevSchoolInfo result = prevSchoolInfoRepository.save(prevSchoolInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prevSchoolInfo", prevSchoolInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prevSchoolInfos -> get all the prevSchoolInfos.
     */
    @RequestMapping(value = "/prevSchoolInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrevSchoolInfo> getAllPrevSchoolInfos() {
        log.debug("REST request to get all PrevSchoolInfos");
        return prevSchoolInfoRepository.findAll();
            }

    /**
     * GET  /prevSchoolInfos/:id -> get the "id" prevSchoolInfo.
     */
    @RequestMapping(value = "/prevSchoolInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrevSchoolInfo> getPrevSchoolInfo(@PathVariable Long id) {
        log.debug("REST request to get PrevSchoolInfo : {}", id);
        PrevSchoolInfo prevSchoolInfo = prevSchoolInfoRepository.findOne(id);
        return Optional.ofNullable(prevSchoolInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prevSchoolInfos/:id -> delete the "id" prevSchoolInfo.
     */
    @RequestMapping(value = "/prevSchoolInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrevSchoolInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrevSchoolInfo : {}", id);
        prevSchoolInfoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prevSchoolInfo", id.toString())).build();
    }
}
