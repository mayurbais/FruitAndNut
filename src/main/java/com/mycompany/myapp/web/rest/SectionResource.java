package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Section;
import com.mycompany.myapp.repository.SectionRepository;
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
 * REST controller for managing Section.
 */
@RestController
@RequestMapping("/api")
public class SectionResource {

    private final Logger log = LoggerFactory.getLogger(SectionResource.class);
        
    @Inject
    private SectionRepository sectionRepository;
    
    /**
     * POST  /sections -> Create a new section.
     */
    @RequestMapping(value = "/sections",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Section> createSection(@Valid @RequestBody Section section) throws URISyntaxException {
        log.debug("REST request to save Section : {}", section);
        if (section.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("section", "idexists", "A new section cannot already have an ID")).body(null);
        }
        Section result = sectionRepository.save(section);
        return ResponseEntity.created(new URI("/api/sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("section", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sections -> Updates an existing section.
     */
    @RequestMapping(value = "/sections",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Section> updateSection(@Valid @RequestBody Section section) throws URISyntaxException {
        log.debug("REST request to update Section : {}", section);
        if (section.getId() == null) {
            return createSection(section);
        }
        Section result = sectionRepository.save(section);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("section", section.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sections -> get all the sections.
     */
    @RequestMapping(value = "/sections",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Section> getAllSections() {
        log.debug("REST request to get all Sections");
        return sectionRepository.findAll();
            }

    /**
     * GET  /sections/:id -> get the "id" section.
     */
    @RequestMapping(value = "/sections/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Section> getSection(@PathVariable Long id) {
        log.debug("REST request to get Section : {}", id);
        Section section = sectionRepository.findOne(id);
        return Optional.ofNullable(section)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sections/:id -> delete the "id" section.
     */
    @RequestMapping(value = "/sections/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        log.debug("REST request to delete Section : {}", id);
        sectionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("section", id.toString())).build();
    }
}
