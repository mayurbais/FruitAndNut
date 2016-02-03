package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.ModuleLOV;
import com.mycompany.myapp.repository.ModuleLOVRepository;

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
 * Test class for the ModuleLOVResource REST controller.
 *
 * @see ModuleLOVResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ModuleLOVResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAA";
    private static final String UPDATED_TYPE = "BBBBB";
    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";

    @Inject
    private ModuleLOVRepository moduleLOVRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restModuleLOVMockMvc;

    private ModuleLOV moduleLOV;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ModuleLOVResource moduleLOVResource = new ModuleLOVResource();
        ReflectionTestUtils.setField(moduleLOVResource, "moduleLOVRepository", moduleLOVRepository);
        this.restModuleLOVMockMvc = MockMvcBuilders.standaloneSetup(moduleLOVResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        moduleLOV = new ModuleLOV();
        moduleLOV.setType(DEFAULT_TYPE);
        moduleLOV.setName(DEFAULT_NAME);
        moduleLOV.setValue(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createModuleLOV() throws Exception {
        int databaseSizeBeforeCreate = moduleLOVRepository.findAll().size();

        // Create the ModuleLOV

        restModuleLOVMockMvc.perform(post("/api/moduleLOVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(moduleLOV)))
                .andExpect(status().isCreated());

        // Validate the ModuleLOV in the database
        List<ModuleLOV> moduleLOVs = moduleLOVRepository.findAll();
        assertThat(moduleLOVs).hasSize(databaseSizeBeforeCreate + 1);
        ModuleLOV testModuleLOV = moduleLOVs.get(moduleLOVs.size() - 1);
        assertThat(testModuleLOV.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testModuleLOV.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testModuleLOV.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void getAllModuleLOVs() throws Exception {
        // Initialize the database
        moduleLOVRepository.saveAndFlush(moduleLOV);

        // Get all the moduleLOVs
        restModuleLOVMockMvc.perform(get("/api/moduleLOVs?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(moduleLOV.getId().intValue())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getModuleLOV() throws Exception {
        // Initialize the database
        moduleLOVRepository.saveAndFlush(moduleLOV);

        // Get the moduleLOV
        restModuleLOVMockMvc.perform(get("/api/moduleLOVs/{id}", moduleLOV.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(moduleLOV.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModuleLOV() throws Exception {
        // Get the moduleLOV
        restModuleLOVMockMvc.perform(get("/api/moduleLOVs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModuleLOV() throws Exception {
        // Initialize the database
        moduleLOVRepository.saveAndFlush(moduleLOV);

		int databaseSizeBeforeUpdate = moduleLOVRepository.findAll().size();

        // Update the moduleLOV
        moduleLOV.setType(UPDATED_TYPE);
        moduleLOV.setName(UPDATED_NAME);
        moduleLOV.setValue(UPDATED_VALUE);

        restModuleLOVMockMvc.perform(put("/api/moduleLOVs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(moduleLOV)))
                .andExpect(status().isOk());

        // Validate the ModuleLOV in the database
        List<ModuleLOV> moduleLOVs = moduleLOVRepository.findAll();
        assertThat(moduleLOVs).hasSize(databaseSizeBeforeUpdate);
        ModuleLOV testModuleLOV = moduleLOVs.get(moduleLOVs.size() - 1);
        assertThat(testModuleLOV.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testModuleLOV.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testModuleLOV.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void deleteModuleLOV() throws Exception {
        // Initialize the database
        moduleLOVRepository.saveAndFlush(moduleLOV);

		int databaseSizeBeforeDelete = moduleLOVRepository.findAll().size();

        // Get the moduleLOV
        restModuleLOVMockMvc.perform(delete("/api/moduleLOVs/{id}", moduleLOV.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ModuleLOV> moduleLOVs = moduleLOVRepository.findAll();
        assertThat(moduleLOVs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
