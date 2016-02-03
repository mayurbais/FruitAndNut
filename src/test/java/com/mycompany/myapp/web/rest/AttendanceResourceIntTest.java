package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Attendance;
import com.mycompany.myapp.repository.AttendanceRepository;

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

import com.mycompany.myapp.domain.enumeration.IrisUserRole;

/**
 * Test class for the AttendanceResource REST controller.
 *
 * @see AttendanceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AttendanceResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));



    private static final IrisUserRole DEFAULT_ATTENDANCE_FOR = IrisUserRole.STUDENT;
    private static final IrisUserRole UPDATED_ATTENDANCE_FOR = IrisUserRole.STAFF;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);

    private static final Boolean DEFAULT_IS_PRESENT = false;
    private static final Boolean UPDATED_IS_PRESENT = true;
    private static final String DEFAULT_REASON_FOR_ABSENT = "AAAAAAAAAA";
    private static final String UPDATED_REASON_FOR_ABSENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;
    private static final String DEFAULT_APPROVED_BY = "AAAAA";
    private static final String UPDATED_APPROVED_BY = "BBBBB";
    private static final String DEFAULT_ATTRIBUTE = "AAAAA";
    private static final String UPDATED_ATTRIBUTE = "BBBBB";

    @Inject
    private AttendanceRepository attendanceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAttendanceMockMvc;

    private Attendance attendance;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AttendanceResource attendanceResource = new AttendanceResource();
        ReflectionTestUtils.setField(attendanceResource, "attendanceRepository", attendanceRepository);
        this.restAttendanceMockMvc = MockMvcBuilders.standaloneSetup(attendanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        attendance = new Attendance();
        attendance.setAttendanceFor(DEFAULT_ATTENDANCE_FOR);
        attendance.setDate(DEFAULT_DATE);
        attendance.setIsPresent(DEFAULT_IS_PRESENT);
        attendance.setReasonForAbsent(DEFAULT_REASON_FOR_ABSENT);
        attendance.setIsApproved(DEFAULT_IS_APPROVED);
        attendance.setApprovedBy(DEFAULT_APPROVED_BY);
        attendance.setAttribute(DEFAULT_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void createAttendance() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();

        // Create the Attendance

        restAttendanceMockMvc.perform(post("/api/attendances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attendance)))
                .andExpect(status().isCreated());

        // Validate the Attendance in the database
        List<Attendance> attendances = attendanceRepository.findAll();
        assertThat(attendances).hasSize(databaseSizeBeforeCreate + 1);
        Attendance testAttendance = attendances.get(attendances.size() - 1);
        assertThat(testAttendance.getAttendanceFor()).isEqualTo(DEFAULT_ATTENDANCE_FOR);
        assertThat(testAttendance.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAttendance.getIsPresent()).isEqualTo(DEFAULT_IS_PRESENT);
        assertThat(testAttendance.getReasonForAbsent()).isEqualTo(DEFAULT_REASON_FOR_ABSENT);
        assertThat(testAttendance.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testAttendance.getApprovedBy()).isEqualTo(DEFAULT_APPROVED_BY);
        assertThat(testAttendance.getAttribute()).isEqualTo(DEFAULT_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void getAllAttendances() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendances
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
                .andExpect(jsonPath("$.[*].attendanceFor").value(hasItem(DEFAULT_ATTENDANCE_FOR.toString())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].isPresent").value(hasItem(DEFAULT_IS_PRESENT.booleanValue())))
                .andExpect(jsonPath("$.[*].reasonForAbsent").value(hasItem(DEFAULT_REASON_FOR_ABSENT.toString())))
                .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
                .andExpect(jsonPath("$.[*].approvedBy").value(hasItem(DEFAULT_APPROVED_BY.toString())))
                .andExpect(jsonPath("$.[*].attribute").value(hasItem(DEFAULT_ATTRIBUTE.toString())));
    }

    @Test
    @Transactional
    public void getAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get the attendance
        restAttendanceMockMvc.perform(get("/api/attendances/{id}", attendance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(attendance.getId().intValue()))
            .andExpect(jsonPath("$.attendanceFor").value(DEFAULT_ATTENDANCE_FOR.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.isPresent").value(DEFAULT_IS_PRESENT.booleanValue()))
            .andExpect(jsonPath("$.reasonForAbsent").value(DEFAULT_REASON_FOR_ABSENT.toString()))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.approvedBy").value(DEFAULT_APPROVED_BY.toString()))
            .andExpect(jsonPath("$.attribute").value(DEFAULT_ATTRIBUTE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAttendance() throws Exception {
        // Get the attendance
        restAttendanceMockMvc.perform(get("/api/attendances/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

		int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance
        attendance.setAttendanceFor(UPDATED_ATTENDANCE_FOR);
        attendance.setDate(UPDATED_DATE);
        attendance.setIsPresent(UPDATED_IS_PRESENT);
        attendance.setReasonForAbsent(UPDATED_REASON_FOR_ABSENT);
        attendance.setIsApproved(UPDATED_IS_APPROVED);
        attendance.setApprovedBy(UPDATED_APPROVED_BY);
        attendance.setAttribute(UPDATED_ATTRIBUTE);

        restAttendanceMockMvc.perform(put("/api/attendances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(attendance)))
                .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendances = attendanceRepository.findAll();
        assertThat(attendances).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendances.get(attendances.size() - 1);
        assertThat(testAttendance.getAttendanceFor()).isEqualTo(UPDATED_ATTENDANCE_FOR);
        assertThat(testAttendance.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAttendance.getIsPresent()).isEqualTo(UPDATED_IS_PRESENT);
        assertThat(testAttendance.getReasonForAbsent()).isEqualTo(UPDATED_REASON_FOR_ABSENT);
        assertThat(testAttendance.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testAttendance.getApprovedBy()).isEqualTo(UPDATED_APPROVED_BY);
        assertThat(testAttendance.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void deleteAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

		int databaseSizeBeforeDelete = attendanceRepository.findAll().size();

        // Get the attendance
        restAttendanceMockMvc.perform(delete("/api/attendances/{id}", attendance.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Attendance> attendances = attendanceRepository.findAll();
        assertThat(attendances).hasSize(databaseSizeBeforeDelete - 1);
    }
}
