package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.RankingLevel;
import com.mycompany.myapp.repository.RankingLevelRepository;

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
 * Test class for the RankingLevelResource REST controller.
 *
 * @see RankingLevelResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RankingLevelResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_PERCENTAGE_LIMIT = "AAAAA";
    private static final String UPDATED_PERCENTAGE_LIMIT = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private RankingLevelRepository rankingLevelRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restRankingLevelMockMvc;

    private RankingLevel rankingLevel;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RankingLevelResource rankingLevelResource = new RankingLevelResource();
        ReflectionTestUtils.setField(rankingLevelResource, "rankingLevelRepository", rankingLevelRepository);
        this.restRankingLevelMockMvc = MockMvcBuilders.standaloneSetup(rankingLevelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        rankingLevel = new RankingLevel();
        rankingLevel.setName(DEFAULT_NAME);
        rankingLevel.setPercentageLimit(DEFAULT_PERCENTAGE_LIMIT);
        rankingLevel.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createRankingLevel() throws Exception {
        int databaseSizeBeforeCreate = rankingLevelRepository.findAll().size();

        // Create the RankingLevel

        restRankingLevelMockMvc.perform(post("/api/rankingLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rankingLevel)))
                .andExpect(status().isCreated());

        // Validate the RankingLevel in the database
        List<RankingLevel> rankingLevels = rankingLevelRepository.findAll();
        assertThat(rankingLevels).hasSize(databaseSizeBeforeCreate + 1);
        RankingLevel testRankingLevel = rankingLevels.get(rankingLevels.size() - 1);
        assertThat(testRankingLevel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRankingLevel.getPercentageLimit()).isEqualTo(DEFAULT_PERCENTAGE_LIMIT);
        assertThat(testRankingLevel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllRankingLevels() throws Exception {
        // Initialize the database
        rankingLevelRepository.saveAndFlush(rankingLevel);

        // Get all the rankingLevels
        restRankingLevelMockMvc.perform(get("/api/rankingLevels?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(rankingLevel.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].percentageLimit").value(hasItem(DEFAULT_PERCENTAGE_LIMIT.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getRankingLevel() throws Exception {
        // Initialize the database
        rankingLevelRepository.saveAndFlush(rankingLevel);

        // Get the rankingLevel
        restRankingLevelMockMvc.perform(get("/api/rankingLevels/{id}", rankingLevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(rankingLevel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.percentageLimit").value(DEFAULT_PERCENTAGE_LIMIT.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRankingLevel() throws Exception {
        // Get the rankingLevel
        restRankingLevelMockMvc.perform(get("/api/rankingLevels/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRankingLevel() throws Exception {
        // Initialize the database
        rankingLevelRepository.saveAndFlush(rankingLevel);

		int databaseSizeBeforeUpdate = rankingLevelRepository.findAll().size();

        // Update the rankingLevel
        rankingLevel.setName(UPDATED_NAME);
        rankingLevel.setPercentageLimit(UPDATED_PERCENTAGE_LIMIT);
        rankingLevel.setDescription(UPDATED_DESCRIPTION);

        restRankingLevelMockMvc.perform(put("/api/rankingLevels")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rankingLevel)))
                .andExpect(status().isOk());

        // Validate the RankingLevel in the database
        List<RankingLevel> rankingLevels = rankingLevelRepository.findAll();
        assertThat(rankingLevels).hasSize(databaseSizeBeforeUpdate);
        RankingLevel testRankingLevel = rankingLevels.get(rankingLevels.size() - 1);
        assertThat(testRankingLevel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRankingLevel.getPercentageLimit()).isEqualTo(UPDATED_PERCENTAGE_LIMIT);
        assertThat(testRankingLevel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteRankingLevel() throws Exception {
        // Initialize the database
        rankingLevelRepository.saveAndFlush(rankingLevel);

		int databaseSizeBeforeDelete = rankingLevelRepository.findAll().size();

        // Get the rankingLevel
        restRankingLevelMockMvc.perform(delete("/api/rankingLevels/{id}", rankingLevel.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<RankingLevel> rankingLevels = rankingLevelRepository.findAll();
        assertThat(rankingLevels).hasSize(databaseSizeBeforeDelete - 1);
    }
}
