package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.CountryLOV;
import com.mycompany.myapp.repository.CountryLOVRepository;

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
 * Test class for the CountryLOVResource REST controller.
 *
 * @see CountryLOVResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CountryLOVResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private CountryLOVRepository countryLOVRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCountryLOVMockMvc;

    private CountryLOV countryLOV;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CountryLOVResource countryLOVResource = new CountryLOVResource();
        ReflectionTestUtils.setField(countryLOVResource, "countryLOVRepository", countryLOVRepository);
        this.restCountryLOVMockMvc = MockMvcBuilders.standaloneSetup(countryLOVResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        countryLOV = new CountryLOV();
        countryLOV.setValue(DEFAULT_VALUE);
        countryLOV.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCountryLOV() throws Exception {
        int databaseSizeBeforeCreate = countryLOVRepository.findAll().size();

        // Create the CountryLOV

        restCountryLOVMockMvc.perform(post("/api/countryLOVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(countryLOV)))
                .andExpect(status().isCreated());

        // Validate the CountryLOV in the database
        List<CountryLOV> countryLOVs = countryLOVRepository.findAll();
        assertThat(countryLOVs).hasSize(databaseSizeBeforeCreate + 1);
        CountryLOV testCountryLOV = countryLOVs.get(countryLOVs.size() - 1);
        assertThat(testCountryLOV.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testCountryLOV.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllCountryLOVs() throws Exception {
        // Initialize the database
        countryLOVRepository.saveAndFlush(countryLOV);

        // Get all the countryLOVs
        restCountryLOVMockMvc.perform(get("/api/countryLOVs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(countryLOV.getId().intValue())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCountryLOV() throws Exception {
        // Initialize the database
        countryLOVRepository.saveAndFlush(countryLOV);

        // Get the countryLOV
        restCountryLOVMockMvc.perform(get("/api/countryLOVs/{id}", countryLOV.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(countryLOV.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCountryLOV() throws Exception {
        // Get the countryLOV
        restCountryLOVMockMvc.perform(get("/api/countryLOVs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCountryLOV() throws Exception {
        // Initialize the database
        countryLOVRepository.saveAndFlush(countryLOV);

		int databaseSizeBeforeUpdate = countryLOVRepository.findAll().size();

        // Update the countryLOV
        countryLOV.setValue(UPDATED_VALUE);
        countryLOV.setName(UPDATED_NAME);

        restCountryLOVMockMvc.perform(put("/api/countryLOVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(countryLOV)))
                .andExpect(status().isOk());

        // Validate the CountryLOV in the database
        List<CountryLOV> countryLOVs = countryLOVRepository.findAll();
        assertThat(countryLOVs).hasSize(databaseSizeBeforeUpdate);
        CountryLOV testCountryLOV = countryLOVs.get(countryLOVs.size() - 1);
        assertThat(testCountryLOV.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testCountryLOV.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteCountryLOV() throws Exception {
        // Initialize the database
        countryLOVRepository.saveAndFlush(countryLOV);

		int databaseSizeBeforeDelete = countryLOVRepository.findAll().size();

        // Get the countryLOV
        restCountryLOVMockMvc.perform(delete("/api/countryLOVs/{id}", countryLOV.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CountryLOV> countryLOVs = countryLOVRepository.findAll();
        assertThat(countryLOVs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
