package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Building;
import com.mycompany.myapp.repository.BuildingRepository;
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
 * REST controller for managing Building.
 */
@RestController
@RequestMapping("/api")
public class BuildingResource {

    private final Logger log = LoggerFactory.getLogger(BuildingResource.class);
        
    @Inject
    private BuildingRepository buildingRepository;
    
    /**
     * POST  /buildings -> Create a new building.
     */
    @RequestMapping(value = "/buildings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Building> createBuilding(@RequestBody Building building) throws URISyntaxException {
        log.debug("REST request to save Building : {}", building);
        if (building.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("building", "idexists", "A new building cannot already have an ID")).body(null);
        }
        Building result = buildingRepository.save(building);
        return ResponseEntity.created(new URI("/api/buildings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("building", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /buildings -> Updates an existing building.
     */
    @RequestMapping(value = "/buildings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Building> updateBuilding(@RequestBody Building building) throws URISyntaxException {
        log.debug("REST request to update Building : {}", building);
        if (building.getId() == null) {
            return createBuilding(building);
        }
        Building result = buildingRepository.save(building);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("building", building.getId().toString()))
            .body(result);
    }

    /**
     * GET  /buildings -> get all the buildings.
     */
    @RequestMapping(value = "/buildings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Building> getAllBuildings() {
        log.debug("REST request to get all Buildings");
        return buildingRepository.findAll();
            }

    /**
     * GET  /buildings/:id -> get the "id" building.
     */
    @RequestMapping(value = "/buildings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Building> getBuilding(@PathVariable Long id) {
        log.debug("REST request to get Building : {}", id);
        Building building = buildingRepository.findOne(id);
        return Optional.ofNullable(building)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /buildings/:id -> delete the "id" building.
     */
    @RequestMapping(value = "/buildings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBuilding(@PathVariable Long id) {
        log.debug("REST request to delete Building : {}", id);
        buildingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("building", id.toString())).build();
    }
}
