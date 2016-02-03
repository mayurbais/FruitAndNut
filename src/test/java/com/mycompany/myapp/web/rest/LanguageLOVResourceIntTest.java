package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.LanguageLOV;
import com.mycompany.myapp.repository.LanguageLOVRepository;

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
 * Test class for the LanguageLOVResource REST controller.
 *
 * @see LanguageLOVResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LanguageLOVResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private LanguageLOVRepository languageLOVRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLanguageLOVMockMvc;

    private LanguageLOV languageLOV;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LanguageLOVResource languageLOVResource = new LanguageLOVResource();
        ReflectionTestUtils.setField(languageLOVResource, "languageLOVRepository", languageLOVRepository);
        this.restLanguageLOVMockMvc = MockMvcBuilders.standaloneSetup(languageLOVResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        languageLOV = new LanguageLOV();
        languageLOV.setName(DEFAULT_NAME);
        languageLOV.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createLanguageLOV() throws Exception {
        int databaseSizeBeforeCreate = languageLOVRepository.findAll().size();

        // Create the LanguageLOV

        restLanguageLOVMockMvc.perform(post("/api/languageLOVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(languageLOV)))
                .andExpect(status().isCreated());

        // Validate the LanguageLOV in the database
        List<LanguageLOV> languageLOVs = languageLOVRepository.findAll();
        assertThat(languageLOVs).hasSize(databaseSizeBeforeCreate + 1);
        LanguageLOV testLanguageLOV = languageLOVs.get(languageLOVs.size() - 1);
        assertThat(testLanguageLOV.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLanguageLOV.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllLanguageLOVs() throws Exception {
        // Initialize the database
        languageLOVRepository.saveAndFlush(languageLOV);

        // Get all the languageLOVs
        restLanguageLOVMockMvc.perform(get("/api/languageLOVs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(languageLOV.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getLanguageLOV() throws Exception {
        // Initialize the database
        languageLOVRepository.saveAndFlush(languageLOV);

        // Get the languageLOV
        restLanguageLOVMockMvc.perform(get("/api/languageLOVs/{id}", languageLOV.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(languageLOV.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLanguageLOV() throws Exception {
        // Get the languageLOV
        restLanguageLOVMockMvc.perform(get("/api/languageLOVs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLanguageLOV() throws Exception {
        // Initialize the database
        languageLOVRepository.saveAndFlush(languageLOV);

		int databaseSizeBeforeUpdate = languageLOVRepository.findAll().size();

        // Update the languageLOV
        languageLOV.setName(UPDATED_NAME);
        languageLOV.setValue(UPDATED_VALUE);

        restLanguageLOVMockMvc.perform(put("/api/languageLOVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(languageLOV)))
                .andExpect(status().isOk());

        // Validate the LanguageLOV in the database
        List<LanguageLOV> languageLOVs = languageLOVRepository.findAll();
        assertThat(languageLOVs).hasSize(databaseSizeBeforeUpdate);
        LanguageLOV testLanguageLOV = languageLOVs.get(languageLOVs.size() - 1);
        assertThat(testLanguageLOV.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLanguageLOV.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteLanguageLOV() throws Exception {
        // Initialize the database
        languageLOVRepository.saveAndFlush(languageLOV);

		int databaseSizeBeforeDelete = languageLOVRepository.findAll().size();

        // Get the languageLOV
        restLanguageLOVMockMvc.perform(delete("/api/languageLOVs/{id}", languageLOV.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LanguageLOV> languageLOVs = languageLOVRepository.findAll();
        assertThat(languageLOVs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
