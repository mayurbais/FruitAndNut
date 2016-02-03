package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.CurrancyLOV;
import com.mycompany.myapp.repository.CurrancyLOVRepository;

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
 * Test class for the CurrancyLOVResource REST controller.
 *
 * @see CurrancyLOVResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CurrancyLOVResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private CurrancyLOVRepository currancyLOVRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCurrancyLOVMockMvc;

    private CurrancyLOV currancyLOV;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CurrancyLOVResource currancyLOVResource = new CurrancyLOVResource();
        ReflectionTestUtils.setField(currancyLOVResource, "currancyLOVRepository", currancyLOVRepository);
        this.restCurrancyLOVMockMvc = MockMvcBuilders.standaloneSetup(currancyLOVResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        currancyLOV = new CurrancyLOV();
        currancyLOV.setValue(DEFAULT_VALUE);
        currancyLOV.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCurrancyLOV() throws Exception {
        int databaseSizeBeforeCreate = currancyLOVRepository.findAll().size();

        // Create the CurrancyLOV

        restCurrancyLOVMockMvc.perform(post("/api/currancyLOVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(currancyLOV)))
                .andExpect(status().isCreated());

        // Validate the CurrancyLOV in the database
        List<CurrancyLOV> currancyLOVs = currancyLOVRepository.findAll();
        assertThat(currancyLOVs).hasSize(databaseSizeBeforeCreate + 1);
        CurrancyLOV testCurrancyLOV = currancyLOVs.get(currancyLOVs.size() - 1);
        assertThat(testCurrancyLOV.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCurrancyLOV.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllCurrancyLOVs() throws Exception {
        // Initialize the database
        currancyLOVRepository.saveAndFlush(currancyLOV);

        // Get all the currancyLOVs
        restCurrancyLOVMockMvc.perform(get("/api/currancyLOVs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(currancyLOV.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCurrancyLOV() throws Exception {
        // Initialize the database
        currancyLOVRepository.saveAndFlush(currancyLOV);

        // Get the currancyLOV
        restCurrancyLOVMockMvc.perform(get("/api/currancyLOVs/{id}", currancyLOV.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(currancyLOV.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurrancyLOV() throws Exception {
        // Get the currancyLOV
        restCurrancyLOVMockMvc.perform(get("/api/currancyLOVs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurrancyLOV() throws Exception {
        // Initialize the database
        currancyLOVRepository.saveAndFlush(currancyLOV);

		int databaseSizeBeforeUpdate = currancyLOVRepository.findAll().size();

        // Update the currancyLOV
        currancyLOV.setValue(UPDATED_VALUE);
        currancyLOV.setName(UPDATED_NAME);

        restCurrancyLOVMockMvc.perform(put("/api/currancyLOVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(currancyLOV)))
                .andExpect(status().isOk());

        // Validate the CurrancyLOV in the database
        List<CurrancyLOV> currancyLOVs = currancyLOVRepository.findAll();
        assertThat(currancyLOVs).hasSize(databaseSizeBeforeUpdate);
        CurrancyLOV testCurrancyLOV = currancyLOVs.get(currancyLOVs.size() - 1);
        assertThat(testCurrancyLOV.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCurrancyLOV.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteCurrancyLOV() throws Exception {
        // Initialize the database
        currancyLOVRepository.saveAndFlush(currancyLOV);

		int databaseSizeBeforeDelete = currancyLOVRepository.findAll().size();

        // Get the currancyLOV
        restCurrancyLOVMockMvc.perform(delete("/api/currancyLOVs/{id}", currancyLOV.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CurrancyLOV> currancyLOVs = currancyLOVRepository.findAll();
        assertThat(currancyLOVs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
