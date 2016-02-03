package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Session;
import com.mycompany.myapp.repository.SessionRepository;

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
 * Test class for the SessionResource REST controller.
 *
 * @see SessionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SessionResourceIntTest {

    private static final String DEFAULT_SESSION_NAME = "AAAAA";
    private static final String UPDATED_SESSION_NAME = "BBBBB";
    private static final String DEFAULT_START_TIME = "AAAAA";
    private static final String UPDATED_START_TIME = "BBBBB";
    private static final String DEFAULT_END_TIME = "AAAAA";
    private static final String UPDATED_END_TIME = "BBBBB";

    private static final Boolean DEFAULT_IS_BREAK = false;
    private static final Boolean UPDATED_IS_BREAK = true;

    @Inject
    private SessionRepository sessionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSessionMockMvc;

    private Session session;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SessionResource sessionResource = new SessionResource();
        ReflectionTestUtils.setField(sessionResource, "sessionRepository", sessionRepository);
        this.restSessionMockMvc = MockMvcBuilders.standaloneSetup(sessionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        session = new Session();
        session.setSessionName(DEFAULT_SESSION_NAME);
        session.setStartTime(DEFAULT_START_TIME);
        session.setEndTime(DEFAULT_END_TIME);
        session.setIsBreak(DEFAULT_IS_BREAK);
    }

    @Test
    @Transactional
    public void createSession() throws Exception {
        int databaseSizeBeforeCreate = sessionRepository.findAll().size();

        // Create the Session

        restSessionMockMvc.perform(post("/api/sessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(session)))
                .andExpect(status().isCreated());

        // Validate the Session in the database
        List<Session> sessions = sessionRepository.findAll();
        assertThat(sessions).hasSize(databaseSizeBeforeCreate + 1);
        Session testSession = sessions.get(sessions.size() - 1);
        assertThat(testSession.getSessionName()).isEqualTo(DEFAULT_SESSION_NAME);
        assertThat(testSession.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSession.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testSession.getIsBreak()).isEqualTo(DEFAULT_IS_BREAK);
    }

    @Test
    @Transactional
    public void getAllSessions() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get all the sessions
        restSessionMockMvc.perform(get("/api/sessions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(session.getId().intValue())))
                .andExpect(jsonPath("$.[*].sessionName").value(hasItem(DEFAULT_SESSION_NAME.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
                .andExpect(jsonPath("$.[*].isBreak").value(hasItem(DEFAULT_IS_BREAK.booleanValue())));
    }

    @Test
    @Transactional
    public void getSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", session.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(session.getId().intValue()))
            .andExpect(jsonPath("$.sessionName").value(DEFAULT_SESSION_NAME.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.isBreak").value(DEFAULT_IS_BREAK.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSession() throws Exception {
        // Get the session
        restSessionMockMvc.perform(get("/api/sessions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

		int databaseSizeBeforeUpdate = sessionRepository.findAll().size();

        // Update the session
        session.setSessionName(UPDATED_SESSION_NAME);
        session.setStartTime(UPDATED_START_TIME);
        session.setEndTime(UPDATED_END_TIME);
        session.setIsBreak(UPDATED_IS_BREAK);

        restSessionMockMvc.perform(put("/api/sessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(session)))
                .andExpect(status().isOk());

        // Validate the Session in the database
        List<Session> sessions = sessionRepository.findAll();
        assertThat(sessions).hasSize(databaseSizeBeforeUpdate);
        Session testSession = sessions.get(sessions.size() - 1);
        assertThat(testSession.getSessionName()).isEqualTo(UPDATED_SESSION_NAME);
        assertThat(testSession.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSession.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testSession.getIsBreak()).isEqualTo(UPDATED_IS_BREAK);
    }

    @Test
    @Transactional
    public void deleteSession() throws Exception {
        // Initialize the database
        sessionRepository.saveAndFlush(session);

		int databaseSizeBeforeDelete = sessionRepository.findAll().size();

        // Get the session
        restSessionMockMvc.perform(delete("/api/sessions/{id}", session.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Session> sessions = sessionRepository.findAll();
        assertThat(sessions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
