package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.SubjectExamResult;
import com.mycompany.myapp.repository.SubjectExamResultRepository;

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
 * Test class for the SubjectExamResultResource REST controller.
 *
 * @see SubjectExamResultResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SubjectExamResultResourceIntTest {


    private static final Integer DEFAULT_STUDENT_ID = 1;
    private static final Integer UPDATED_STUDENT_ID = 2;

    private static final Integer DEFAULT_MARKS_OBTAINED = 1;
    private static final Integer UPDATED_MARKS_OBTAINED = 2;
    private static final String DEFAULT_GRADE = "AAAAA";
    private static final String UPDATED_GRADE = "BBBBB";

    private static final Boolean DEFAULT_IS_PASSED = false;
    private static final Boolean UPDATED_IS_PASSED = true;

    private static final Boolean DEFAULT_IS_ABSENT = false;
    private static final Boolean UPDATED_IS_ABSENT = true;
    private static final String DEFAULT_REMARK = "AAAAA";
    private static final String UPDATED_REMARK = "BBBBB";

    @Inject
    private SubjectExamResultRepository subjectExamResultRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubjectExamResultMockMvc;

    private SubjectExamResult subjectExamResult;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubjectExamResultResource subjectExamResultResource = new SubjectExamResultResource();
        ReflectionTestUtils.setField(subjectExamResultResource, "subjectExamResultRepository", subjectExamResultRepository);
        this.restSubjectExamResultMockMvc = MockMvcBuilders.standaloneSetup(subjectExamResultResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subjectExamResult = new SubjectExamResult();
        subjectExamResult.setStudentId(DEFAULT_STUDENT_ID);
        subjectExamResult.setMarksObtained(DEFAULT_MARKS_OBTAINED);
        subjectExamResult.setGrade(DEFAULT_GRADE);
        subjectExamResult.setIsPassed(DEFAULT_IS_PASSED);
        subjectExamResult.setIsAbsent(DEFAULT_IS_ABSENT);
        subjectExamResult.setRemark(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createSubjectExamResult() throws Exception {
        int databaseSizeBeforeCreate = subjectExamResultRepository.findAll().size();

        // Create the SubjectExamResult

        restSubjectExamResultMockMvc.perform(post("/api/subjectExamResults")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subjectExamResult)))
                .andExpect(status().isCreated());

        // Validate the SubjectExamResult in the database
        List<SubjectExamResult> subjectExamResults = subjectExamResultRepository.findAll();
        assertThat(subjectExamResults).hasSize(databaseSizeBeforeCreate + 1);
        SubjectExamResult testSubjectExamResult = subjectExamResults.get(subjectExamResults.size() - 1);
        assertThat(testSubjectExamResult.getStudentId()).isEqualTo(DEFAULT_STUDENT_ID);
        assertThat(testSubjectExamResult.getMarksObtained()).isEqualTo(DEFAULT_MARKS_OBTAINED);
        assertThat(testSubjectExamResult.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testSubjectExamResult.getIsPassed()).isEqualTo(DEFAULT_IS_PASSED);
        assertThat(testSubjectExamResult.getIsAbsent()).isEqualTo(DEFAULT_IS_ABSENT);
        assertThat(testSubjectExamResult.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void getAllSubjectExamResults() throws Exception {
        // Initialize the database
        subjectExamResultRepository.saveAndFlush(subjectExamResult);

        // Get all the subjectExamResults
        restSubjectExamResultMockMvc.perform(get("/api/subjectExamResults?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subjectExamResult.getId().intValue())))
                .andExpect(jsonPath("$.[*].studentId").value(hasItem(DEFAULT_STUDENT_ID)))
                .andExpect(jsonPath("$.[*].marksObtained").value(hasItem(DEFAULT_MARKS_OBTAINED)))
                .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
                .andExpect(jsonPath("$.[*].isPassed").value(hasItem(DEFAULT_IS_PASSED.booleanValue())))
                .andExpect(jsonPath("$.[*].isAbsent").value(hasItem(DEFAULT_IS_ABSENT.booleanValue())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getSubjectExamResult() throws Exception {
        // Initialize the database
        subjectExamResultRepository.saveAndFlush(subjectExamResult);

        // Get the subjectExamResult
        restSubjectExamResultMockMvc.perform(get("/api/subjectExamResults/{id}", subjectExamResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subjectExamResult.getId().intValue()))
            .andExpect(jsonPath("$.studentId").value(DEFAULT_STUDENT_ID))
            .andExpect(jsonPath("$.marksObtained").value(DEFAULT_MARKS_OBTAINED))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.isPassed").value(DEFAULT_IS_PASSED.booleanValue()))
            .andExpect(jsonPath("$.isAbsent").value(DEFAULT_IS_ABSENT.booleanValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubjectExamResult() throws Exception {
        // Get the subjectExamResult
        restSubjectExamResultMockMvc.perform(get("/api/subjectExamResults/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubjectExamResult() throws Exception {
        // Initialize the database
        subjectExamResultRepository.saveAndFlush(subjectExamResult);

		int databaseSizeBeforeUpdate = subjectExamResultRepository.findAll().size();

        // Update the subjectExamResult
        subjectExamResult.setStudentId(UPDATED_STUDENT_ID);
        subjectExamResult.setMarksObtained(UPDATED_MARKS_OBTAINED);
        subjectExamResult.setGrade(UPDATED_GRADE);
        subjectExamResult.setIsPassed(UPDATED_IS_PASSED);
        subjectExamResult.setIsAbsent(UPDATED_IS_ABSENT);
        subjectExamResult.setRemark(UPDATED_REMARK);

        restSubjectExamResultMockMvc.perform(put("/api/subjectExamResults")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subjectExamResult)))
                .andExpect(status().isOk());

        // Validate the SubjectExamResult in the database
        List<SubjectExamResult> subjectExamResults = subjectExamResultRepository.findAll();
        assertThat(subjectExamResults).hasSize(databaseSizeBeforeUpdate);
        SubjectExamResult testSubjectExamResult = subjectExamResults.get(subjectExamResults.size() - 1);
        assertThat(testSubjectExamResult.getStudentId()).isEqualTo(UPDATED_STUDENT_ID);
        assertThat(testSubjectExamResult.getMarksObtained()).isEqualTo(UPDATED_MARKS_OBTAINED);
        assertThat(testSubjectExamResult.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testSubjectExamResult.getIsPassed()).isEqualTo(UPDATED_IS_PASSED);
        assertThat(testSubjectExamResult.getIsAbsent()).isEqualTo(UPDATED_IS_ABSENT);
        assertThat(testSubjectExamResult.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void deleteSubjectExamResult() throws Exception {
        // Initialize the database
        subjectExamResultRepository.saveAndFlush(subjectExamResult);

		int databaseSizeBeforeDelete = subjectExamResultRepository.findAll().size();

        // Get the subjectExamResult
        restSubjectExamResultMockMvc.perform(delete("/api/subjectExamResults/{id}", subjectExamResult.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SubjectExamResult> subjectExamResults = subjectExamResultRepository.findAll();
        assertThat(subjectExamResults).hasSize(databaseSizeBeforeDelete - 1);
    }
}
