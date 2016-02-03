package com.mycompany.myapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

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
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.IrisUser;
import com.mycompany.myapp.domain.Teacher;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.EmployeeRepository;
import com.mycompany.myapp.repository.IrisUserRepository;
import com.mycompany.myapp.repository.TeacherRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Teacher.
 */
@RestController
@RequestMapping("/api")
public class TeacherResource {

    private final Logger log = LoggerFactory.getLogger(TeacherResource.class);
        
    @Inject
    private TeacherRepository teacherRepository;
    
    @Inject
    private IrisUserRepository irisUserRepository;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private UserService userService;
    
    @Inject
    private EmployeeRepository employeeRepository;
    
    
    /**
     * POST  /teachers -> Create a new teacher.
     */
    @RequestMapping(value = "/teachers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to save Teacher : {}", teacher);
        if (teacher.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("teacher", "idexists", "A new teacher cannot already have an ID")).body(null);
        }
        
        
        IrisUser irisUser = teacher.getEmployee().getIrisUser();
        
        User user = userService.createUserInformation((irisUser.getFirstName()+"123"),
        					"test@123",irisUser.getFirstName(),irisUser.getLastName(),
        					null,"en");
        
        userService.activateRegistration(user.getActivationKey());
        
        
        teacher.getEmployee().getIrisUser().setUser(user);
        
        irisUser =  irisUserRepository.save(teacher.getEmployee().getIrisUser());
        
        teacher.getEmployee().setIrisUser(irisUser);
        
        Employee employee = employeeRepository.save(teacher.getEmployee());
        
        teacher.setEmployee(employee);
        
        
        Teacher result = teacherRepository.save(teacher);
        return ResponseEntity.created(new URI("/api/teachers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("teacher", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teachers -> Updates an existing teacher.
     */
    @RequestMapping(value = "/teachers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to update Teacher : {}", teacher);
        if (teacher.getId() == null) {
            return createTeacher(teacher);
        }
        Teacher result = teacherRepository.save(teacher);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("teacher", teacher.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teachers -> get all the teachers.
     */
    @RequestMapping(value = "/teachers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Teacher> getAllTeachers() {
        log.debug("REST request to get all Teachers");
        return teacherRepository.findAll();
            }

    /**
     * GET  /teachers/:id -> get the "id" teacher.
     */
    @RequestMapping(value = "/teachers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teacher> getTeacher(@PathVariable Long id) {
        log.debug("REST request to get Teacher : {}", id);
        Teacher teacher = teacherRepository.findOne(id);
        return Optional.ofNullable(teacher)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /teachers/:id -> delete the "id" teacher.
     */
    @RequestMapping(value = "/teachers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        log.debug("REST request to delete Teacher : {}", id);
        teacherRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("teacher", id.toString())).build();
    }
}
