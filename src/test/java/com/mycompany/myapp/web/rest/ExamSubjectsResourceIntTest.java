package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.ExamSubjects;
import com.mycompany.myapp.repository.ExamSubjectsRepository;

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


/**
 * Test class for the ExamSubjectsResource REST controller.
 *
 * @see ExamSubjectsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ExamSubjectsResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final Integer DEFAULT_MAX_MARKS = 1;
    private static final Integer UPDATED_MAX_MARKS = 2;

    private static final Integer DEFAULT_MIN_PASS_MARK = 1;
    private static final Integer UPDATED_MIN_PASS_MARK = 2;

    private static final Boolean DEFAULT_IS_GRADE = false;
    private static final Boolean UPDATED_IS_GRADE = true;

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_TIME_STR = dateTimeFormatter.format(DEFAULT_START_TIME);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_TIME_STR = dateTimeFormatter.format(DEFAULT_END_TIME);

    private static final ZonedDateTime DEFAULT_CONDUCTING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CONDUCTING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CONDUCTING_DATE_STR = dateTimeFormatter.format(DEFAULT_CONDUCTING_DATE);

    private static final Boolean DEFAULT_IS_RESULT_PUBLISHED = false;
    private static final Boolean UPDATED_IS_RESULT_PUBLISHED = true;

    private static final Integer DEFAULT_CLASS_AVERAGE = 1;
    private static final Integer UPDATED_CLASS_AVERAGE = 2;
    private static final String DEFAULT_REMARK_BY_PRINCIPAL = "AAAAA";
    private static final String UPDATED_REMARK_BY_PRINCIPAL = "BBBBB";
    private static final String DEFAULT_REMARK_BY_HEAD_TEACHER = "AAAAA";
    private static final String UPDATED_REMARK_BY_HEAD_TEACHER = "BBBBB";

    @Inject
    private ExamSubjectsRepository examSubjectsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restExamSubjectsMockMvc;

    private ExamSubjects examSubjects;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ExamSubjectsResource examSubjectsResource = new ExamSubjectsResource();
        ReflectionTestUtils.setField(examSubjectsResource, "examSubjectsRepository", examSubjectsRepository);
        this.restExamSubjectsMockMvc = MockMvcBuilders.standaloneSetup(examSubjectsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        examSubjects = new ExamSubjects();
        examSubjects.setMaxMarks(DEFAULT_MAX_MARKS);
        examSubjects.setMinPassMark(DEFAULT_MIN_PASS_MARK);
        examSubjects.setIsGrade(DEFAULT_IS_GRADE);
        examSubjects.setStartTime(DEFAULT_START_TIME);
        examSubjects.setEndTime(DEFAULT_END_TIME);
        examSubjects.setConductingDate(DEFAULT_CONDUCTING_DATE);
        examSubjects.setIsResultPublished(DEFAULT_IS_RESULT_PUBLISHED);
        examSubjects.setClassAverage(DEFAULT_CLASS_AVERAGE);
        examSubjects.setRemarkByPrincipal(DEFAULT_REMARK_BY_PRINCIPAL);
        examSubjects.setRemarkByHeadTeacher(DEFAULT_REMARK_BY_HEAD_TEACHER);
    }

    @Test
    @Transactional
    public void createExamSubjects() throws Exception {
        int databaseSizeBeforeCreate = examSubjectsRepository.findAll().size();

        // Create the ExamSubjects

        restExamSubjectsMockMvc.perform(post("/api/examSubjectss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(examSubjects)))
                .andExpect(status().isCreated());

        // Validate the ExamSubjects in the database
        List<ExamSubjects> examSubjectss = examSubjectsRepository.findAll();
        assertThat(examSubjectss).hasSize(databaseSizeBeforeCreate + 1);
        ExamSubjects testExamSubjects = examSubjectss.get(examSubjectss.size() - 1);
        assertThat(testExamSubjects.getMaxMarks()).isEqualTo(DEFAULT_MAX_MARKS);
        assertThat(testExamSubjects.getMinPassMark()).isEqualTo(DEFAULT_MIN_PASS_MARK);
        assertThat(testExamSubjects.getIsGrade()).isEqualTo(DEFAULT_IS_GRADE);
        assertThat(testExamSubjects.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testExamSubjects.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testExamSubjects.getConductingDate()).isEqualTo(DEFAULT_CONDUCTING_DATE);
        assertThat(testExamSubjects.getIsResultPublished()).isEqualTo(DEFAULT_IS_RESULT_PUBLISHED);
        assertThat(testExamSubjects.getClassAverage()).isEqualTo(DEFAULT_CLASS_AVERAGE);
        assertThat(testExamSubjects.getRemarkByPrincipal()).isEqualTo(DEFAULT_REMARK_BY_PRINCIPAL);
        assertThat(testExamSubjects.getRemarkByHeadTeacher()).isEqualTo(DEFAULT_REMARK_BY_HEAD_TEACHER);
    }

    @Test
    @Transactional
    public void getAllExamSubjectss() throws Exception {
        // Initialize the database
        examSubjectsRepository.saveAndFlush(examSubjects);

        // Get all the examSubjectss
        restExamSubjectsMockMvc.perform(get("/api/examSubjectss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(examSubjects.getId().intValue())))
                .andExpect(jsonPath("$.[*].maxMarks").value(hasItem(DEFAULT_MAX_MARKS)))
                .andExpect(jsonPath("$.[*].minPassMark").value(hasItem(DEFAULT_MIN_PASS_MARK)))
                .andExpect(jsonPath("$.[*].isGrade").value(hasItem(DEFAULT_IS_GRADE.booleanValue())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME_STR)))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME_STR)))
                .andExpect(jsonPath("$.[*].conductingDate").value(hasItem(DEFAULT_CONDUCTING_DATE_STR)))
                .andExpect(jsonPath("$.[*].isResultPublished").value(hasItem(DEFAULT_IS_RESULT_PUBLISHED.booleanValue())))
                .andExpect(jsonPath("$.[*].classAverage").value(hasItem(DEFAULT_CLASS_AVERAGE)))
                .andExpect(jsonPath("$.[*].remarkByPrincipal").value(hasItem(DEFAULT_REMARK_BY_PRINCIPAL.toString())))
                .andExpect(jsonPath("$.[*].remarkByHeadTeacher").value(hasItem(DEFAULT_REMARK_BY_HEAD_TEACHER.toString())));
    }

    @Test
    @Transactional
    public void getExamSubjects() throws Exception {
        // Initialize the database
        examSubjectsRepository.saveAndFlush(examSubjects);

        // Get the examSubjects
        restExamSubjectsMockMvc.perform(get("/api/examSubjectss/{id}", examSubjects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(examSubjects.getId().intValue()))
            .andExpect(jsonPath("$.maxMarks").value(DEFAULT_MAX_MARKS))
            .andExpect(jsonPath("$.minPassMark").value(DEFAULT_MIN_PASS_MARK))
            .andExpect(jsonPath("$.isGrade").value(DEFAULT_IS_GRADE.booleanValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME_STR))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME_STR))
            .andExpect(jsonPath("$.conductingDate").value(DEFAULT_CONDUCTING_DATE_STR))
            .andExpect(jsonPath("$.isResultPublished").value(DEFAULT_IS_RESULT_PUBLISHED.booleanValue()))
            .andExpect(jsonPath("$.classAverage").value(DEFAULT_CLASS_AVERAGE))
            .andExpect(jsonPath("$.remarkByPrincipal").value(DEFAULT_REMARK_BY_PRINCIPAL.toString()))
            .andExpect(jsonPath("$.remarkByHeadTeacher").value(DEFAULT_REMARK_BY_HEAD_TEACHER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamSubjects() throws Exception {
        // Get the examSubjects
        restExamSubjectsMockMvc.perform(get("/api/examSubjectss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamSubjects() throws Exception {
        // Initialize the database
        examSubjectsRepository.saveAndFlush(examSubjects);

		int databaseSizeBeforeUpdate = examSubjectsRepository.findAll().size();

        // Update the examSubjects
        examSubjects.setMaxMarks(UPDATED_MAX_MARKS);
        examSubjects.setMinPassMark(UPDATED_MIN_PASS_MARK);
        examSubjects.setIsGrade(UPDATED_IS_GRADE);
        examSubjects.setStartTime(UPDATED_START_TIME);
        examSubjects.setEndTime(UPDATED_END_TIME);
        examSubjects.setConductingDate(UPDATED_CONDUCTING_DATE);
        examSubjects.setIsResultPublished(UPDATED_IS_RESULT_PUBLISHED);
        examSubjects.setClassAverage(UPDATED_CLASS_AVERAGE);
        examSubjects.setRemarkByPrincipal(UPDATED_REMARK_BY_PRINCIPAL);
        examSubjects.setRemarkByHeadTeacher(UPDATED_REMARK_BY_HEAD_TEACHER);

        restExamSubjectsMockMvc.perform(put("/api/examSubjectss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(examSubjects)))
                .andExpect(status().isOk());

        // Validate the ExamSubjects in the database
        List<ExamSubjects> examSubjectss = examSubjectsRepository.findAll();
        assertThat(examSubjectss).hasSize(databaseSizeBeforeUpdate);
        ExamSubjects testExamSubjects = examSubjectss.get(examSubjectss.size() - 1);
        assertThat(testExamSubjects.getMaxMarks()).isEqualTo(UPDATED_MAX_MARKS);
        assertThat(testExamSubjects.getMinPassMark()).isEqualTo(UPDATED_MIN_PASS_MARK);
        assertThat(testExamSubjects.getIsGrade()).isEqualTo(UPDATED_IS_GRADE);
        assertThat(testExamSubjects.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testExamSubjects.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testExamSubjects.getConductingDate()).isEqualTo(UPDATED_CONDUCTING_DATE);
        assertThat(testExamSubjects.getIsResultPublished()).isEqualTo(UPDATED_IS_RESULT_PUBLISHED);
        assertThat(testExamSubjects.getClassAverage()).isEqualTo(UPDATED_CLASS_AVERAGE);
        assertThat(testExamSubjects.getRemarkByPrincipal()).isEqualTo(UPDATED_REMARK_BY_PRINCIPAL);
        assertThat(testExamSubjects.getRemarkByHeadTeacher()).isEqualTo(UPDATED_REMARK_BY_HEAD_TEACHER);
    }

    @Test
    @Transactional
    public void deleteExamSubjects() throws Exception {
        // Initialize the database
        examSubjectsRepository.saveAndFlush(examSubjects);

		int databaseSizeBeforeDelete = examSubjectsRepository.findAll().size();

        // Get the examSubjects
        restExamSubjectsMockMvc.perform(delete("/api/examSubjectss/{id}", examSubjects.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamSubjects> examSubjectss = examSubjectsRepository.findAll();
        assertThat(examSubjectss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
