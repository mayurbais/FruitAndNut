package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.IrisUser;
import com.mycompany.myapp.domain.Parent;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.IrisUserRepository;
import com.mycompany.myapp.repository.ParentRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.UserService;
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
 * REST controller for managing Parent.
 */
@RestController
@RequestMapping("/api")
public class ParentResource {

    private final Logger log = LoggerFactory.getLogger(ParentResource.class);
        
    @Inject
    private ParentRepository parentRepository;
    
    @Inject
    private IrisUserRepository irisUserRepository;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private UserService userService;
    
    /**
     * POST  /parents -> Create a new parent.
     */
    @RequestMapping(value = "/parents",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Parent> createParent(@RequestBody Parent parent) throws URISyntaxException {
        log.debug("REST request to save Parent : {}", parent);
        if (parent.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("parent", "idexists", "A new parent cannot already have an ID")).body(null);
        }
        
        IrisUser irisUser = parent.getIrisUser();
        
        User user = userService.createUserInformation((irisUser.getFirstName()+"123"),
        					"test@123",irisUser.getFirstName(),irisUser.getLastName(),
        					null,"en");
        
        userService.activateRegistration(user.getActivationKey());
        
        parent.getIrisUser().setUser(user);
        irisUser =  irisUserRepository.save(parent.getIrisUser());
        
        parent.setIrisUser(irisUser);
        
        Parent result = parentRepository.save(parent);
        return ResponseEntity.created(new URI("/api/parents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("parent", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /parents -> Updates an existing parent.
     */
    @RequestMapping(value = "/parents",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Parent> updateParent(@RequestBody Parent parent) throws URISyntaxException {
        log.debug("REST request to update Parent : {}", parent);
        if (parent.getId() == null) {
            return createParent(parent);
        }
        Parent result = parentRepository.save(parent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("parent", parent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /parents -> get all the parents.
     */
    @RequestMapping(value = "/parents",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Parent> getAllParents() {
        log.debug("REST request to get all Parents");
        return parentRepository.findAll();
            }

    /**
     * GET  /parents/:id -> get the "id" parent.
     */
    @RequestMapping(value = "/parents/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Parent> getParent(@PathVariable Long id) {
        log.debug("REST request to get Parent : {}", id);
        Parent parent = parentRepository.findOne(id);
        return Optional.ofNullable(parent)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /parents/:id -> delete the "id" parent.
     */
    @RequestMapping(value = "/parents/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteParent(@PathVariable Long id) {
        log.debug("REST request to delete Parent : {}", id);
        parentRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("parent", id.toString())).build();
    }
}
