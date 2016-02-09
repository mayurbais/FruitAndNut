package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.DairyTemplate;
import com.mycompany.myapp.repository.DairyTemplateRepository;

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

import com.mycompany.myapp.domain.enumeration.DairyEntryType;

/**
 * Test class for the DairyTemplateResource REST controller.
 *
 * @see DairyTemplateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DairyTemplateResourceIntTest {

    private static final String DEFAULT_DATE = "AAAAA";
    private static final String UPDATED_DATE = "BBBBB";


    private static final DairyEntryType DEFAULT_ENTRY_TYPE = DairyEntryType.HOMEWORK;
    private static final DairyEntryType UPDATED_ENTRY_TYPE = DairyEntryType.PROJECT;

    @Inject
    private DairyTemplateRepository dairyTemplateRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDairyTemplateMockMvc;

    private DairyTemplate dairyTemplate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DairyTemplateResource dairyTemplateResource = new DairyTemplateResource();
        ReflectionTestUtils.setField(dairyTemplateResource, "dairyTemplateRepository", dairyTemplateRepository);
        this.restDairyTemplateMockMvc = MockMvcBuilders.standaloneSetup(dairyTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dairyTemplate = new DairyTemplate();
        dairyTemplate.setDate(DEFAULT_DATE);
        dairyTemplate.setEntryType(DEFAULT_ENTRY_TYPE);
    }

    @Test
    @Transactional
    public void createDairyTemplate() throws Exception {
        int databaseSizeBeforeCreate = dairyTemplateRepository.findAll().size();

        // Create the DairyTemplate

        restDairyTemplateMockMvc.perform(post("/api/dairyTemplates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dairyTemplate)))
                .andExpect(status().isCreated());

        // Validate the DairyTemplate in the database
        List<DairyTemplate> dairyTemplates = dairyTemplateRepository.findAll();
        assertThat(dairyTemplates).hasSize(databaseSizeBeforeCreate + 1);
        DairyTemplate testDairyTemplate = dairyTemplates.get(dairyTemplates.size() - 1);
        assertThat(testDairyTemplate.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDairyTemplate.getEntryType()).isEqualTo(DEFAULT_ENTRY_TYPE);
    }

    @Test
    @Transactional
    public void getAllDairyTemplates() throws Exception {
        // Initialize the database
        dairyTemplateRepository.saveAndFlush(dairyTemplate);

        // Get all the dairyTemplates
        restDairyTemplateMockMvc.perform(get("/api/dairyTemplates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dairyTemplate.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].entryType").value(hasItem(DEFAULT_ENTRY_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getDairyTemplate() throws Exception {
        // Initialize the database
        dairyTemplateRepository.saveAndFlush(dairyTemplate);

        // Get the dairyTemplate
        restDairyTemplateMockMvc.perform(get("/api/dairyTemplates/{id}", dairyTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dairyTemplate.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.entryType").value(DEFAULT_ENTRY_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDairyTemplate() throws Exception {
        // Get the dairyTemplate
        restDairyTemplateMockMvc.perform(get("/api/dairyTemplates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDairyTemplate() throws Exception {
        // Initialize the database
        dairyTemplateRepository.saveAndFlush(dairyTemplate);

		int databaseSizeBeforeUpdate = dairyTemplateRepository.findAll().size();

        // Update the dairyTemplate
        dairyTemplate.setDate(UPDATED_DATE);
        dairyTemplate.setEntryType(UPDATED_ENTRY_TYPE);

        restDairyTemplateMockMvc.perform(put("/api/dairyTemplates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dairyTemplate)))
                .andExpect(status().isOk());

        // Validate the DairyTemplate in the database
        List<DairyTemplate> dairyTemplates = dairyTemplateRepository.findAll();
        assertThat(dairyTemplates).hasSize(databaseSizeBeforeUpdate);
        DairyTemplate testDairyTemplate = dairyTemplates.get(dairyTemplates.size() - 1);
        assertThat(testDairyTemplate.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDairyTemplate.getEntryType()).isEqualTo(UPDATED_ENTRY_TYPE);
    }

    @Test
    @Transactional
    public void deleteDairyTemplate() throws Exception {
        // Initialize the database
        dairyTemplateRepository.saveAndFlush(dairyTemplate);

		int databaseSizeBeforeDelete = dairyTemplateRepository.findAll().size();

        // Get the dairyTemplate
        restDairyTemplateMockMvc.perform(delete("/api/dairyTemplates/{id}", dairyTemplate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DairyTemplate> dairyTemplates = dairyTemplateRepository.findAll();
        assertThat(dairyTemplates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
