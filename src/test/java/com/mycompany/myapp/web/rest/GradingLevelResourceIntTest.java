package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.GradingLevel;
import com.mycompany.myapp.repository.GradingLevelRepository;

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
 * Test class for the GradingLevelResource REST controller.
 *
 * @see GradingLevelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class GradingLevelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_MIN_SCORE = "AAAAA";
    private static final String UPDATED_MIN_SCORE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private GradingLevelRepository gradingLevelRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restGradingLevelMockMvc;

    private GradingLevel gradingLevel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GradingLevelResource gradingLevelResource = new GradingLevelResource();
        ReflectionTestUtils.setField(gradingLevelResource, "gradingLevelRepository", gradingLevelRepository);
        this.restGradingLevelMockMvc = MockMvcBuilders.standaloneSetup(gradingLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        gradingLevel = new GradingLevel();
        gradingLevel.setName(DEFAULT_NAME);
        gradingLevel.setMinScore(DEFAULT_MIN_SCORE);
        gradingLevel.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createGradingLevel() throws Exception {
        int databaseSizeBeforeCreate = gradingLevelRepository.findAll().size();

        // Create the GradingLevel

        restGradingLevelMockMvc.perform(post("/api/gradingLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gradingLevel)))
                .andExpect(status().isCreated());

        // Validate the GradingLevel in the database
        List<GradingLevel> gradingLevels = gradingLevelRepository.findAll();
        assertThat(gradingLevels).hasSize(databaseSizeBeforeCreate + 1);
        GradingLevel testGradingLevel = gradingLevels.get(gradingLevels.size() - 1);
        assertThat(testGradingLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGradingLevel.getMinScore()).isEqualTo(DEFAULT_MIN_SCORE);
        assertThat(testGradingLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllGradingLevels() throws Exception {
        // Initialize the database
        gradingLevelRepository.saveAndFlush(gradingLevel);

        // Get all the gradingLevels
        restGradingLevelMockMvc.perform(get("/api/gradingLevels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(gradingLevel.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].minScore").value(hasItem(DEFAULT_MIN_SCORE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getGradingLevel() throws Exception {
        // Initialize the database
        gradingLevelRepository.saveAndFlush(gradingLevel);

        // Get the gradingLevel
        restGradingLevelMockMvc.perform(get("/api/gradingLevels/{id}", gradingLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(gradingLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.minScore").value(DEFAULT_MIN_SCORE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGradingLevel() throws Exception {
        // Get the gradingLevel
        restGradingLevelMockMvc.perform(get("/api/gradingLevels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGradingLevel() throws Exception {
        // Initialize the database
        gradingLevelRepository.saveAndFlush(gradingLevel);

		int databaseSizeBeforeUpdate = gradingLevelRepository.findAll().size();

        // Update the gradingLevel
        gradingLevel.setName(UPDATED_NAME);
        gradingLevel.setMinScore(UPDATED_MIN_SCORE);
        gradingLevel.setDescription(UPDATED_DESCRIPTION);

        restGradingLevelMockMvc.perform(put("/api/gradingLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(gradingLevel)))
                .andExpect(status().isOk());

        // Validate the GradingLevel in the database
        List<GradingLevel> gradingLevels = gradingLevelRepository.findAll();
        assertThat(gradingLevels).hasSize(databaseSizeBeforeUpdate);
        GradingLevel testGradingLevel = gradingLevels.get(gradingLevels.size() - 1);
        assertThat(testGradingLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGradingLevel.getMinScore()).isEqualTo(UPDATED_MIN_SCORE);
        assertThat(testGradingLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteGradingLevel() throws Exception {
        // Initialize the database
        gradingLevelRepository.saveAndFlush(gradingLevel);

		int databaseSizeBeforeDelete = gradingLevelRepository.findAll().size();

        // Get the gradingLevel
        restGradingLevelMockMvc.perform(delete("/api/gradingLevels/{id}", gradingLevel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<GradingLevel> gradingLevels = gradingLevelRepository.findAll();
        assertThat(gradingLevels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
