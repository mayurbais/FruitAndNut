package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.ClassRoomSession;
import com.mycompany.myapp.repository.ClassRoomSessionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ClassRoomSessionResource REST controller.
 *
 * @see ClassRoomSessionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ClassRoomSessionResourceIntTest {

    private static final String DEFAULT_SESSION_NAME = "AAAAA";
    private static final String UPDATED_SESSION_NAME = "BBBBB";
    private static final String DEFAULT_START_TIME = "AAAAA";
    private static final String UPDATED_START_TIME = "BBBBB";
    private static final String DEFAULT_END_TIME = "AAAAA";
    private static final String UPDATED_END_TIME = "BBBBB";

    private static final Boolean DEFAULT_IS_BREAK = false;
    private static final Boolean UPDATED_IS_BREAK = true;
    private static final String DEFAULT_ATTRIBUTE = "AAAAA";
    private static final String UPDATED_ATTRIBUTE = "BBBBB";

    @Inject
    private ClassRoomSessionRepository classRoomSessionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restClassRoomSessionMockMvc;

    private ClassRoomSession classRoomSession;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ClassRoomSessionResource classRoomSessionResource = new ClassRoomSessionResource();
        ReflectionTestUtils.setField(classRoomSessionResource, "classRoomSessionRepository", classRoomSessionRepository);
        this.restClassRoomSessionMockMvc = MockMvcBuilders.standaloneSetup(classRoomSessionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        classRoomSession = new ClassRoomSession();
        classRoomSession.setSessionName(DEFAULT_SESSION_NAME);
        classRoomSession.setStartTime(DEFAULT_START_TIME);
        classRoomSession.setEndTime(DEFAULT_END_TIME);
        classRoomSession.setIsBreak(DEFAULT_IS_BREAK);
        classRoomSession.setAttribute(DEFAULT_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void createClassRoomSession() throws Exception {
        int databaseSizeBeforeCreate = classRoomSessionRepository.findAll().size();

        // Create the ClassRoomSession

        restClassRoomSessionMockMvc.perform(post("/api/classRoomSessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classRoomSession)))
                .andExpect(status().isCreated());

        // Validate the ClassRoomSession in the database
        List<ClassRoomSession> classRoomSessions = classRoomSessionRepository.findAll();
        assertThat(classRoomSessions).hasSize(databaseSizeBeforeCreate + 1);
        ClassRoomSession testClassRoomSession = classRoomSessions.get(classRoomSessions.size() - 1);
        assertThat(testClassRoomSession.getSessionName()).isEqualTo(DEFAULT_SESSION_NAME);
        assertThat(testClassRoomSession.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testClassRoomSession.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testClassRoomSession.getIsBreak()).isEqualTo(DEFAULT_IS_BREAK);
        assertThat(testClassRoomSession.getAttribute()).isEqualTo(DEFAULT_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void getAllClassRoomSessions() throws Exception {
        // Initialize the database
        classRoomSessionRepository.saveAndFlush(classRoomSession);

        // Get all the classRoomSessions
        restClassRoomSessionMockMvc.perform(get("/api/classRoomSessions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(classRoomSession.getId().intValue())))
                .andExpect(jsonPath("$.[*].sessionName").value(hasItem(DEFAULT_SESSION_NAME.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
                .andExpect(jsonPath("$.[*].isBreak").value(hasItem(DEFAULT_IS_BREAK.booleanValue())))
                .andExpect(jsonPath("$.[*].attribute").value(hasItem(DEFAULT_ATTRIBUTE.toString())));
    }

    @Test
    @Transactional
    public void getClassRoomSession() throws Exception {
        // Initialize the database
        classRoomSessionRepository.saveAndFlush(classRoomSession);

        // Get the classRoomSession
        restClassRoomSessionMockMvc.perform(get("/api/classRoomSessions/{id}", classRoomSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(classRoomSession.getId().intValue()))
            .andExpect(jsonPath("$.sessionName").value(DEFAULT_SESSION_NAME.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.isBreak").value(DEFAULT_IS_BREAK.booleanValue()))
            .andExpect(jsonPath("$.attribute").value(DEFAULT_ATTRIBUTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClassRoomSession() throws Exception {
        // Get the classRoomSession
        restClassRoomSessionMockMvc.perform(get("/api/classRoomSessions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassRoomSession() throws Exception {
        // Initialize the database
        classRoomSessionRepository.saveAndFlush(classRoomSession);

		int databaseSizeBeforeUpdate = classRoomSessionRepository.findAll().size();

        // Update the classRoomSession
        classRoomSession.setSessionName(UPDATED_SESSION_NAME);
        classRoomSession.setStartTime(UPDATED_START_TIME);
        classRoomSession.setEndTime(UPDATED_END_TIME);
        classRoomSession.setIsBreak(UPDATED_IS_BREAK);
        classRoomSession.setAttribute(UPDATED_ATTRIBUTE);

        restClassRoomSessionMockMvc.perform(put("/api/classRoomSessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(classRoomSession)))
                .andExpect(status().isOk());

        // Validate the ClassRoomSession in the database
        List<ClassRoomSession> classRoomSessions = classRoomSessionRepository.findAll();
        assertThat(classRoomSessions).hasSize(databaseSizeBeforeUpdate);
        ClassRoomSession testClassRoomSession = classRoomSessions.get(classRoomSessions.size() - 1);
        assertThat(testClassRoomSession.getSessionName()).isEqualTo(UPDATED_SESSION_NAME);
        assertThat(testClassRoomSession.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testClassRoomSession.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testClassRoomSession.getIsBreak()).isEqualTo(UPDATED_IS_BREAK);
        assertThat(testClassRoomSession.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void deleteClassRoomSession() throws Exception {
        // Initialize the database
        classRoomSessionRepository.saveAndFlush(classRoomSession);

		int databaseSizeBeforeDelete = classRoomSessionRepository.findAll().size();

        // Get the classRoomSession
        restClassRoomSessionMockMvc.perform(delete("/api/classRoomSessions/{id}", classRoomSession.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassRoomSession> classRoomSessions = classRoomSessionRepository.findAll();
        assertThat(classRoomSessions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
