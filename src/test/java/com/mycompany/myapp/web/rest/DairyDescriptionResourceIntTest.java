package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.DairyDescription;
import com.mycompany.myapp.repository.DairyDescriptionRepository;

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
 * Test class for the DairyDescriptionResource REST controller.
 *
 * @see DairyDescriptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DairyDescriptionResourceIntTest {

    private static final String DEFAULT_RULES = "AAAAA";
    private static final String UPDATED_RULES = "BBBBB";
    private static final String DEFAULT_CONTACT_NO_OF_MANAGMENT = "AAAAA";
    private static final String UPDATED_CONTACT_NO_OF_MANAGMENT = "BBBBB";
    private static final String DEFAULT_MISSION = "AAAAA";
    private static final String UPDATED_MISSION = "BBBBB";
    private static final String DEFAULT_OBJECTIVE = "AAAAA";
    private static final String UPDATED_OBJECTIVE = "BBBBB";
    private static final String DEFAULT_DECLARATION = "AAAAA";
    private static final String UPDATED_DECLARATION = "BBBBB";

    @Inject
    private DairyDescriptionRepository dairyDescriptionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDairyDescriptionMockMvc;

    private DairyDescription dairyDescription;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DairyDescriptionResource dairyDescriptionResource = new DairyDescriptionResource();
        ReflectionTestUtils.setField(dairyDescriptionResource, "dairyDescriptionRepository", dairyDescriptionRepository);
        this.restDairyDescriptionMockMvc = MockMvcBuilders.standaloneSetup(dairyDescriptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dairyDescription = new DairyDescription();
        dairyDescription.setRules(DEFAULT_RULES);
        dairyDescription.setContactNoOfManagment(DEFAULT_CONTACT_NO_OF_MANAGMENT);
        dairyDescription.setMission(DEFAULT_MISSION);
        dairyDescription.setObjective(DEFAULT_OBJECTIVE);
        dairyDescription.setDeclaration(DEFAULT_DECLARATION);
    }

    @Test
    @Transactional
    public void createDairyDescription() throws Exception {
        int databaseSizeBeforeCreate = dairyDescriptionRepository.findAll().size();

        // Create the DairyDescription

        restDairyDescriptionMockMvc.perform(post("/api/dairyDescriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dairyDescription)))
                .andExpect(status().isCreated());

        // Validate the DairyDescription in the database
        List<DairyDescription> dairyDescriptions = dairyDescriptionRepository.findAll();
        assertThat(dairyDescriptions).hasSize(databaseSizeBeforeCreate + 1);
        DairyDescription testDairyDescription = dairyDescriptions.get(dairyDescriptions.size() - 1);
        assertThat(testDairyDescription.getRules()).isEqualTo(DEFAULT_RULES);
        assertThat(testDairyDescription.getContactNoOfManagment()).isEqualTo(DEFAULT_CONTACT_NO_OF_MANAGMENT);
        assertThat(testDairyDescription.getMission()).isEqualTo(DEFAULT_MISSION);
        assertThat(testDairyDescription.getObjective()).isEqualTo(DEFAULT_OBJECTIVE);
        assertThat(testDairyDescription.getDeclaration()).isEqualTo(DEFAULT_DECLARATION);
    }

    @Test
    @Transactional
    public void getAllDairyDescriptions() throws Exception {
        // Initialize the database
        dairyDescriptionRepository.saveAndFlush(dairyDescription);

        // Get all the dairyDescriptions
        restDairyDescriptionMockMvc.perform(get("/api/dairyDescriptions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dairyDescription.getId().intValue())))
                .andExpect(jsonPath("$.[*].rules").value(hasItem(DEFAULT_RULES.toString())))
                .andExpect(jsonPath("$.[*].contactNoOfManagment").value(hasItem(DEFAULT_CONTACT_NO_OF_MANAGMENT.toString())))
                .andExpect(jsonPath("$.[*].mission").value(hasItem(DEFAULT_MISSION.toString())))
                .andExpect(jsonPath("$.[*].objective").value(hasItem(DEFAULT_OBJECTIVE.toString())))
                .andExpect(jsonPath("$.[*].declaration").value(hasItem(DEFAULT_DECLARATION.toString())));
    }

    @Test
    @Transactional
    public void getDairyDescription() throws Exception {
        // Initialize the database
        dairyDescriptionRepository.saveAndFlush(dairyDescription);

        // Get the dairyDescription
        restDairyDescriptionMockMvc.perform(get("/api/dairyDescriptions/{id}", dairyDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dairyDescription.getId().intValue()))
            .andExpect(jsonPath("$.rules").value(DEFAULT_RULES.toString()))
            .andExpect(jsonPath("$.contactNoOfManagment").value(DEFAULT_CONTACT_NO_OF_MANAGMENT.toString()))
            .andExpect(jsonPath("$.mission").value(DEFAULT_MISSION.toString()))
            .andExpect(jsonPath("$.objective").value(DEFAULT_OBJECTIVE.toString()))
            .andExpect(jsonPath("$.declaration").value(DEFAULT_DECLARATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDairyDescription() throws Exception {
        // Get the dairyDescription
        restDairyDescriptionMockMvc.perform(get("/api/dairyDescriptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDairyDescription() throws Exception {
        // Initialize the database
        dairyDescriptionRepository.saveAndFlush(dairyDescription);

		int databaseSizeBeforeUpdate = dairyDescriptionRepository.findAll().size();

        // Update the dairyDescription
        dairyDescription.setRules(UPDATED_RULES);
        dairyDescription.setContactNoOfManagment(UPDATED_CONTACT_NO_OF_MANAGMENT);
        dairyDescription.setMission(UPDATED_MISSION);
        dairyDescription.setObjective(UPDATED_OBJECTIVE);
        dairyDescription.setDeclaration(UPDATED_DECLARATION);

        restDairyDescriptionMockMvc.perform(put("/api/dairyDescriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dairyDescription)))
                .andExpect(status().isOk());

        // Validate the DairyDescription in the database
        List<DairyDescription> dairyDescriptions = dairyDescriptionRepository.findAll();
        assertThat(dairyDescriptions).hasSize(databaseSizeBeforeUpdate);
        DairyDescription testDairyDescription = dairyDescriptions.get(dairyDescriptions.size() - 1);
        assertThat(testDairyDescription.getRules()).isEqualTo(UPDATED_RULES);
        assertThat(testDairyDescription.getContactNoOfManagment()).isEqualTo(UPDATED_CONTACT_NO_OF_MANAGMENT);
        assertThat(testDairyDescription.getMission()).isEqualTo(UPDATED_MISSION);
        assertThat(testDairyDescription.getObjective()).isEqualTo(UPDATED_OBJECTIVE);
        assertThat(testDairyDescription.getDeclaration()).isEqualTo(UPDATED_DECLARATION);
    }

    @Test
    @Transactional
    public void deleteDairyDescription() throws Exception {
        // Initialize the database
        dairyDescriptionRepository.saveAndFlush(dairyDescription);

		int databaseSizeBeforeDelete = dairyDescriptionRepository.findAll().size();

        // Get the dairyDescription
        restDairyDescriptionMockMvc.perform(delete("/api/dairyDescriptions/{id}", dairyDescription.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DairyDescription> dairyDescriptions = dairyDescriptionRepository.findAll();
        assertThat(dairyDescriptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
