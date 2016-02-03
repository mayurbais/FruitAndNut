package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.SmsSetting;
import com.mycompany.myapp.repository.SmsSettingRepository;
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
 * REST controller for managing SmsSetting.
 */
@RestController
@RequestMapping("/api")
public class SmsSettingResource {

    private final Logger log = LoggerFactory.getLogger(SmsSettingResource.class);
        
    @Inject
    private SmsSettingRepository smsSettingRepository;
    
    /**
     * POST  /smsSettings -> Create a new smsSetting.
     */
    @RequestMapping(value = "/smsSettings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsSetting> createSmsSetting(@RequestBody SmsSetting smsSetting) throws URISyntaxException {
        log.debug("REST request to save SmsSetting : {}", smsSetting);
        if (smsSetting.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("smsSetting", "idexists", "A new smsSetting cannot already have an ID")).body(null);
        }
        SmsSetting result = smsSettingRepository.save(smsSetting);
        return ResponseEntity.created(new URI("/api/smsSettings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("smsSetting", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /smsSettings -> Updates an existing smsSetting.
     */
    @RequestMapping(value = "/smsSettings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsSetting> updateSmsSetting(@RequestBody SmsSetting smsSetting) throws URISyntaxException {
        log.debug("REST request to update SmsSetting : {}", smsSetting);
        if (smsSetting.getId() == null) {
            return createSmsSetting(smsSetting);
        }
        SmsSetting result = smsSettingRepository.save(smsSetting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("smsSetting", smsSetting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /smsSettings -> get all the smsSettings.
     */
    @RequestMapping(value = "/smsSettings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsSetting> getAllSmsSettings() {
        log.debug("REST request to get all SmsSettings");
        return smsSettingRepository.findAll();
            }

    /**
     * GET  /smsSettings/:id -> get the "id" smsSetting.
     */
    @RequestMapping(value = "/smsSettings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsSetting> getSmsSetting(@PathVariable Long id) {
        log.debug("REST request to get SmsSetting : {}", id);
        SmsSetting smsSetting = smsSettingRepository.findOne(id);
        return Optional.ofNullable(smsSetting)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /smsSettings/:id -> delete the "id" smsSetting.
     */
    @RequestMapping(value = "/smsSettings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSmsSetting(@PathVariable Long id) {
        log.debug("REST request to delete SmsSetting : {}", id);
        smsSettingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("smsSetting", id.toString())).build();
    }
}
