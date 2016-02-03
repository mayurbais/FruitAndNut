package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ElectiveSubject;
import com.mycompany.myapp.repository.ElectiveSubjectRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ElectiveSubject.
 */
@RestController
@RequestMapping("/api")
public class ElectiveSubjectResource {

    private final Logger log = LoggerFactory.getLogger(ElectiveSubjectResource.class);
        
    @Inject
    private ElectiveSubjectRepository electiveSubjectRepository;
    
    /**
     * POST  /electiveSubjects -> Create a new electiveSubject.
     */
    @RequestMapping(value = "/electiveSubjects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ElectiveSubject> createElectiveSubject(@Valid @RequestBody ElectiveSubject electiveSubject) throws URISyntaxException {
        log.debug("REST request to save ElectiveSubject : {}", electiveSubject);
        if (electiveSubject.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("electiveSubject", "idexists", "A new electiveSubject cannot already have an ID")).body(null);
        }
        ElectiveSubject result = electiveSubjectRepository.save(electiveSubject);
        return ResponseEntity.created(new URI("/api/electiveSubjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("electiveSubject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /electiveSubjects -> Updates an existing electiveSubject.
     */
    @RequestMapping(value = "/electiveSubjects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ElectiveSubject> updateElectiveSubject(@Valid @RequestBody ElectiveSubject electiveSubject) throws URISyntaxException {
        log.debug("REST request to update ElectiveSubject : {}", electiveSubject);
        if (electiveSubject.getId() == null) {
            return createElectiveSubject(electiveSubject);
        }
        ElectiveSubject result = electiveSubjectRepository.save(electiveSubject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("electiveSubject", electiveSubject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /electiveSubjects -> get all the electiveSubjects.
     */
    @RequestMapping(value = "/electiveSubjects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ElectiveSubject> getAllElectiveSubjects() {
        log.debug("REST request to get all ElectiveSubjects");
        return electiveSubjectRepository.findAll();
            }

    /**
     * GET  /electiveSubjects/:id -> get the "id" electiveSubject.
     */
    @RequestMapping(value = "/electiveSubjects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ElectiveSubject> getElectiveSubject(@PathVariable Long id) {
        log.debug("REST request to get ElectiveSubject : {}", id);
        ElectiveSubject electiveSubject = electiveSubjectRepository.findOne(id);
        return Optional.ofNullable(electiveSubject)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /electiveSubjects/:id -> delete the "id" electiveSubject.
     */
    @RequestMapping(value = "/electiveSubjects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteElectiveSubject(@PathVariable Long id) {
        log.debug("REST request to delete ElectiveSubject : {}", id);
        electiveSubjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("electiveSubject", id.toString())).build();
    }
}
