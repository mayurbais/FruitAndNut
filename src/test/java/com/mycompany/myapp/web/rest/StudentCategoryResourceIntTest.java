package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.StudentCategory;
import com.mycompany.myapp.repository.StudentCategoryRepository;

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
 * Test class for the StudentCategoryResource REST controller.
 *
 * @see StudentCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StudentCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_VALUE = "AAAAA";
    private static final String UPDATED_VALUE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private StudentCategoryRepository studentCategoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStudentCategoryMockMvc;

    private StudentCategory studentCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudentCategoryResource studentCategoryResource = new StudentCategoryResource();
        ReflectionTestUtils.setField(studentCategoryResource, "studentCategoryRepository", studentCategoryRepository);
        this.restStudentCategoryMockMvc = MockMvcBuilders.standaloneSetup(studentCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        studentCategory = new StudentCategory();
        studentCategory.setName(DEFAULT_NAME);
        studentCategory.setValue(DEFAULT_VALUE);
        studentCategory.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createStudentCategory() throws Exception {
        int databaseSizeBeforeCreate = studentCategoryRepository.findAll().size();

        // Create the StudentCategory

        restStudentCategoryMockMvc.perform(post("/api/studentCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentCategory)))
                .andExpect(status().isCreated());

        // Validate the StudentCategory in the database
        List<StudentCategory> studentCategorys = studentCategoryRepository.findAll();
        assertThat(studentCategorys).hasSize(databaseSizeBeforeCreate + 1);
        StudentCategory testStudentCategory = studentCategorys.get(studentCategorys.size() - 1);
        assertThat(testStudentCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStudentCategory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testStudentCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentCategoryRepository.findAll().size();
        // set the field null
        studentCategory.setName(null);

        // Create the StudentCategory, which fails.

        restStudentCategoryMockMvc.perform(post("/api/studentCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentCategory)))
                .andExpect(status().isBadRequest());

        List<StudentCategory> studentCategorys = studentCategoryRepository.findAll();
        assertThat(studentCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentCategoryRepository.findAll().size();
        // set the field null
        studentCategory.setValue(null);

        // Create the StudentCategory, which fails.

        restStudentCategoryMockMvc.perform(post("/api/studentCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentCategory)))
                .andExpect(status().isBadRequest());

        List<StudentCategory> studentCategorys = studentCategoryRepository.findAll();
        assertThat(studentCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentCategorys() throws Exception {
        // Initialize the database
        studentCategoryRepository.saveAndFlush(studentCategory);

        // Get all the studentCategorys
        restStudentCategoryMockMvc.perform(get("/api/studentCategorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(studentCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getStudentCategory() throws Exception {
        // Initialize the database
        studentCategoryRepository.saveAndFlush(studentCategory);

        // Get the studentCategory
        restStudentCategoryMockMvc.perform(get("/api/studentCategorys/{id}", studentCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(studentCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentCategory() throws Exception {
        // Get the studentCategory
        restStudentCategoryMockMvc.perform(get("/api/studentCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentCategory() throws Exception {
        // Initialize the database
        studentCategoryRepository.saveAndFlush(studentCategory);

		int databaseSizeBeforeUpdate = studentCategoryRepository.findAll().size();

        // Update the studentCategory
        studentCategory.setName(UPDATED_NAME);
        studentCategory.setValue(UPDATED_VALUE);
        studentCategory.setDescription(UPDATED_DESCRIPTION);

        restStudentCategoryMockMvc.perform(put("/api/studentCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentCategory)))
                .andExpect(status().isOk());

        // Validate the StudentCategory in the database
        List<StudentCategory> studentCategorys = studentCategoryRepository.findAll();
        assertThat(studentCategorys).hasSize(databaseSizeBeforeUpdate);
        StudentCategory testStudentCategory = studentCategorys.get(studentCategorys.size() - 1);
        assertThat(testStudentCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudentCategory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testStudentCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteStudentCategory() throws Exception {
        // Initialize the database
        studentCategoryRepository.saveAndFlush(studentCategory);

		int databaseSizeBeforeDelete = studentCategoryRepository.findAll().size();

        // Get the studentCategory
        restStudentCategoryMockMvc.perform(delete("/api/studentCategorys/{id}", studentCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentCategory> studentCategorys = studentCategoryRepository.findAll();
        assertThat(studentCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
