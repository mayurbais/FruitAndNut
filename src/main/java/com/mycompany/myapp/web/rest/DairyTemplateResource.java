package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DairyTemplate;
import com.mycompany.myapp.repository.DairyTemplateRepository;
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
 * REST controller for managing DairyTemplate.
 */
@RestController
@RequestMapping("/api")
public class DairyTemplateResource {

    private final Logger log = LoggerFactory.getLogger(DairyTemplateResource.class);
        
    @Inject
    private DairyTemplateRepository dairyTemplateRepository;
    
    /**
     * POST  /dairyTemplates -> Create a new dairyTemplate.
     */
    @RequestMapping(value = "/dairyTemplates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DairyTemplate> createDairyTemplate(@RequestBody DairyTemplate dairyTemplate) throws URISyntaxException {
        log.debug("REST request to save DairyTemplate : {}", dairyTemplate);
        if (dairyTemplate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dairyTemplate", "idexists", "A new dairyTemplate cannot already have an ID")).body(null);
        }
        DairyTemplate result = dairyTemplateRepository.save(dairyTemplate);
        return ResponseEntity.created(new URI("/api/dairyTemplates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dairyTemplate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dairyTemplates -> Updates an existing dairyTemplate.
     */
    @RequestMapping(value = "/dairyTemplates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DairyTemplate> updateDairyTemplate(@RequestBody DairyTemplate dairyTemplate) throws URISyntaxException {
        log.debug("REST request to update DairyTemplate : {}", dairyTemplate);
        if (dairyTemplate.getId() == null) {
            return createDairyTemplate(dairyTemplate);
        }
        DairyTemplate result = dairyTemplateRepository.save(dairyTemplate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dairyTemplate", dairyTemplate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dairyTemplates -> get all the dairyTemplates.
     */
    @RequestMapping(value = "/dairyTemplates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DairyTemplate> getAllDairyTemplates() {
        log.debug("REST request to get all DairyTemplates");
        return dairyTemplateRepository.findAll();
            }

    /**
     * GET  /dairyTemplates/:id -> get the "id" dairyTemplate.
     */
    @RequestMapping(value = "/dairyTemplates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DairyTemplate> getDairyTemplate(@PathVariable Long id) {
        log.debug("REST request to get DairyTemplate : {}", id);
        DairyTemplate dairyTemplate = dairyTemplateRepository.findOne(id);
        return Optional.ofNullable(dairyTemplate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dairyTemplates/:id -> delete the "id" dairyTemplate.
     */
    @RequestMapping(value = "/dairyTemplates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDairyTemplate(@PathVariable Long id) {
        log.debug("REST request to delete DairyTemplate : {}", id);
        dairyTemplateRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dairyTemplate", id.toString())).build();
    }
}
