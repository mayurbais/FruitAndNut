package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.EndDate;
import com.mycompany.myapp.repository.EndDateRepository;

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

import com.mycompany.myapp.domain.enumeration.Days;

/**
 * Test class for the EndDateResource REST controller.
 *
 * @see EndDateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EndDateResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));



    private static final Days DEFAULT_DAY = Days.MONDAY;
    private static final Days UPDATED_DAY = Days.TUESDAY;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);

    @Inject
    private EndDateRepository endDateRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEndDateMockMvc;

    private EndDate endDate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EndDateResource endDateResource = new EndDateResource();
        ReflectionTestUtils.setField(endDateResource, "endDateRepository", endDateRepository);
        this.restEndDateMockMvc = MockMvcBuilders.standaloneSetup(endDateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        endDate = new EndDate();
        endDate.setDay(DEFAULT_DAY);
        endDate.setStartDate(DEFAULT_START_DATE);
        endDate.setEndDate(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createEndDate() throws Exception {
        int databaseSizeBeforeCreate = endDateRepository.findAll().size();

        // Create the EndDate

        restEndDateMockMvc.perform(post("/api/endDates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(endDate)))
                .andExpect(status().isCreated());

        // Validate the EndDate in the database
        List<EndDate> endDates = endDateRepository.findAll();
        assertThat(endDates).hasSize(databaseSizeBeforeCreate + 1);
        EndDate testEndDate = endDates.get(endDates.size() - 1);
        assertThat(testEndDate.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testEndDate.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEndDate.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void getAllEndDates() throws Exception {
        // Initialize the database
        endDateRepository.saveAndFlush(endDate);

        // Get all the endDates
        restEndDateMockMvc.perform(get("/api/endDates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(endDate.getId().intValue())))
                .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)));
    }

    @Test
    @Transactional
    public void getEndDate() throws Exception {
        // Initialize the database
        endDateRepository.saveAndFlush(endDate);

        // Get the endDate
        restEndDateMockMvc.perform(get("/api/endDates/{id}", endDate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(endDate.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingEndDate() throws Exception {
        // Get the endDate
        restEndDateMockMvc.perform(get("/api/endDates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEndDate() throws Exception {
        // Initialize the database
        endDateRepository.saveAndFlush(endDate);

		int databaseSizeBeforeUpdate = endDateRepository.findAll().size();

        // Update the endDate
        endDate.setDay(UPDATED_DAY);
        endDate.setStartDate(UPDATED_START_DATE);
        endDate.setEndDate(UPDATED_END_DATE);

        restEndDateMockMvc.perform(put("/api/endDates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(endDate)))
                .andExpect(status().isOk());

        // Validate the EndDate in the database
        List<EndDate> endDates = endDateRepository.findAll();
        assertThat(endDates).hasSize(databaseSizeBeforeUpdate);
        EndDate testEndDate = endDates.get(endDates.size() - 1);
        assertThat(testEndDate.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testEndDate.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEndDate.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void deleteEndDate() throws Exception {
        // Initialize the database
        endDateRepository.saveAndFlush(endDate);

		int databaseSizeBeforeDelete = endDateRepository.findAll().size();

        // Get the endDate
        restEndDateMockMvc.perform(delete("/api/endDates/{id}", endDate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<EndDate> endDates = endDateRepository.findAll();
        assertThat(endDates).hasSize(databaseSizeBeforeDelete - 1);
    }
}
