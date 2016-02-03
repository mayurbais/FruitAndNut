package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.StudentCategory;
import com.mycompany.myapp.repository.StudentCategoryRepository;
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
 * REST controller for managing StudentCategory.
 */
@RestController
@RequestMapping("/api")
public class StudentCategoryResource {

    private final Logger log = LoggerFactory.getLogger(StudentCategoryResource.class);
        
    @Inject
    private StudentCategoryRepository studentCategoryRepository;
    
    /**
     * POST  /studentCategorys -> Create a new studentCategory.
     */
    @RequestMapping(value = "/studentCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentCategory> createStudentCategory(@Valid @RequestBody StudentCategory studentCategory) throws URISyntaxException {
        log.debug("REST request to save StudentCategory : {}", studentCategory);
        if (studentCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("studentCategory", "idexists", "A new studentCategory cannot already have an ID")).body(null);
        }
        StudentCategory result = studentCategoryRepository.save(studentCategory);
        return ResponseEntity.created(new URI("/api/studentCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("studentCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /studentCategorys -> Updates an existing studentCategory.
     */
    @RequestMapping(value = "/studentCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentCategory> updateStudentCategory(@Valid @RequestBody StudentCategory studentCategory) throws URISyntaxException {
        log.debug("REST request to update StudentCategory : {}", studentCategory);
        if (studentCategory.getId() == null) {
            return createStudentCategory(studentCategory);
        }
        StudentCategory result = studentCategoryRepository.save(studentCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("studentCategory", studentCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /studentCategorys -> get all the studentCategorys.
     */
    @RequestMapping(value = "/studentCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StudentCategory> getAllStudentCategorys() {
        log.debug("REST request to get all StudentCategorys");
        return studentCategoryRepository.findAll();
            }

    /**
     * GET  /studentCategorys/:id -> get the "id" studentCategory.
     */
    @RequestMapping(value = "/studentCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentCategory> getStudentCategory(@PathVariable Long id) {
        log.debug("REST request to get StudentCategory : {}", id);
        StudentCategory studentCategory = studentCategoryRepository.findOne(id);
        return Optional.ofNullable(studentCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /studentCategorys/:id -> delete the "id" studentCategory.
     */
    @RequestMapping(value = "/studentCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStudentCategory(@PathVariable Long id) {
        log.debug("REST request to delete StudentCategory : {}", id);
        studentCategoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("studentCategory", id.toString())).build();
    }
}
