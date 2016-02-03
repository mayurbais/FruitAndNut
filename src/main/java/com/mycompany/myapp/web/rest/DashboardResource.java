package com.mycompany.myapp.web.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.IrisUser;
import com.mycompany.myapp.domain.Parent;
import com.mycompany.myapp.domain.Student;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.IrisUserRepository;
import com.mycompany.myapp.repository.ParentRepository;
import com.mycompany.myapp.repository.StudentRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.security.SecurityUtils;

@RestController
@RequestMapping("/api")
public class DashboardResource {
    private final Logger log = LoggerFactory.getLogger(AdmissionDetailsResource.class);
    
    @Inject
    StudentRepository studentRepository;

	@Inject
    UserRepository userRepository;
	
	@Inject
	ParentRepository parentRepository;
	
	@Inject
	IrisUserRepository irisUserRepository;
	
	

    /**
     * GET  /admissionDetailss -> get all the admissionDetailss.
     */
    @RequestMapping(value = "/dashboard/getMyChildren",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Student> getMyChildren() {
        log.debug("REST request to get all My Children");
        User u = userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get();
        IrisUser irisuser = irisUserRepository.findOneByUserId(u.getId()); 
        Parent p = parentRepository.findOneByIrisUserId(irisuser.getId());
        List parents = new ArrayList<Parent>();
        parents.add(p);
        return studentRepository.findByParents( parents);
    }
}
