package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Notice;
import com.mycompany.myapp.repository.NoticeRepository;
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
 * REST controller for managing Notice.
 */
@RestController
@RequestMapping("/api")
public class NoticeResource {

    private final Logger log = LoggerFactory.getLogger(NoticeResource.class);
        
    @Inject
    private NoticeRepository noticeRepository;
    
    /**
     * POST  /notices -> Create a new notice.
     */
    @RequestMapping(value = "/notices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Notice> createNotice(@RequestBody Notice notice) throws URISyntaxException {
        log.debug("REST request to save Notice : {}", notice);
        if (notice.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("notice", "idexists", "A new notice cannot already have an ID")).body(null);
        }
        Notice result = noticeRepository.save(notice);
        return ResponseEntity.created(new URI("/api/notices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("notice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notices -> Updates an existing notice.
     */
    @RequestMapping(value = "/notices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Notice> updateNotice(@RequestBody Notice notice) throws URISyntaxException {
        log.debug("REST request to update Notice : {}", notice);
        if (notice.getId() == null) {
            return createNotice(notice);
        }
        Notice result = noticeRepository.save(notice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("notice", notice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notices -> get all the notices.
     */
    @RequestMapping(value = "/notices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Notice> getAllNotices() {
        log.debug("REST request to get all Notices");
        return noticeRepository.findAll();
            }

    /**
     * GET  /notices/:id -> get the "id" notice.
     */
    @RequestMapping(value = "/notices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Notice> getNotice(@PathVariable Long id) {
        log.debug("REST request to get Notice : {}", id);
        Notice notice = noticeRepository.findOne(id);
        return Optional.ofNullable(notice)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /notices/:id -> delete the "id" notice.
     */
    @RequestMapping(value = "/notices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNotice(@PathVariable Long id) {
        log.debug("REST request to delete Notice : {}", id);
        noticeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("notice", id.toString())).build();
    }
}
