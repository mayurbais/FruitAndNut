package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.AdmissionDetails;
import com.mycompany.myapp.repository.AdmissionDetailsRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the AdmissionDetailsResource REST controller.
 *
 * @see AdmissionDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AdmissionDetailsResourceIntTest {

    private static final String DEFAULT_ADMISSION_NO = "AAAAA";
    private static final String UPDATED_ADMISSION_NO = "BBBBB";

    private static final LocalDate DEFAULT_ADMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADMISSION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private AdmissionDetailsRepository admissionDetailsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAdmissionDetailsMockMvc;

    private AdmissionDetails admissionDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AdmissionDetailsResource admissionDetailsResource = new AdmissionDetailsResource();
        ReflectionTestUtils.setField(admissionDetailsResource, "admissionDetailsRepository", admissionDetailsRepository);
        this.restAdmissionDetailsMockMvc = MockMvcBuilders.standaloneSetup(admissionDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        admissionDetails = new AdmissionDetails();
        admissionDetails.setAdmissionNo(DEFAULT_ADMISSION_NO);
        admissionDetails.setAdmissionDate(DEFAULT_ADMISSION_DATE);
    }

    @Test
    @Transactional
    public void createAdmissionDetails() throws Exception {
        int databaseSizeBeforeCreate = admissionDetailsRepository.findAll().size();

        // Create the AdmissionDetails

        restAdmissionDetailsMockMvc.perform(post("/api/admissionDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(admissionDetails)))
                .andExpect(status().isCreated());

        // Validate the AdmissionDetails in the database
        List<AdmissionDetails> admissionDetailss = admissionDetailsRepository.findAll();
        assertThat(admissionDetailss).hasSize(databaseSizeBeforeCreate + 1);
        AdmissionDetails testAdmissionDetails = admissionDetailss.get(admissionDetailss.size() - 1);
        assertThat(testAdmissionDetails.getAdmissionNo()).isEqualTo(DEFAULT_ADMISSION_NO);
        assertThat(testAdmissionDetails.getAdmissionDate()).isEqualTo(DEFAULT_ADMISSION_DATE);
    }

    @Test
    @Transactional
    public void checkAdmissionNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = admissionDetailsRepository.findAll().size();
        // set the field null
        admissionDetails.setAdmissionNo(null);

        // Create the AdmissionDetails, which fails.

        restAdmissionDetailsMockMvc.perform(post("/api/admissionDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(admissionDetails)))
                .andExpect(status().isBadRequest());

        List<AdmissionDetails> admissionDetailss = admissionDetailsRepository.findAll();
        assertThat(admissionDetailss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdmissionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = admissionDetailsRepository.findAll().size();
        // set the field null
        admissionDetails.setAdmissionDate(null);

        // Create the AdmissionDetails, which fails.

        restAdmissionDetailsMockMvc.perform(post("/api/admissionDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(admissionDetails)))
                .andExpect(status().isBadRequest());

        List<AdmissionDetails> admissionDetailss = admissionDetailsRepository.findAll();
        assertThat(admissionDetailss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdmissionDetailss() throws Exception {
        // Initialize the database
        admissionDetailsRepository.saveAndFlush(admissionDetails);

        // Get all the admissionDetailss
        restAdmissionDetailsMockMvc.perform(get("/api/admissionDetailss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(admissionDetails.getId().intValue())))
                .andExpect(jsonPath("$.[*].admissionNo").value(hasItem(DEFAULT_ADMISSION_NO.toString())))
                .andExpect(jsonPath("$.[*].admissionDate").value(hasItem(DEFAULT_ADMISSION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAdmissionDetails() throws Exception {
        // Initialize the database
        admissionDetailsRepository.saveAndFlush(admissionDetails);

        // Get the admissionDetails
        restAdmissionDetailsMockMvc.perform(get("/api/admissionDetailss/{id}", admissionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(admissionDetails.getId().intValue()))
            .andExpect(jsonPath("$.admissionNo").value(DEFAULT_ADMISSION_NO.toString()))
            .andExpect(jsonPath("$.admissionDate").value(DEFAULT_ADMISSION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdmissionDetails() throws Exception {
        // Get the admissionDetails
        restAdmissionDetailsMockMvc.perform(get("/api/admissionDetailss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdmissionDetails() throws Exception {
        // Initialize the database
        admissionDetailsRepository.saveAndFlush(admissionDetails);

		int databaseSizeBeforeUpdate = admissionDetailsRepository.findAll().size();

        // Update the admissionDetails
        admissionDetails.setAdmissionNo(UPDATED_ADMISSION_NO);
        admissionDetails.setAdmissionDate(UPDATED_ADMISSION_DATE);

        restAdmissionDetailsMockMvc.perform(put("/api/admissionDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(admissionDetails)))
                .andExpect(status().isOk());

        // Validate the AdmissionDetails in the database
        List<AdmissionDetails> admissionDetailss = admissionDetailsRepository.findAll();
        assertThat(admissionDetailss).hasSize(databaseSizeBeforeUpdate);
        AdmissionDetails testAdmissionDetails = admissionDetailss.get(admissionDetailss.size() - 1);
        assertThat(testAdmissionDetails.getAdmissionNo()).isEqualTo(UPDATED_ADMISSION_NO);
        assertThat(testAdmissionDetails.getAdmissionDate()).isEqualTo(UPDATED_ADMISSION_DATE);
    }

    @Test
    @Transactional
    public void deleteAdmissionDetails() throws Exception {
        // Initialize the database
        admissionDetailsRepository.saveAndFlush(admissionDetails);

		int databaseSizeBeforeDelete = admissionDetailsRepository.findAll().size();

        // Get the admissionDetails
        restAdmissionDetailsMockMvc.perform(delete("/api/admissionDetailss/{id}", admissionDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AdmissionDetails> admissionDetailss = admissionDetailsRepository.findAll();
        assertThat(admissionDetailss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
