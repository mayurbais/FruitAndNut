package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ModuleLOV;
import com.mycompany.myapp.repository.ModuleLOVRepository;
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
 * REST controller for managing ModuleLOV.
 */
@RestController
@RequestMapping("/api")
public class ModuleLOVResource {

    private final Logger log = LoggerFactory.getLogger(ModuleLOVResource.class);
        
    @Inject
    private ModuleLOVRepository moduleLOVRepository;
    
    /**
     * POST  /moduleLOVs -> Create a new moduleLOV.
     */
    @RequestMapping(value = "/moduleLOVs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ModuleLOV> createModuleLOV(@RequestBody ModuleLOV moduleLOV) throws URISyntaxException {
        log.debug("REST request to save ModuleLOV : {}", moduleLOV);
        if (moduleLOV.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("moduleLOV", "idexists", "A new moduleLOV cannot already have an ID")).body(null);
        }
        ModuleLOV result = moduleLOVRepository.save(moduleLOV);
        return ResponseEntity.created(new URI("/api/moduleLOVs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("moduleLOV", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /moduleLOVs -> Updates an existing moduleLOV.
     */
    @RequestMapping(value = "/moduleLOVs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ModuleLOV> updateModuleLOV(@RequestBody ModuleLOV moduleLOV) throws URISyntaxException {
        log.debug("REST request to update ModuleLOV : {}", moduleLOV);
        if (moduleLOV.getId() == null) {
            return createModuleLOV(moduleLOV);
        }
        ModuleLOV result = moduleLOVRepository.save(moduleLOV);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("moduleLOV", moduleLOV.getId().toString()))
            .body(result);
    }

    /**
     * GET  /moduleLOVs -> get all the moduleLOVs.
     */
    @RequestMapping(value = "/moduleLOVs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ModuleLOV> getAllModuleLOVs() {
        log.debug("REST request to get all ModuleLOVs");
        return moduleLOVRepository.findAll();
            }

    /**
     * GET  /moduleLOVs/:id -> get the "id" moduleLOV.
     */
    @RequestMapping(value = "/moduleLOVs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ModuleLOV> getModuleLOV(@PathVariable Long id) {
        log.debug("REST request to get ModuleLOV : {}", id);
        ModuleLOV moduleLOV = moduleLOVRepository.findOne(id);
        return Optional.ofNullable(moduleLOV)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /moduleLOVs/:id -> delete the "id" moduleLOV.
     */
    @RequestMapping(value = "/moduleLOVs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteModuleLOV(@PathVariable Long id) {
        log.debug("REST request to delete ModuleLOV : {}", id);
        moduleLOVRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("moduleLOV", id.toString())).build();
    }
}
