package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.ExamResult;
import com.mycompany.myapp.repository.ExamResultRepository;

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
 * Test class for the ExamResultResource REST controller.
 *
 * @see ExamResultResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ExamResultResourceIntTest {


    private static final Integer DEFAULT_STUDENT_ID = 1;
    private static final Integer UPDATED_STUDENT_ID = 2;
    private static final String DEFAULT_PERCENTAGE = "AAAAA";
    private static final String UPDATED_PERCENTAGE = "BBBBB";
    private static final String DEFAULT_GRADE = "AAAAA";
    private static final String UPDATED_GRADE = "BBBBB";

    private static final Boolean DEFAULT_IS_PASSED = false;
    private static final Boolean UPDATED_IS_PASSED = true;

    private static final Boolean DEFAULT_IS_ABSENT = false;
    private static final Boolean UPDATED_IS_ABSENT = true;
    private static final String DEFAULT_REMARK = "AAAAA";
    private static final String UPDATED_REMARK = "BBBBB";

    @Inject
    private ExamResultRepository examResultRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restExamResultMockMvc;

    private ExamResult examResult;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExamResultResource examResultResource = new ExamResultResource();
        ReflectionTestUtils.setField(examResultResource, "examResultRepository", examResultRepository);
        this.restExamResultMockMvc = MockMvcBuilders.standaloneSetup(examResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        examResult = new ExamResult();
        examResult.setStudentId(DEFAULT_STUDENT_ID);
        examResult.setPercentage(DEFAULT_PERCENTAGE);
        examResult.setGrade(DEFAULT_GRADE);
        examResult.setIsPassed(DEFAULT_IS_PASSED);
        examResult.setIsAbsent(DEFAULT_IS_ABSENT);
        examResult.setRemark(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createExamResult() throws Exception {
        int databaseSizeBeforeCreate = examResultRepository.findAll().size();

        // Create the ExamResult

        restExamResultMockMvc.perform(post("/api/examResults")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(examResult)))
                .andExpect(status().isCreated());

        // Validate the ExamResult in the database
        List<ExamResult> examResults = examResultRepository.findAll();
        assertThat(examResults).hasSize(databaseSizeBeforeCreate + 1);
        ExamResult testExamResult = examResults.get(examResults.size() - 1);
        assertThat(testExamResult.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);
        assertThat(testExamResult.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testExamResult.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testExamResult.getIsPassed()).isEqualTo(DEFAULT_IS_PASSED);
        assertThat(testExamResult.getIsAbsent()).isEqualTo(DEFAULT_IS_ABSENT);
        assertThat(testExamResult.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void getAllExamResults() throws Exception {
        // Initialize the database
        examResultRepository.saveAndFlush(examResult);

        // Get all the examResults
        restExamResultMockMvc.perform(get("/api/examResults?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(examResult.getId().intValue())))
                .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID)))
                .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.toString())))
                .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
                .andExpect(jsonPath("$.[*].isPassed").value(hasItem(DEFAULT_IS_PASSED.booleanValue())))
                .andExpect(jsonPath("$.[*].isAbsent").value(hasItem(DEFAULT_IS_ABSENT.booleanValue())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getExamResult() throws Exception {
        // Initialize the database
        examResultRepository.saveAndFlush(examResult);

        // Get the examResult
        restExamResultMockMvc.perform(get("/api/examResults/{id}", examResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(examResult.getId().intValue()))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.isPassed").value(DEFAULT_IS_PASSED.booleanValue()))
            .andExpect(jsonPath("$.isAbsent").value(DEFAULT_IS_ABSENT.booleanValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamResult() throws Exception {
        // Get the examResult
        restExamResultMockMvc.perform(get("/api/examResults/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamResult() throws Exception {
        // Initialize the database
        examResultRepository.saveAndFlush(examResult);

		int databaseSizeBeforeUpdate = examResultRepository.findAll().size();

        // Update the examResult
        examResult.setStudentId(UPDATED_STUDENT_ID);
        examResult.setPercentage(UPDATED_PERCENTAGE);
        examResult.setGrade(UPDATED_GRADE);
        examResult.setIsPassed(UPDATED_IS_PASSED);
        examResult.setIsAbsent(UPDATED_IS_ABSENT);
        examResult.setRemark(UPDATED_REMARK);

        restExamResultMockMvc.perform(put("/api/examResults")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(examResult)))
                .andExpect(status().isOk());

        // Validate the ExamResult in the database
        List<ExamResult> examResults = examResultRepository.findAll();
        assertThat(examResults).hasSize(databaseSizeBeforeUpdate);
        ExamResult testExamResult = examResults.get(examResults.size() - 1);
        assertThat(testExamResult.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testExamResult.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testExamResult.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testExamResult.getIsPassed()).isEqualTo(UPDATED_IS_PASSED);
        assertThat(testExamResult.getIsAbsent()).isEqualTo(UPDATED_IS_ABSENT);
        assertThat(testExamResult.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void deleteExamResult() throws Exception {
        // Initialize the database
        examResultRepository.saveAndFlush(examResult);

		int databaseSizeBeforeDelete = examResultRepository.findAll().size();

        // Get the examResult
        restExamResultMockMvc.perform(delete("/api/examResults/{id}", examResult.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamResult> examResults = examResultRepository.findAll();
        assertThat(examResults).hasSize(databaseSizeBeforeDelete - 1);
    }
}
