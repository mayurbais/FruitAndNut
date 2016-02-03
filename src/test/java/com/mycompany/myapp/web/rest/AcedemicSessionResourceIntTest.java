package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.AcedemicSession;
import com.mycompany.myapp.repository.AcedemicSessionRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AcedemicSessionResource REST controller.
 *
 * @see AcedemicSessionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AcedemicSessionResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_SESSION_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SESSION_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SESSION_START_DATE_STR = dateTimeFormatter.format(DEFAULT_SESSION_START_DATE);

    private static final ZonedDateTime DEFAULT_SESSION_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SESSION_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SESSION_END_DATE_STR = dateTimeFormatter.format(DEFAULT_SESSION_END_DATE);

    @Inject
    private AcedemicSessionRepository acedemicSessionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAcedemicSessionMockMvc;

    private AcedemicSession acedemicSession;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AcedemicSessionResource acedemicSessionResource = new AcedemicSessionResource();
        ReflectionTestUtils.setField(acedemicSessionResource, "acedemicSessionRepository", acedemicSessionRepository);
        this.restAcedemicSessionMockMvc = MockMvcBuilders.standaloneSetup(acedemicSessionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        acedemicSession = new AcedemicSession();
        acedemicSession.setSessionStartDate(DEFAULT_SESSION_START_DATE);
        acedemicSession.setSessionEndDate(DEFAULT_SESSION_END_DATE);
    }

    @Test
    @Transactional
    public void createAcedemicSession() throws Exception {
        int databaseSizeBeforeCreate = acedemicSessionRepository.findAll().size();

        // Create the AcedemicSession

        restAcedemicSessionMockMvc.perform(post("/api/acedemicSessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(acedemicSession)))
                .andExpect(status().isCreated());

        // Validate the AcedemicSession in the database
        List<AcedemicSession> acedemicSessions = acedemicSessionRepository.findAll();
        assertThat(acedemicSessions).hasSize(databaseSizeBeforeCreate + 1);
        AcedemicSession testAcedemicSession = acedemicSessions.get(acedemicSessions.size() - 1);
        assertThat(testAcedemicSession.getSessionStartDate()).isEqualTo(DEFAULT_SESSION_START_DATE);
        assertThat(testAcedemicSession.getSessionEndDate()).isEqualTo(DEFAULT_SESSION_END_DATE);
    }

    @Test
    @Transactional
    public void getAllAcedemicSessions() throws Exception {
        // Initialize the database
        acedemicSessionRepository.saveAndFlush(acedemicSession);

        // Get all the acedemicSessions
        restAcedemicSessionMockMvc.perform(get("/api/acedemicSessions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(acedemicSession.getId().intValue())))
                .andExpect(jsonPath("$.[*].sessionStartDate").value(hasItem(DEFAULT_SESSION_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].sessionEndDate").value(hasItem(DEFAULT_SESSION_END_DATE_STR)));
    }

    @Test
    @Transactional
    public void getAcedemicSession() throws Exception {
        // Initialize the database
        acedemicSessionRepository.saveAndFlush(acedemicSession);

        // Get the acedemicSession
        restAcedemicSessionMockMvc.perform(get("/api/acedemicSessions/{id}", acedemicSession.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(acedemicSession.getId().intValue()))
            .andExpect(jsonPath("$.sessionStartDate").value(DEFAULT_SESSION_START_DATE_STR))
            .andExpect(jsonPath("$.sessionEndDate").value(DEFAULT_SESSION_END_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingAcedemicSession() throws Exception {
        // Get the acedemicSession
        restAcedemicSessionMockMvc.perform(get("/api/acedemicSessions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcedemicSession() throws Exception {
        // Initialize the database
        acedemicSessionRepository.saveAndFlush(acedemicSession);

		int databaseSizeBeforeUpdate = acedemicSessionRepository.findAll().size();

        // Update the acedemicSession
        acedemicSession.setSessionStartDate(UPDATED_SESSION_START_DATE);
        acedemicSession.setSessionEndDate(UPDATED_SESSION_END_DATE);

        restAcedemicSessionMockMvc.perform(put("/api/acedemicSessions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(acedemicSession)))
                .andExpect(status().isOk());

        // Validate the AcedemicSession in the database
        List<AcedemicSession> acedemicSessions = acedemicSessionRepository.findAll();
        assertThat(acedemicSessions).hasSize(databaseSizeBeforeUpdate);
        AcedemicSession testAcedemicSession = acedemicSessions.get(acedemicSessions.size() - 1);
        assertThat(testAcedemicSession.getSessionStartDate()).isEqualTo(UPDATED_SESSION_START_DATE);
        assertThat(testAcedemicSession.getSessionEndDate()).isEqualTo(UPDATED_SESSION_END_DATE);
    }

    @Test
    @Transactional
    public void deleteAcedemicSession() throws Exception {
        // Initialize the database
        acedemicSessionRepository.saveAndFlush(acedemicSession);

		int databaseSizeBeforeDelete = acedemicSessionRepository.findAll().size();

        // Get the acedemicSession
        restAcedemicSessionMockMvc.perform(delete("/api/acedemicSessions/{id}", acedemicSession.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AcedemicSession> acedemicSessions = acedemicSessionRepository.findAll();
        assertThat(acedemicSessions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
