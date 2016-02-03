package com.mycompany.myapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.AdmissionDetails;
import com.mycompany.myapp.domain.Parent;
import com.mycompany.myapp.domain.PrevSchoolInfo;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.repository.AdmissionDetailsRepository;
import com.mycompany.myapp.repository.IrisUserRepository;
import com.mycompany.myapp.repository.ParentRepository;
import com.mycompany.myapp.repository.PrevSchoolInfoRepository;
import com.mycompany.myapp.repository.StudentRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.web.rest.dto.NewAdmissionDetails;
import com.mycompany.myapp.web.rest.util.HeaderUtil;

/**
 * REST controller for managing AdmissionDetails.
 */
@RestController
@RequestMapping("/api")
public class AdmissionDetailsResource {

    private final Logger log = LoggerFactory.getLogger(AdmissionDetailsResource.class);
        
    @Inject
    private AdmissionDetailsRepository admissionDetailsRepository;
    
    @Inject
    private StudentRepository studentRespository;
    
    @Inject
    private ParentRepository parentRepository;
    
    @Inject
    private PrevSchoolInfoRepository prevSchoolInfoRepository;
    
    @Inject
    private IrisUserRepository irisUserRepository;
    
    /**
     * POST  /admissionDetailss -> Create a new admissionDetails.
     */
    @RequestMapping(value = "/admissionDetailss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdmissionDetails> createAdmissionDetails(@Valid @RequestBody AdmissionDetails admissionDetails) throws URISyntaxException {
        log.debug("REST request to save AdmissionDetails : {}", admissionDetails);
        if (admissionDetails.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("admissionDetails", "idexists", "A new admissionDetails cannot already have an ID")).body(null);
        }
        AdmissionDetails result = admissionDetailsRepository.save(admissionDetails);
        return ResponseEntity.created(new URI("/api/admissionDetailss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("admissionDetails", result.getId().toString()))
            .body(result);
    }
    
    
    /**
     * POST  /admissionDetailss -> Create a new admissionDetails.
     */
    @RequestMapping(value = "/newAdmissionDetailss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdmissionDetails> createNewAdmissionDetails(@Valid @RequestBody NewAdmissionDetails newAdmissionDetails) throws URISyntaxException {
        log.debug("REST request to save AdmissionDetails : {}", newAdmissionDetails);
        
        AdmissionDetails resultAdmissionDetails = admissionDetailsRepository.save(newAdmissionDetails.getAdmissionDetails());
        Student resultAdmissionDetailsStudent = studentRespository.save(newAdmissionDetails.getStudent());
        irisUserRepository.save(newAdmissionDetails.getStudent().getIrisUser());
        Parent resultAdmissionDetailsParent = parentRepository.save(newAdmissionDetails.getParent());
        irisUserRepository.save(newAdmissionDetails.getParent().getIrisUser());
        PrevSchoolInfo prevSchoolInfo = prevSchoolInfoRepository.save(newAdmissionDetails.getPrevSchoolInfo());
        
        return ResponseEntity.created(new URI("/api/admissionDetailss/" + resultAdmissionDetails.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("admissionDetails", resultAdmissionDetails.getId().toString()))
            .body(resultAdmissionDetails);
    }
    

    /**
     * PUT  /admissionDetailss -> Updates an existing admissionDetails.
     */
    @RequestMapping(value = "/admissionDetailss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdmissionDetails> updateAdmissionDetails(@Valid @RequestBody AdmissionDetails admissionDetails) throws URISyntaxException {
        log.debug("REST request to update AdmissionDetails : {}", admissionDetails);
        if (admissionDetails.getId() == null) {
            return createAdmissionDetails(admissionDetails);
        }
        AdmissionDetails result = admissionDetailsRepository.save(admissionDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("admissionDetails", admissionDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /admissionDetailss -> get all the admissionDetailss.
     */
    @RequestMapping(value = "/admissionDetailss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AdmissionDetails> getAllAdmissionDetailss() {
        log.debug("REST request to get all AdmissionDetailss");
        return admissionDetailsRepository.findAll();
            }

    /**
     * GET  /admissionDetailss/:id -> get the "id" admissionDetails.
     */
    @RequestMapping(value = "/admissionDetailss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AdmissionDetails> getAdmissionDetails(@PathVariable Long id) {
        log.debug("REST request to get AdmissionDetails : {}", id);
        AdmissionDetails admissionDetails = admissionDetailsRepository.findOne(id);
        return Optional.ofNullable(admissionDetails)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /admissionDetailss/:id -> delete the "id" admissionDetails.
     */
    @RequestMapping(value = "/admissionDetailss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAdmissionDetails(@PathVariable Long id) {
        log.debug("REST request to delete AdmissionDetails : {}", id);
        admissionDetailsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("admissionDetails", id.toString())).build();
    }
}
