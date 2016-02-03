package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.AcedemicSession;
import com.mycompany.myapp.repository.AcedemicSessionRepository;
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
 * REST controller for managing AcedemicSession.
 */
@RestController
@RequestMapping("/api")
public class AcedemicSessionResource {

    private final Logger log = LoggerFactory.getLogger(AcedemicSessionResource.class);
        
    @Inject
    private AcedemicSessionRepository acedemicSessionRepository;
    
    /**
     * POST  /acedemicSessions -> Create a new acedemicSession.
     */
    @RequestMapping(value = "/acedemicSessions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AcedemicSession> createAcedemicSession(@RequestBody AcedemicSession acedemicSession) throws URISyntaxException {
        log.debug("REST request to save AcedemicSession : {}", acedemicSession);
        if (acedemicSession.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("acedemicSession", "idexists", "A new acedemicSession cannot already have an ID")).body(null);
        }
        AcedemicSession result = acedemicSessionRepository.save(acedemicSession);
        return ResponseEntity.created(new URI("/api/acedemicSessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("acedemicSession", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acedemicSessions -> Updates an existing acedemicSession.
     */
    @RequestMapping(value = "/acedemicSessions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AcedemicSession> updateAcedemicSession(@RequestBody AcedemicSession acedemicSession) throws URISyntaxException {
        log.debug("REST request to update AcedemicSession : {}", acedemicSession);
        if (acedemicSession.getId() == null) {
            return createAcedemicSession(acedemicSession);
        }
        AcedemicSession result = acedemicSessionRepository.save(acedemicSession);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("acedemicSession", acedemicSession.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acedemicSessions -> get all the acedemicSessions.
     */
    @RequestMapping(value = "/acedemicSessions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AcedemicSession> getAllAcedemicSessions() {
        log.debug("REST request to get all AcedemicSessions");
        return acedemicSessionRepository.findAll();
            }

    /**
     * GET  /acedemicSessions/:id -> get the "id" acedemicSession.
     */
    @RequestMapping(value = "/acedemicSessions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AcedemicSession> getAcedemicSession(@PathVariable Long id) {
        log.debug("REST request to get AcedemicSession : {}", id);
        AcedemicSession acedemicSession = acedemicSessionRepository.findOne(id);
        return Optional.ofNullable(acedemicSession)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /acedemicSessions/:id -> delete the "id" acedemicSession.
     */
    @RequestMapping(value = "/acedemicSessions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAcedemicSession(@PathVariable Long id) {
        log.debug("REST request to delete AcedemicSession : {}", id);
        acedemicSessionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("acedemicSession", id.toString())).build();
    }
}
