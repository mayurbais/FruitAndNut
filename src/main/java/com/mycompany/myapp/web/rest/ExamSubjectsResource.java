package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ExamSubjects;
import com.mycompany.myapp.repository.ExamSubjectsRepository;
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
 * REST controller for managing ExamSubjects.
 */
@RestController
@RequestMapping("/api")
public class ExamSubjectsResource {

    private final Logger log = LoggerFactory.getLogger(ExamSubjectsResource.class);
        
    @Inject
    private ExamSubjectsRepository examSubjectsRepository;
    
    /**
     * POST  /examSubjectss -> Create a new examSubjects.
     */
    @RequestMapping(value = "/examSubjectss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExamSubjects> createExamSubjects(@RequestBody ExamSubjects examSubjects) throws URISyntaxException {
        log.debug("REST request to save ExamSubjects : {}", examSubjects);
        if (examSubjects.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("examSubjects", "idexists", "A new examSubjects cannot already have an ID")).body(null);
        }
        ExamSubjects result = examSubjectsRepository.save(examSubjects);
        return ResponseEntity.created(new URI("/api/examSubjectss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("examSubjects", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /examSubjectss -> Updates an existing examSubjects.
     */
    @RequestMapping(value = "/examSubjectss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExamSubjects> updateExamSubjects(@RequestBody ExamSubjects examSubjects) throws URISyntaxException {
        log.debug("REST request to update ExamSubjects : {}", examSubjects);
        if (examSubjects.getId() == null) {
            return createExamSubjects(examSubjects);
        }
        ExamSubjects result = examSubjectsRepository.save(examSubjects);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("examSubjects", examSubjects.getId().toString()))
            .body(result);
    }

    /**
     * GET  /examSubjectss -> get all the examSubjectss.
     */
    @RequestMapping(value = "/examSubjectss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ExamSubjects> getAllExamSubjectss() {
        log.debug("REST request to get all ExamSubjectss");
        return examSubjectsRepository.findAll();
            }

    /**
     * GET  /examSubjectss/:id -> get the "id" examSubjects.
     */
    @RequestMapping(value = "/examSubjectss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExamSubjects> getExamSubjects(@PathVariable Long id) {
        log.debug("REST request to get ExamSubjects : {}", id);
        ExamSubjects examSubjects = examSubjectsRepository.findOne(id);
        return Optional.ofNullable(examSubjects)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /examSubjectss/:id -> delete the "id" examSubjects.
     */
    @RequestMapping(value = "/examSubjectss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteExamSubjects(@PathVariable Long id) {
        log.debug("REST request to delete ExamSubjects : {}", id);
        examSubjectsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("examSubjects", id.toString())).build();
    }
}
