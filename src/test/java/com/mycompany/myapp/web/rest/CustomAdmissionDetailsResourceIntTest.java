package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.CustomAdmissionDetails;
import com.mycompany.myapp.repository.CustomAdmissionDetailsRepository;

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
 * Test class for the CustomAdmissionDetailsResource REST controller.
 *
 * @see CustomAdmissionDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CustomAdmissionDetailsResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Boolean DEFAULT_IS_MANDATORY = false;
    private static final Boolean UPDATED_IS_MANDATORY = true;
    private static final String DEFAULT_INPUT_METHOD = "AAAAA";
    private static final String UPDATED_INPUT_METHOD = "BBBBB";

    @Inject
    private CustomAdmissionDetailsRepository customAdmissionDetailsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCustomAdmissionDetailsMockMvc;

    private CustomAdmissionDetails customAdmissionDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CustomAdmissionDetailsResource customAdmissionDetailsResource = new CustomAdmissionDetailsResource();
        ReflectionTestUtils.setField(customAdmissionDetailsResource, "customAdmissionDetailsRepository", customAdmissionDetailsRepository);
        this.restCustomAdmissionDetailsMockMvc = MockMvcBuilders.standaloneSetup(customAdmissionDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        customAdmissionDetails = new CustomAdmissionDetails();
        customAdmissionDetails.setName(DEFAULT_NAME);
        customAdmissionDetails.setIsActive(DEFAULT_IS_ACTIVE);
        customAdmissionDetails.setIsMandatory(DEFAULT_IS_MANDATORY);
        customAdmissionDetails.setInputMethod(DEFAULT_INPUT_METHOD);
    }

    @Test
    @Transactional
    public void createCustomAdmissionDetails() throws Exception {
        int databaseSizeBeforeCreate = customAdmissionDetailsRepository.findAll().size();

        // Create the CustomAdmissionDetails

        restCustomAdmissionDetailsMockMvc.perform(post("/api/customAdmissionDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customAdmissionDetails)))
                .andExpect(status().isCreated());

        // Validate the CustomAdmissionDetails in the database
        List<CustomAdmissionDetails> customAdmissionDetailss = customAdmissionDetailsRepository.findAll();
        assertThat(customAdmissionDetailss).hasSize(databaseSizeBeforeCreate + 1);
        CustomAdmissionDetails testCustomAdmissionDetails = customAdmissionDetailss.get(customAdmissionDetailss.size() - 1);
        assertThat(testCustomAdmissionDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCustomAdmissionDetails.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCustomAdmissionDetails.getIsMandatory()).isEqualTo(DEFAULT_IS_MANDATORY);
        assertThat(testCustomAdmissionDetails.getInputMethod()).isEqualTo(DEFAULT_INPUT_METHOD);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = customAdmissionDetailsRepository.findAll().size();
        // set the field null
        customAdmissionDetails.setName(null);

        // Create the CustomAdmissionDetails, which fails.

        restCustomAdmissionDetailsMockMvc.perform(post("/api/customAdmissionDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customAdmissionDetails)))
                .andExpect(status().isBadRequest());

        List<CustomAdmissionDetails> customAdmissionDetailss = customAdmissionDetailsRepository.findAll();
        assertThat(customAdmissionDetailss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomAdmissionDetailss() throws Exception {
        // Initialize the database
        customAdmissionDetailsRepository.saveAndFlush(customAdmissionDetails);

        // Get all the customAdmissionDetailss
        restCustomAdmissionDetailsMockMvc.perform(get("/api/customAdmissionDetailss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(customAdmissionDetails.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
                .andExpect(jsonPath("$.[*].isMandatory").value(hasItem(DEFAULT_IS_MANDATORY.booleanValue())))
                .andExpect(jsonPath("$.[*].inputMethod").value(hasItem(DEFAULT_INPUT_METHOD.toString())));
    }

    @Test
    @Transactional
    public void getCustomAdmissionDetails() throws Exception {
        // Initialize the database
        customAdmissionDetailsRepository.saveAndFlush(customAdmissionDetails);

        // Get the customAdmissionDetails
        restCustomAdmissionDetailsMockMvc.perform(get("/api/customAdmissionDetailss/{id}", customAdmissionDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(customAdmissionDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.isMandatory").value(DEFAULT_IS_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.inputMethod").value(DEFAULT_INPUT_METHOD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCustomAdmissionDetails() throws Exception {
        // Get the customAdmissionDetails
        restCustomAdmissionDetailsMockMvc.perform(get("/api/customAdmissionDetailss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomAdmissionDetails() throws Exception {
        // Initialize the database
        customAdmissionDetailsRepository.saveAndFlush(customAdmissionDetails);

		int databaseSizeBeforeUpdate = customAdmissionDetailsRepository.findAll().size();

        // Update the customAdmissionDetails
        customAdmissionDetails.setName(UPDATED_NAME);
        customAdmissionDetails.setIsActive(UPDATED_IS_ACTIVE);
        customAdmissionDetails.setIsMandatory(UPDATED_IS_MANDATORY);
        customAdmissionDetails.setInputMethod(UPDATED_INPUT_METHOD);

        restCustomAdmissionDetailsMockMvc.perform(put("/api/customAdmissionDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(customAdmissionDetails)))
                .andExpect(status().isOk());

        // Validate the CustomAdmissionDetails in the database
        List<CustomAdmissionDetails> customAdmissionDetailss = customAdmissionDetailsRepository.findAll();
        assertThat(customAdmissionDetailss).hasSize(databaseSizeBeforeUpdate);
        CustomAdmissionDetails testCustomAdmissionDetails = customAdmissionDetailss.get(customAdmissionDetailss.size() - 1);
        assertThat(testCustomAdmissionDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCustomAdmissionDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCustomAdmissionDetails.getIsMandatory()).isEqualTo(UPDATED_IS_MANDATORY);
        assertThat(testCustomAdmissionDetails.getInputMethod()).isEqualTo(UPDATED_INPUT_METHOD);
    }

    @Test
    @Transactional
    public void deleteCustomAdmissionDetails() throws Exception {
        // Initialize the database
        customAdmissionDetailsRepository.saveAndFlush(customAdmissionDetails);

		int databaseSizeBeforeDelete = customAdmissionDetailsRepository.findAll().size();

        // Get the customAdmissionDetails
        restCustomAdmissionDetailsMockMvc.perform(delete("/api/customAdmissionDetailss/{id}", customAdmissionDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CustomAdmissionDetails> customAdmissionDetailss = customAdmissionDetailsRepository.findAll();
        assertThat(customAdmissionDetailss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
