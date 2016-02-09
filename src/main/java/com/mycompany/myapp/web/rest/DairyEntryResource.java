package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DairyEntry;
import com.mycompany.myapp.repository.DairyEntryRepository;
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
 * REST controller for managing DairyEntry.
 */
@RestController
@RequestMapping("/api")
public class DairyEntryResource {

    private final Logger log = LoggerFactory.getLogger(DairyEntryResource.class);
        
    @Inject
    private DairyEntryRepository dairyEntryRepository;
    
    /**
     * POST  /dairyEntrys -> Create a new dairyEntry.
     */
    @RequestMapping(value = "/dairyEntrys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DairyEntry> createDairyEntry(@RequestBody DairyEntry dairyEntry) throws URISyntaxException {
        log.debug("REST request to save DairyEntry : {}", dairyEntry);
        if (dairyEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("dairyEntry", "idexists", "A new dairyEntry cannot already have an ID")).body(null);
        }
        DairyEntry result = dairyEntryRepository.save(dairyEntry);
        return ResponseEntity.created(new URI("/api/dairyEntrys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dairyEntry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dairyEntrys -> Updates an existing dairyEntry.
     */
    @RequestMapping(value = "/dairyEntrys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DairyEntry> updateDairyEntry(@RequestBody DairyEntry dairyEntry) throws URISyntaxException {
        log.debug("REST request to update DairyEntry : {}", dairyEntry);
        if (dairyEntry.getId() == null) {
            return createDairyEntry(dairyEntry);
        }
        DairyEntry result = dairyEntryRepository.save(dairyEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dairyEntry", dairyEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dairyEntrys -> get all the dairyEntrys.
     */
    @RequestMapping(value = "/dairyEntrys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DairyEntry> getAllDairyEntrys() {
        log.debug("REST request to get all DairyEntrys");
        return dairyEntryRepository.findAll();
            }

    /**
     * GET  /dairyEntrys/:id -> get the "id" dairyEntry.
     */
    @RequestMapping(value = "/dairyEntrys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DairyEntry> getDairyEntry(@PathVariable Long id) {
        log.debug("REST request to get DairyEntry : {}", id);
        DairyEntry dairyEntry = dairyEntryRepository.findOne(id);
        return Optional.ofNullable(dairyEntry)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dairyEntrys/:id -> delete the "id" dairyEntry.
     */
    @RequestMapping(value = "/dairyEntrys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDairyEntry(@PathVariable Long id) {
        log.debug("REST request to delete DairyEntry : {}", id);
        dairyEntryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dairyEntry", id.toString())).build();
    }
}
