package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ExamResult;
import com.mycompany.myapp.repository.ExamResultRepository;
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
 * REST controller for managing ExamResult.
 */
@RestController
@RequestMapping("/api")
public class ExamResultResource {

    private final Logger log = LoggerFactory.getLogger(ExamResultResource.class);
        
    @Inject
    private ExamResultRepository examResultRepository;
    
    /**
     * POST  /examResults -> Create a new examResult.
     */
    @RequestMapping(value = "/examResults",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExamResult> createExamResult(@RequestBody ExamResult examResult) throws URISyntaxException {
        log.debug("REST request to save ExamResult : {}", examResult);
        if (examResult.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("examResult", "idexists", "A new examResult cannot already have an ID")).body(null);
        }
        ExamResult result = examResultRepository.save(examResult);
        return ResponseEntity.created(new URI("/api/examResults/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("examResult", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /examResults -> Updates an existing examResult.
     */
    @RequestMapping(value = "/examResults",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExamResult> updateExamResult(@RequestBody ExamResult examResult) throws URISyntaxException {
        log.debug("REST request to update ExamResult : {}", examResult);
        if (examResult.getId() == null) {
            return createExamResult(examResult);
        }
        ExamResult result = examResultRepository.save(examResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("examResult", examResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /examResults -> get all the examResults.
     */
    @RequestMapping(value = "/examResults",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ExamResult> getAllExamResults() {
        log.debug("REST request to get all ExamResults");
        return examResultRepository.findAll();
            }

    /**
     * GET  /examResults/:id -> get the "id" examResult.
     */
    @RequestMapping(value = "/examResults/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExamResult> getExamResult(@PathVariable Long id) {
        log.debug("REST request to get ExamResult : {}", id);
        ExamResult examResult = examResultRepository.findOne(id);
        return Optional.ofNullable(examResult)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /examResults/:id -> delete the "id" examResult.
     */
    @RequestMapping(value = "/examResults/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteExamResult(@PathVariable Long id) {
        log.debug("REST request to delete ExamResult : {}", id);
        examResultRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("examResult", id.toString())).build();
    }
}
