package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Events;
import com.mycompany.myapp.repository.EventsRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Events.
 */
@RestController
@RequestMapping("/api")
public class EventsResource {

    private final Logger log = LoggerFactory.getLogger(EventsResource.class);
        
    @Inject
    private EventsRepository eventsRepository;
    
    /**
     * POST  /eventss -> Create a new events.
     */
    @RequestMapping(value = "/eventss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Events> createEvents(@RequestBody Events events) throws URISyntaxException {
        log.debug("REST request to save Events : {}", events);
        if (events.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("events", "idexists", "A new events cannot already have an ID")).body(null);
        }
        Events result = eventsRepository.save(events);
        return ResponseEntity.created(new URI("/api/eventss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("events", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eventss -> Updates an existing events.
     */
    @RequestMapping(value = "/eventss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Events> updateEvents(@RequestBody Events events) throws URISyntaxException {
        log.debug("REST request to update Events : {}", events);
        if (events.getId() == null) {
            return createEvents(events);
        }
        Events result = eventsRepository.save(events);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("events", events.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eventss -> get all the eventss.
     */
    @RequestMapping(value = "/eventss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Events> getAllEventss() {
        log.debug("REST request to get all Eventss");
        return eventsRepository.findAll();
            }

    /**
     * GET  /eventss/:id -> get the "id" events.
     */
    @RequestMapping(value = "/eventss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Events> getEvents(@PathVariable Long id) {
        log.debug("REST request to get Events : {}", id);
        Events events = eventsRepository.findOne(id);
        return Optional.ofNullable(events)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    
    /**
     * GET  /eventss/:id -> get the "id" events.
     */
    @RequestMapping(value = "/eventss/getChildEvents")
    @Timed
    public List<Events> getMyChildEvents(@NotNull Long sectionId) {
        log.debug("REST request to get Events for section: {}", sectionId);
        return eventsRepository.findAllBySectionId(sectionId);
        
    }
    
    
    /**
     * DELETE  /eventss/:id -> delete the "id" events.
     */
    @RequestMapping(value = "/eventss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEvents(@PathVariable Long id) {
        log.debug("REST request to delete Events : {}", id);
        eventsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("events", id.toString())).build();
    }
}
