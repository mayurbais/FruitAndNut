package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Exam;
import com.mycompany.myapp.repository.ExamRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.ExamType;
import com.mycompany.myapp.domain.enumeration.ProgressStatus;

/**
 * Test class for the ExamResource REST controller.
 *
 * @see ExamResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ExamResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";


    private static final ExamType DEFAULT_TYPE = ExamType.MARKS;
    private static final ExamType UPDATED_TYPE = ExamType.GRADES;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);

    private static final Boolean DEFAULT_IS_PUBLISHED = false;
    private static final Boolean UPDATED_IS_PUBLISHED = true;


    private static final ProgressStatus DEFAULT_PROGRESS_STATUS = ProgressStatus.IN_PROGRESS;
    private static final ProgressStatus UPDATED_PROGRESS_STATUS = ProgressStatus.NEW;

    private static final Boolean DEFAULT_IS_RESULT_PUBLISHED = false;
    private static final Boolean UPDATED_IS_RESULT_PUBLISHED = true;

    private static final Integer DEFAULT_CLASS_AVERAGE = 1;
    private static final Integer UPDATED_CLASS_AVERAGE = 2;
    private static final String DEFAULT_REMARK_BY_PRINCIPAL = "AAAAA";
    private static final String UPDATED_REMARK_BY_PRINCIPAL = "BBBBB";
    private static final String DEFAULT_REMARK_BY_HEAD_TEACHER = "AAAAA";
    private static final String UPDATED_REMARK_BY_HEAD_TEACHER = "BBBBB";

    @Inject
    private ExamRepository examRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restExamMockMvc;

    private Exam exam;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExamResource examResource = new ExamResource();
        ReflectionTestUtils.setField(examResource, "examRepository", examRepository);
        this.restExamMockMvc = MockMvcBuilders.standaloneSetup(examResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        exam = new Exam();
        exam.setName(DEFAULT_NAME);
        exam.setType(DEFAULT_TYPE);
        exam.setStartDate(DEFAULT_START_DATE);
        exam.setEndDate(DEFAULT_END_DATE);
        exam.setIsPublished(DEFAULT_IS_PUBLISHED);
        exam.setProgressStatus(DEFAULT_PROGRESS_STATUS);
        exam.setIsResultPublished(DEFAULT_IS_RESULT_PUBLISHED);
        exam.setClassAverage(DEFAULT_CLASS_AVERAGE);
        exam.setRemarkByPrincipal(DEFAULT_REMARK_BY_PRINCIPAL);
        exam.setRemarkByHeadTeacher(DEFAULT_REMARK_BY_HEAD_TEACHER);
    }

    @Test
    @Transactional
    public void createExam() throws Exception {
        int databaseSizeBeforeCreate = examRepository.findAll().size();

        // Create the Exam

        restExamMockMvc.perform(post("/api/exams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exam)))
                .andExpect(status().isCreated());

        // Validate the Exam in the database
        List<Exam> exams = examRepository.findAll();
        assertThat(exams).hasSize(databaseSizeBeforeCreate + 1);
        Exam testExam = exams.get(exams.size() - 1);
        assertThat(testExam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExam.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testExam.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExam.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExam.getIsPublished()).isEqualTo(DEFAULT_IS_PUBLISHED);
        assertThat(testExam.getProgressStatus()).isEqualTo(DEFAULT_PROGRESS_STATUS);
        assertThat(testExam.getIsResultPublished()).isEqualTo(DEFAULT_IS_RESULT_PUBLISHED);
        assertThat(testExam.getClassAverage()).isEqualTo(DEFAULT_CLASS_AVERAGE);
        assertThat(testExam.getRemarkByPrincipal()).isEqualTo(DEFAULT_REMARK_BY_PRINCIPAL);
        assertThat(testExam.getRemarkByHeadTeacher()).isEqualTo(DEFAULT_REMARK_BY_HEAD_TEACHER);
    }

    @Test
    @Transactional
    public void getAllExams() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get all the exams
        restExamMockMvc.perform(get("/api/exams?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(exam.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].isPublished").value(hasItem(DEFAULT_IS_PUBLISHED.booleanValue())))
                .andExpect(jsonPath("$.[*].progressStatus").value(hasItem(DEFAULT_PROGRESS_STATUS.toString())))
                .andExpect(jsonPath("$.[*].isResultPublished").value(hasItem(DEFAULT_IS_RESULT_PUBLISHED.booleanValue())))
                .andExpect(jsonPath("$.[*].classAverage").value(hasItem(DEFAULT_CLASS_AVERAGE)))
                .andExpect(jsonPath("$.[*].remarkByPrincipal").value(hasItem(DEFAULT_REMARK_BY_PRINCIPAL.toString())))
                .andExpect(jsonPath("$.[*].remarkByHeadTeacher").value(hasItem(DEFAULT_REMARK_BY_HEAD_TEACHER.toString())));
    }

    @Test
    @Transactional
    public void getExam() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

        // Get the exam
        restExamMockMvc.perform(get("/api/exams/{id}", exam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(exam.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.isPublished").value(DEFAULT_IS_PUBLISHED.booleanValue()))
            .andExpect(jsonPath("$.progressStatus").value(DEFAULT_PROGRESS_STATUS.toString()))
            .andExpect(jsonPath("$.isResultPublished").value(DEFAULT_IS_RESULT_PUBLISHED.booleanValue()))
            .andExpect(jsonPath("$.classAverage").value(DEFAULT_CLASS_AVERAGE))
            .andExpect(jsonPath("$.remarkByPrincipal").value(DEFAULT_REMARK_BY_PRINCIPAL.toString()))
            .andExpect(jsonPath("$.remarkByHeadTeacher").value(DEFAULT_REMARK_BY_HEAD_TEACHER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExam() throws Exception {
        // Get the exam
        restExamMockMvc.perform(get("/api/exams/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExam() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

		int databaseSizeBeforeUpdate = examRepository.findAll().size();

        // Update the exam
        exam.setName(UPDATED_NAME);
        exam.setType(UPDATED_TYPE);
        exam.setStartDate(UPDATED_START_DATE);
        exam.setEndDate(UPDATED_END_DATE);
        exam.setIsPublished(UPDATED_IS_PUBLISHED);
        exam.setProgressStatus(UPDATED_PROGRESS_STATUS);
        exam.setIsResultPublished(UPDATED_IS_RESULT_PUBLISHED);
        exam.setClassAverage(UPDATED_CLASS_AVERAGE);
        exam.setRemarkByPrincipal(UPDATED_REMARK_BY_PRINCIPAL);
        exam.setRemarkByHeadTeacher(UPDATED_REMARK_BY_HEAD_TEACHER);

        restExamMockMvc.perform(put("/api/exams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(exam)))
                .andExpect(status().isOk());

        // Validate the Exam in the database
        List<Exam> exams = examRepository.findAll();
        assertThat(exams).hasSize(databaseSizeBeforeUpdate);
        Exam testExam = exams.get(exams.size() - 1);
        assertThat(testExam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExam.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testExam.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExam.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExam.getIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
        assertThat(testExam.getProgressStatus()).isEqualTo(UPDATED_PROGRESS_STATUS);
        assertThat(testExam.getIsResultPublished()).isEqualTo(UPDATED_IS_RESULT_PUBLISHED);
        assertThat(testExam.getClassAverage()).isEqualTo(UPDATED_CLASS_AVERAGE);
        assertThat(testExam.getRemarkByPrincipal()).isEqualTo(UPDATED_REMARK_BY_PRINCIPAL);
        assertThat(testExam.getRemarkByHeadTeacher()).isEqualTo(UPDATED_REMARK_BY_HEAD_TEACHER);
    }

    @Test
    @Transactional
    public void deleteExam() throws Exception {
        // Initialize the database
        examRepository.saveAndFlush(exam);

		int databaseSizeBeforeDelete = examRepository.findAll().size();

        // Get the exam
        restExamMockMvc.perform(delete("/api/exams/{id}", exam.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Exam> exams = examRepository.findAll();
        assertThat(exams).hasSize(databaseSizeBeforeDelete - 1);
    }
}
