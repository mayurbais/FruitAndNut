package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.ElectiveSubject;
import com.mycompany.myapp.repository.ElectiveSubjectRepository;

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
 * Test class for the ElectiveSubjectResource REST controller.
 *
 * @see ElectiveSubjectResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ElectiveSubjectResourceIntTest {

    private static final String DEFAULT_NAME = "AA";
    private static final String UPDATED_NAME = "BB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";

    @Inject
    private ElectiveSubjectRepository electiveSubjectRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restElectiveSubjectMockMvc;

    private ElectiveSubject electiveSubject;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ElectiveSubjectResource electiveSubjectResource = new ElectiveSubjectResource();
        ReflectionTestUtils.setField(electiveSubjectResource, "electiveSubjectRepository", electiveSubjectRepository);
        this.restElectiveSubjectMockMvc = MockMvcBuilders.standaloneSetup(electiveSubjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        electiveSubject = new ElectiveSubject();
        electiveSubject.setName(DEFAULT_NAME);
        electiveSubject.setDescription(DEFAULT_DESCRIPTION);
        electiveSubject.setCode(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createElectiveSubject() throws Exception {
        int databaseSizeBeforeCreate = electiveSubjectRepository.findAll().size();

        // Create the ElectiveSubject

        restElectiveSubjectMockMvc.perform(post("/api/electiveSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(electiveSubject)))
                .andExpect(status().isCreated());

        // Validate the ElectiveSubject in the database
        List<ElectiveSubject> electiveSubjects = electiveSubjectRepository.findAll();
        assertThat(electiveSubjects).hasSize(databaseSizeBeforeCreate + 1);
        ElectiveSubject testElectiveSubject = electiveSubjects.get(electiveSubjects.size() - 1);
        assertThat(testElectiveSubject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testElectiveSubject.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testElectiveSubject.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = electiveSubjectRepository.findAll().size();
        // set the field null
        electiveSubject.setName(null);

        // Create the ElectiveSubject, which fails.

        restElectiveSubjectMockMvc.perform(post("/api/electiveSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(electiveSubject)))
                .andExpect(status().isBadRequest());

        List<ElectiveSubject> electiveSubjects = electiveSubjectRepository.findAll();
        assertThat(electiveSubjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllElectiveSubjects() throws Exception {
        // Initialize the database
        electiveSubjectRepository.saveAndFlush(electiveSubject);

        // Get all the electiveSubjects
        restElectiveSubjectMockMvc.perform(get("/api/electiveSubjects?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(electiveSubject.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())));
    }

    @Test
    @Transactional
    public void getElectiveSubject() throws Exception {
        // Initialize the database
        electiveSubjectRepository.saveAndFlush(electiveSubject);

        // Get the electiveSubject
        restElectiveSubjectMockMvc.perform(get("/api/electiveSubjects/{id}", electiveSubject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(electiveSubject.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingElectiveSubject() throws Exception {
        // Get the electiveSubject
        restElectiveSubjectMockMvc.perform(get("/api/electiveSubjects/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElectiveSubject() throws Exception {
        // Initialize the database
        electiveSubjectRepository.saveAndFlush(electiveSubject);

		int databaseSizeBeforeUpdate = electiveSubjectRepository.findAll().size();

        // Update the electiveSubject
        electiveSubject.setName(UPDATED_NAME);
        electiveSubject.setDescription(UPDATED_DESCRIPTION);
        electiveSubject.setCode(UPDATED_CODE);

        restElectiveSubjectMockMvc.perform(put("/api/electiveSubjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(electiveSubject)))
                .andExpect(status().isOk());

        // Validate the ElectiveSubject in the database
        List<ElectiveSubject> electiveSubjects = electiveSubjectRepository.findAll();
        assertThat(electiveSubjects).hasSize(databaseSizeBeforeUpdate);
        ElectiveSubject testElectiveSubject = electiveSubjects.get(electiveSubjects.size() - 1);
        assertThat(testElectiveSubject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testElectiveSubject.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testElectiveSubject.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void deleteElectiveSubject() throws Exception {
        // Initialize the database
        electiveSubjectRepository.saveAndFlush(electiveSubject);

		int databaseSizeBeforeDelete = electiveSubjectRepository.findAll().size();

        // Get the electiveSubject
        restElectiveSubjectMockMvc.perform(delete("/api/electiveSubjects/{id}", electiveSubject.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ElectiveSubject> electiveSubjects = electiveSubjectRepository.findAll();
        assertThat(electiveSubjects).hasSize(databaseSizeBeforeDelete - 1);
    }
}
