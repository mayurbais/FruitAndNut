package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.SchoolDetails;
import com.mycompany.myapp.repository.SchoolDetailsRepository;

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

import com.mycompany.myapp.domain.enumeration.AttendanceType;
import com.mycompany.myapp.domain.enumeration.Days;
import com.mycompany.myapp.domain.enumeration.DateFormat;
import com.mycompany.myapp.domain.enumeration.GradingType;
import com.mycompany.myapp.domain.enumeration.InstitutionType;

/**
 * Test class for the SchoolDetailsResource REST controller.
 *
 * @see SchoolDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SchoolDetailsResourceIntTest {

    private static final String DEFAULT_SCHOOL_NAME = "AAAAA";
    private static final String UPDATED_SCHOOL_NAME = "BBBBB";
    private static final String DEFAULT_ADDRESS = "AAAAA";
    private static final String UPDATED_ADDRESS = "BBBBB";

    private static final Integer DEFAULT_PHONE_NUMBER = 1;
    private static final Integer UPDATED_PHONE_NUMBER = 2;


    private static final AttendanceType DEFAULT_ATTENDANCE_TYPE = AttendanceType.DAILY;
    private static final AttendanceType UPDATED_ATTENDANCE_TYPE = AttendanceType.WEEKLY;


    private static final Days DEFAULT_START_DAY_OF_THE_WEEK = Days.MONDAY;
    private static final Days UPDATED_START_DAY_OF_THE_WEEK = Days.TUESDAY;


    private static final DateFormat DEFAULT_DATE_FORMAT = DateFormat.MMDDYYYY;
    private static final DateFormat UPDATED_DATE_FORMAT = DateFormat.DDMMYYYY;

    private static final LocalDate DEFAULT_FINANCIAL_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINANCIAL_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FINANCIAL_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINANCIAL_END_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_LOGO = "AAAAA";
    private static final String UPDATED_LOGO = "BBBBB";


    private static final GradingType DEFAULT_GRADING_SYSTEM = GradingType.NORMAL;
    private static final GradingType UPDATED_GRADING_SYSTEM = GradingType.GPA;

    private static final Boolean DEFAULT_ENABLE_AUTO_INCREAMENT_OF_ADMISSION_NO = false;
    private static final Boolean UPDATED_ENABLE_AUTO_INCREAMENT_OF_ADMISSION_NO = true;

    private static final Boolean DEFAULT_ENABLE_NEWS_COMMENTS_MODERATION = false;
    private static final Boolean UPDATED_ENABLE_NEWS_COMMENTS_MODERATION = true;

    private static final Boolean DEFAULT_ENABLE_SIBLING = false;
    private static final Boolean UPDATED_ENABLE_SIBLING = true;

    private static final Boolean DEFAULT_ENABLE_PASSWORD_CHANGE_AT_FIRST_LOGIN = false;
    private static final Boolean UPDATED_ENABLE_PASSWORD_CHANGE_AT_FIRST_LOGIN = true;

    private static final Boolean DEFAULT_ENABLE_ROLL_NUMBER_FOR_STUDENT = false;
    private static final Boolean UPDATED_ENABLE_ROLL_NUMBER_FOR_STUDENT = true;


    private static final InstitutionType DEFAULT_INSTITUTION_TYPE = InstitutionType.K12;
    private static final InstitutionType UPDATED_INSTITUTION_TYPE = InstitutionType.HIGH;

    @Inject
    private SchoolDetailsRepository schoolDetailsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSchoolDetailsMockMvc;

    private SchoolDetails schoolDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SchoolDetailsResource schoolDetailsResource = new SchoolDetailsResource();
        ReflectionTestUtils.setField(schoolDetailsResource, "schoolDetailsRepository", schoolDetailsRepository);
        this.restSchoolDetailsMockMvc = MockMvcBuilders.standaloneSetup(schoolDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        schoolDetails = new SchoolDetails();
        schoolDetails.setSchoolName(DEFAULT_SCHOOL_NAME);
        schoolDetails.setAddress(DEFAULT_ADDRESS);
        schoolDetails.setPhoneNumber(DEFAULT_PHONE_NUMBER);
        schoolDetails.setAttendanceType(DEFAULT_ATTENDANCE_TYPE);
        schoolDetails.setStartDayOfTheWeek(DEFAULT_START_DAY_OF_THE_WEEK);
        schoolDetails.setDateFormat(DEFAULT_DATE_FORMAT);
        schoolDetails.setFinancialStartDate(DEFAULT_FINANCIAL_START_DATE);
        schoolDetails.setFinancialEndDate(DEFAULT_FINANCIAL_END_DATE);
        schoolDetails.setLogo(DEFAULT_LOGO);
        schoolDetails.setGradingSystem(DEFAULT_GRADING_SYSTEM);
        schoolDetails.setEnableAutoIncreamentOfAdmissionNo(DEFAULT_ENABLE_AUTO_INCREAMENT_OF_ADMISSION_NO);
        schoolDetails.setEnableNewsCommentsModeration(DEFAULT_ENABLE_NEWS_COMMENTS_MODERATION);
        schoolDetails.setEnableSibling(DEFAULT_ENABLE_SIBLING);
        schoolDetails.setEnablePasswordChangeAtFirstLogin(DEFAULT_ENABLE_PASSWORD_CHANGE_AT_FIRST_LOGIN);
        schoolDetails.setEnableRollNumberForStudent(DEFAULT_ENABLE_ROLL_NUMBER_FOR_STUDENT);
        schoolDetails.setInstitutionType(DEFAULT_INSTITUTION_TYPE);
    }

    @Test
    @Transactional
    public void createSchoolDetails() throws Exception {
        int databaseSizeBeforeCreate = schoolDetailsRepository.findAll().size();

        // Create the SchoolDetails

        restSchoolDetailsMockMvc.perform(post("/api/schoolDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schoolDetails)))
                .andExpect(status().isCreated());

        // Validate the SchoolDetails in the database
        List<SchoolDetails> schoolDetailss = schoolDetailsRepository.findAll();
        assertThat(schoolDetailss).hasSize(databaseSizeBeforeCreate + 1);
        SchoolDetails testSchoolDetails = schoolDetailss.get(schoolDetailss.size() - 1);
        assertThat(testSchoolDetails.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testSchoolDetails.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSchoolDetails.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testSchoolDetails.getAttendanceType()).isEqualTo(DEFAULT_ATTENDANCE_TYPE);
        assertThat(testSchoolDetails.getStartDayOfTheWeek()).isEqualTo(DEFAULT_START_DAY_OF_THE_WEEK);
        assertThat(testSchoolDetails.getDateFormat()).isEqualTo(DEFAULT_DATE_FORMAT);
        assertThat(testSchoolDetails.getFinancialStartDate()).isEqualTo(DEFAULT_FINANCIAL_START_DATE);
        assertThat(testSchoolDetails.getFinancialEndDate()).isEqualTo(DEFAULT_FINANCIAL_END_DATE);
        assertThat(testSchoolDetails.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testSchoolDetails.getGradingSystem()).isEqualTo(DEFAULT_GRADING_SYSTEM);
        assertThat(testSchoolDetails.getEnableAutoIncreamentOfAdmissionNo()).isEqualTo(DEFAULT_ENABLE_AUTO_INCREAMENT_OF_ADMISSION_NO);
        assertThat(testSchoolDetails.getEnableNewsCommentsModeration()).isEqualTo(DEFAULT_ENABLE_NEWS_COMMENTS_MODERATION);
        assertThat(testSchoolDetails.getEnableSibling()).isEqualTo(DEFAULT_ENABLE_SIBLING);
        assertThat(testSchoolDetails.getEnablePasswordChangeAtFirstLogin()).isEqualTo(DEFAULT_ENABLE_PASSWORD_CHANGE_AT_FIRST_LOGIN);
        assertThat(testSchoolDetails.getEnableRollNumberForStudent()).isEqualTo(DEFAULT_ENABLE_ROLL_NUMBER_FOR_STUDENT);
        assertThat(testSchoolDetails.getInstitutionType()).isEqualTo(DEFAULT_INSTITUTION_TYPE);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolDetailsRepository.findAll().size();
        // set the field null
        schoolDetails.setPhoneNumber(null);

        // Create the SchoolDetails, which fails.

        restSchoolDetailsMockMvc.perform(post("/api/schoolDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schoolDetails)))
                .andExpect(status().isBadRequest());

        List<SchoolDetails> schoolDetailss = schoolDetailsRepository.findAll();
        assertThat(schoolDetailss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinancialStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolDetailsRepository.findAll().size();
        // set the field null
        schoolDetails.setFinancialStartDate(null);

        // Create the SchoolDetails, which fails.

        restSchoolDetailsMockMvc.perform(post("/api/schoolDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schoolDetails)))
                .andExpect(status().isBadRequest());

        List<SchoolDetails> schoolDetailss = schoolDetailsRepository.findAll();
        assertThat(schoolDetailss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinancialEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = schoolDetailsRepository.findAll().size();
        // set the field null
        schoolDetails.setFinancialEndDate(null);

        // Create the SchoolDetails, which fails.

        restSchoolDetailsMockMvc.perform(post("/api/schoolDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schoolDetails)))
                .andExpect(status().isBadRequest());

        List<SchoolDetails> schoolDetailss = schoolDetailsRepository.findAll();
        assertThat(schoolDetailss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSchoolDetailss() throws Exception {
        // Initialize the database
        schoolDetailsRepository.saveAndFlush(schoolDetails);

        // Get all the schoolDetailss
        restSchoolDetailsMockMvc.perform(get("/api/schoolDetailss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(schoolDetails.getId().intValue())))
                .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
                .andExpect(jsonPath("$.[*].attendanceType").value(hasItem(DEFAULT_ATTENDANCE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].startDayOfTheWeek").value(hasItem(DEFAULT_START_DAY_OF_THE_WEEK.toString())))
                .andExpect(jsonPath("$.[*].dateFormat").value(hasItem(DEFAULT_DATE_FORMAT.toString())))
                .andExpect(jsonPath("$.[*].financialStartDate").value(hasItem(DEFAULT_FINANCIAL_START_DATE.toString())))
                .andExpect(jsonPath("$.[*].financialEndDate").value(hasItem(DEFAULT_FINANCIAL_END_DATE.toString())))
                .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())))
                .andExpect(jsonPath("$.[*].gradingSystem").value(hasItem(DEFAULT_GRADING_SYSTEM.toString())))
                .andExpect(jsonPath("$.[*].enableAutoIncreamentOfAdmissionNo").value(hasItem(DEFAULT_ENABLE_AUTO_INCREAMENT_OF_ADMISSION_NO.booleanValue())))
                .andExpect(jsonPath("$.[*].enableNewsCommentsModeration").value(hasItem(DEFAULT_ENABLE_NEWS_COMMENTS_MODERATION.booleanValue())))
                .andExpect(jsonPath("$.[*].enableSibling").value(hasItem(DEFAULT_ENABLE_SIBLING.booleanValue())))
                .andExpect(jsonPath("$.[*].enablePasswordChangeAtFirstLogin").value(hasItem(DEFAULT_ENABLE_PASSWORD_CHANGE_AT_FIRST_LOGIN.booleanValue())))
                .andExpect(jsonPath("$.[*].enableRollNumberForStudent").value(hasItem(DEFAULT_ENABLE_ROLL_NUMBER_FOR_STUDENT.booleanValue())))
                .andExpect(jsonPath("$.[*].institutionType").value(hasItem(DEFAULT_INSTITUTION_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getSchoolDetails() throws Exception {
        // Initialize the database
        schoolDetailsRepository.saveAndFlush(schoolDetails);

        // Get the schoolDetails
        restSchoolDetailsMockMvc.perform(get("/api/schoolDetailss/{id}", schoolDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(schoolDetails.getId().intValue()))
            .andExpect(jsonPath("$.schoolName").value(DEFAULT_SCHOOL_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.attendanceType").value(DEFAULT_ATTENDANCE_TYPE.toString()))
            .andExpect(jsonPath("$.startDayOfTheWeek").value(DEFAULT_START_DAY_OF_THE_WEEK.toString()))
            .andExpect(jsonPath("$.dateFormat").value(DEFAULT_DATE_FORMAT.toString()))
            .andExpect(jsonPath("$.financialStartDate").value(DEFAULT_FINANCIAL_START_DATE.toString()))
            .andExpect(jsonPath("$.financialEndDate").value(DEFAULT_FINANCIAL_END_DATE.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()))
            .andExpect(jsonPath("$.gradingSystem").value(DEFAULT_GRADING_SYSTEM.toString()))
            .andExpect(jsonPath("$.enableAutoIncreamentOfAdmissionNo").value(DEFAULT_ENABLE_AUTO_INCREAMENT_OF_ADMISSION_NO.booleanValue()))
            .andExpect(jsonPath("$.enableNewsCommentsModeration").value(DEFAULT_ENABLE_NEWS_COMMENTS_MODERATION.booleanValue()))
            .andExpect(jsonPath("$.enableSibling").value(DEFAULT_ENABLE_SIBLING.booleanValue()))
            .andExpect(jsonPath("$.enablePasswordChangeAtFirstLogin").value(DEFAULT_ENABLE_PASSWORD_CHANGE_AT_FIRST_LOGIN.booleanValue()))
            .andExpect(jsonPath("$.enableRollNumberForStudent").value(DEFAULT_ENABLE_ROLL_NUMBER_FOR_STUDENT.booleanValue()))
            .andExpect(jsonPath("$.institutionType").value(DEFAULT_INSTITUTION_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchoolDetails() throws Exception {
        // Get the schoolDetails
        restSchoolDetailsMockMvc.perform(get("/api/schoolDetailss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchoolDetails() throws Exception {
        // Initialize the database
        schoolDetailsRepository.saveAndFlush(schoolDetails);

		int databaseSizeBeforeUpdate = schoolDetailsRepository.findAll().size();

        // Update the schoolDetails
        schoolDetails.setSchoolName(UPDATED_SCHOOL_NAME);
        schoolDetails.setAddress(UPDATED_ADDRESS);
        schoolDetails.setPhoneNumber(UPDATED_PHONE_NUMBER);
        schoolDetails.setAttendanceType(UPDATED_ATTENDANCE_TYPE);
        schoolDetails.setStartDayOfTheWeek(UPDATED_START_DAY_OF_THE_WEEK);
        schoolDetails.setDateFormat(UPDATED_DATE_FORMAT);
        schoolDetails.setFinancialStartDate(UPDATED_FINANCIAL_START_DATE);
        schoolDetails.setFinancialEndDate(UPDATED_FINANCIAL_END_DATE);
        schoolDetails.setLogo(UPDATED_LOGO);
        schoolDetails.setGradingSystem(UPDATED_GRADING_SYSTEM);
        schoolDetails.setEnableAutoIncreamentOfAdmissionNo(UPDATED_ENABLE_AUTO_INCREAMENT_OF_ADMISSION_NO);
        schoolDetails.setEnableNewsCommentsModeration(UPDATED_ENABLE_NEWS_COMMENTS_MODERATION);
        schoolDetails.setEnableSibling(UPDATED_ENABLE_SIBLING);
        schoolDetails.setEnablePasswordChangeAtFirstLogin(UPDATED_ENABLE_PASSWORD_CHANGE_AT_FIRST_LOGIN);
        schoolDetails.setEnableRollNumberForStudent(UPDATED_ENABLE_ROLL_NUMBER_FOR_STUDENT);
        schoolDetails.setInstitutionType(UPDATED_INSTITUTION_TYPE);

        restSchoolDetailsMockMvc.perform(put("/api/schoolDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(schoolDetails)))
                .andExpect(status().isOk());

        // Validate the SchoolDetails in the database
        List<SchoolDetails> schoolDetailss = schoolDetailsRepository.findAll();
        assertThat(schoolDetailss).hasSize(databaseSizeBeforeUpdate);
        SchoolDetails testSchoolDetails = schoolDetailss.get(schoolDetailss.size() - 1);
        assertThat(testSchoolDetails.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testSchoolDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSchoolDetails.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testSchoolDetails.getAttendanceType()).isEqualTo(UPDATED_ATTENDANCE_TYPE);
        assertThat(testSchoolDetails.getStartDayOfTheWeek()).isEqualTo(UPDATED_START_DAY_OF_THE_WEEK);
        assertThat(testSchoolDetails.getDateFormat()).isEqualTo(UPDATED_DATE_FORMAT);
        assertThat(testSchoolDetails.getFinancialStartDate()).isEqualTo(UPDATED_FINANCIAL_START_DATE);
        assertThat(testSchoolDetails.getFinancialEndDate()).isEqualTo(UPDATED_FINANCIAL_END_DATE);
        assertThat(testSchoolDetails.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testSchoolDetails.getGradingSystem()).isEqualTo(UPDATED_GRADING_SYSTEM);
        assertThat(testSchoolDetails.getEnableAutoIncreamentOfAdmissionNo()).isEqualTo(UPDATED_ENABLE_AUTO_INCREAMENT_OF_ADMISSION_NO);
        assertThat(testSchoolDetails.getEnableNewsCommentsModeration()).isEqualTo(UPDATED_ENABLE_NEWS_COMMENTS_MODERATION);
        assertThat(testSchoolDetails.getEnableSibling()).isEqualTo(UPDATED_ENABLE_SIBLING);
        assertThat(testSchoolDetails.getEnablePasswordChangeAtFirstLogin()).isEqualTo(UPDATED_ENABLE_PASSWORD_CHANGE_AT_FIRST_LOGIN);
        assertThat(testSchoolDetails.getEnableRollNumberForStudent()).isEqualTo(UPDATED_ENABLE_ROLL_NUMBER_FOR_STUDENT);
        assertThat(testSchoolDetails.getInstitutionType()).isEqualTo(UPDATED_INSTITUTION_TYPE);
    }

    @Test
    @Transactional
    public void deleteSchoolDetails() throws Exception {
        // Initialize the database
        schoolDetailsRepository.saveAndFlush(schoolDetails);

		int databaseSizeBeforeDelete = schoolDetailsRepository.findAll().size();

        // Get the schoolDetails
        restSchoolDetailsMockMvc.perform(delete("/api/schoolDetailss/{id}", schoolDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SchoolDetails> schoolDetailss = schoolDetailsRepository.findAll();
        assertThat(schoolDetailss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
