package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.YearlyDairyDescription;
import com.mycompany.myapp.repository.YearlyDairyDescriptionRepository;
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
 * REST controller for managing YearlyDairyDescription.
 */
@RestController
@RequestMapping("/api")
public class YearlyDairyDescriptionResource {

    private final Logger log = LoggerFactory.getLogger(YearlyDairyDescriptionResource.class);
        
    @Inject
    private YearlyDairyDescriptionRepository yearlyDairyDescriptionRepository;
    
    /**
     * POST  /yearlyDairyDescriptions -> Create a new yearlyDairyDescription.
     */
    @RequestMapping(value = "/yearlyDairyDescriptions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YearlyDairyDescription> createYearlyDairyDescription(@RequestBody YearlyDairyDescription yearlyDairyDescription) throws URISyntaxException {
        log.debug("REST request to save YearlyDairyDescription : {}", yearlyDairyDescription);
        if (yearlyDairyDescription.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yearlyDairyDescription", "idexists", "A new yearlyDairyDescription cannot already have an ID")).body(null);
        }
        YearlyDairyDescription result = yearlyDairyDescriptionRepository.save(yearlyDairyDescription);
        return ResponseEntity.created(new URI("/api/yearlyDairyDescriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yearlyDairyDescription", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yearlyDairyDescriptions -> Updates an existing yearlyDairyDescription.
     */
    @RequestMapping(value = "/yearlyDairyDescriptions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YearlyDairyDescription> updateYearlyDairyDescription(@RequestBody YearlyDairyDescription yearlyDairyDescription) throws URISyntaxException {
        log.debug("REST request to update YearlyDairyDescription : {}", yearlyDairyDescription);
        if (yearlyDairyDescription.getId() == null) {
            return createYearlyDairyDescription(yearlyDairyDescription);
        }
        YearlyDairyDescription result = yearlyDairyDescriptionRepository.save(yearlyDairyDescription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yearlyDairyDescription", yearlyDairyDescription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yearlyDairyDescriptions -> get all the yearlyDairyDescriptions.
     */
    @RequestMapping(value = "/yearlyDairyDescriptions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YearlyDairyDescription> getAllYearlyDairyDescriptions() {
        log.debug("REST request to get all YearlyDairyDescriptions");
        return yearlyDairyDescriptionRepository.findAll();
            }

    /**
     * GET  /yearlyDairyDescriptions/:id -> get the "id" yearlyDairyDescription.
     */
    @RequestMapping(value = "/yearlyDairyDescriptions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YearlyDairyDescription> getYearlyDairyDescription(@PathVariable Long id) {
        log.debug("REST request to get YearlyDairyDescription : {}", id);
        YearlyDairyDescription yearlyDairyDescription = yearlyDairyDescriptionRepository.findOne(id);
        return Optional.ofNullable(yearlyDairyDescription)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yearlyDairyDescriptions/:id -> delete the "id" yearlyDairyDescription.
     */
    @RequestMapping(value = "/yearlyDairyDescriptions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYearlyDairyDescription(@PathVariable Long id) {
        log.debug("REST request to delete YearlyDairyDescription : {}", id);
        yearlyDairyDescriptionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yearlyDairyDescription", id.toString())).build();
    }
}
