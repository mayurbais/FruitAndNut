package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ClassRoomSession;
import com.mycompany.myapp.repository.ClassRoomSessionRepository;
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
 * REST controller for managing ClassRoomSession.
 */
@RestController
@RequestMapping("/api")
public class ClassRoomSessionResource {

    private final Logger log = LoggerFactory.getLogger(ClassRoomSessionResource.class);
        
    @Inject
    private ClassRoomSessionRepository classRoomSessionRepository;
    
    /**
     * POST  /classRoomSessions -> Create a new classRoomSession.
     */
    @RequestMapping(value = "/classRoomSessions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClassRoomSession> createClassRoomSession(@RequestBody ClassRoomSession classRoomSession) throws URISyntaxException {
        log.debug("REST request to save ClassRoomSession : {}", classRoomSession);
        if (classRoomSession.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("classRoomSession", "idexists", "A new classRoomSession cannot already have an ID")).body(null);
        }
        ClassRoomSession result = classRoomSessionRepository.save(classRoomSession);
        return ResponseEntity.created(new URI("/api/classRoomSessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("classRoomSession", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classRoomSessions -> Updates an existing classRoomSession.
     */
    @RequestMapping(value = "/classRoomSessions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClassRoomSession> updateClassRoomSession(@RequestBody ClassRoomSession classRoomSession) throws URISyntaxException {
        log.debug("REST request to update ClassRoomSession : {}", classRoomSession);
        if (classRoomSession.getId() == null) {
            return createClassRoomSession(classRoomSession);
        }
        ClassRoomSession result = classRoomSessionRepository.save(classRoomSession);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("classRoomSession", classRoomSession.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classRoomSessions -> get all the classRoomSessions.
     */
    @RequestMapping(value = "/classRoomSessions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ClassRoomSession> getAllClassRoomSessions() {
        log.debug("REST request to get all ClassRoomSessions");
        return classRoomSessionRepository.findAll();
            }

    /**
     * GET  /classRoomSessions/:id -> get the "id" classRoomSession.
     */
    @RequestMapping(value = "/classRoomSessions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClassRoomSession> getClassRoomSession(@PathVariable Long id) {
        log.debug("REST request to get ClassRoomSession : {}", id);
        ClassRoomSession classRoomSession = classRoomSessionRepository.findOne(id);
        return Optional.ofNullable(classRoomSession)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /classRoomSessions/:id -> delete the "id" classRoomSession.
     */
    @RequestMapping(value = "/classRoomSessions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClassRoomSession(@PathVariable Long id) {
        log.debug("REST request to delete ClassRoomSession : {}", id);
        classRoomSessionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("classRoomSession", id.toString())).build();
    }
}
