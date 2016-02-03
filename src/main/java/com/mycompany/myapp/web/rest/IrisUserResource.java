package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.IrisUser;
import com.mycompany.myapp.repository.IrisUserRepository;
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
 * REST controller for managing IrisUser.
 */
@RestController
@RequestMapping("/api")
public class IrisUserResource {

    private final Logger log = LoggerFactory.getLogger(IrisUserResource.class);
        
    @Inject
    private IrisUserRepository irisUserRepository;
    
    /**
     * POST  /irisUsers -> Create a new irisUser.
     */
    @RequestMapping(value = "/irisUsers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IrisUser> createIrisUser(@Valid @RequestBody IrisUser irisUser) throws URISyntaxException {
        log.debug("REST request to save IrisUser : {}", irisUser);
        if (irisUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("irisUser", "idexists", "A new irisUser cannot already have an ID")).body(null);
        }
        IrisUser result = irisUserRepository.save(irisUser);
        return ResponseEntity.created(new URI("/api/irisUsers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("irisUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /irisUsers -> Updates an existing irisUser.
     */
    @RequestMapping(value = "/irisUsers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IrisUser> updateIrisUser(@Valid @RequestBody IrisUser irisUser) throws URISyntaxException {
        log.debug("REST request to update IrisUser : {}", irisUser);
        if (irisUser.getId() == null) {
            return createIrisUser(irisUser);
        }
        IrisUser result = irisUserRepository.save(irisUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("irisUser", irisUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /irisUsers -> get all the irisUsers.
     */
    @RequestMapping(value = "/irisUsers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<IrisUser> getAllIrisUsers() {
        log.debug("REST request to get all IrisUsers");
        return irisUserRepository.findAll();
            }

    /**
     * GET  /irisUsers/:id -> get the "id" irisUser.
     */
    @RequestMapping(value = "/irisUsers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IrisUser> getIrisUser(@PathVariable Long id) {
        log.debug("REST request to get IrisUser : {}", id);
        IrisUser irisUser = irisUserRepository.findOne(id);
        return Optional.ofNullable(irisUser)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /irisUsers/:id -> delete the "id" irisUser.
     */
    @RequestMapping(value = "/irisUsers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIrisUser(@PathVariable Long id) {
        log.debug("REST request to delete IrisUser : {}", id);
        irisUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("irisUser", id.toString())).build();
    }
}
