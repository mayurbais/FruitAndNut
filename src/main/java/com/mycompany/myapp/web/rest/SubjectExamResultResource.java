package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.SubjectExamResult;
import com.mycompany.myapp.repository.SubjectExamResultRepository;
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
 * REST controller for managing SubjectExamResult.
 */
@RestController
@RequestMapping("/api")
public class SubjectExamResultResource {

    private final Logger log = LoggerFactory.getLogger(SubjectExamResultResource.class);
        
    @Inject
    private SubjectExamResultRepository subjectExamResultRepository;
    
    /**
     * POST  /subjectExamResults -> Create a new subjectExamResult.
     */
    @RequestMapping(value = "/subjectExamResults",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubjectExamResult> createSubjectExamResult(@RequestBody SubjectExamResult subjectExamResult) throws URISyntaxException {
        log.debug("REST request to save SubjectExamResult : {}", subjectExamResult);
        if (subjectExamResult.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("subjectExamResult", "idexists", "A new subjectExamResult cannot already have an ID")).body(null);
        }
        SubjectExamResult result = subjectExamResultRepository.save(subjectExamResult);
        return ResponseEntity.created(new URI("/api/subjectExamResults/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("subjectExamResult", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subjectExamResults -> Updates an existing subjectExamResult.
     */
    @RequestMapping(value = "/subjectExamResults",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubjectExamResult> updateSubjectExamResult(@RequestBody SubjectExamResult subjectExamResult) throws URISyntaxException {
        log.debug("REST request to update SubjectExamResult : {}", subjectExamResult);
        if (subjectExamResult.getId() == null) {
            return createSubjectExamResult(subjectExamResult);
        }
        SubjectExamResult result = subjectExamResultRepository.save(subjectExamResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("subjectExamResult", subjectExamResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subjectExamResults -> get all the subjectExamResults.
     */
    @RequestMapping(value = "/subjectExamResults",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SubjectExamResult> getAllSubjectExamResults() {
        log.debug("REST request to get all SubjectExamResults");
        return subjectExamResultRepository.findAll();
            }

    /**
     * GET  /subjectExamResults/:id -> get the "id" subjectExamResult.
     */
    @RequestMapping(value = "/subjectExamResults/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubjectExamResult> getSubjectExamResult(@PathVariable Long id) {
        log.debug("REST request to get SubjectExamResult : {}", id);
        SubjectExamResult subjectExamResult = subjectExamResultRepository.findOne(id);
        return Optional.ofNullable(subjectExamResult)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /subjectExamResults/:id -> delete the "id" subjectExamResult.
     */
    @RequestMapping(value = "/subjectExamResults/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubjectExamResult(@PathVariable Long id) {
        log.debug("REST request to delete SubjectExamResult : {}", id);
        subjectExamResultRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subjectExamResult", id.toString())).build();
    }
}
