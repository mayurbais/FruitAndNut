package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.TimeSlot;
import com.mycompany.myapp.repository.TimeSlotRepository;

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
 * Test class for the TimeSlotResource REST controller.
 *
 * @see TimeSlotResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TimeSlotResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_START_TIME = "AAAAA";
    private static final String UPDATED_START_TIME = "BBBBB";
    private static final String DEFAULT_END_TIME = "AAAAA";
    private static final String UPDATED_END_TIME = "BBBBB";

    @Inject
    private TimeSlotRepository timeSlotRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTimeSlotMockMvc;

    private TimeSlot timeSlot;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeSlotResource timeSlotResource = new TimeSlotResource();
        ReflectionTestUtils.setField(timeSlotResource, "timeSlotRepository", timeSlotRepository);
        this.restTimeSlotMockMvc = MockMvcBuilders.standaloneSetup(timeSlotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        timeSlot = new TimeSlot();
        timeSlot.setName(DEFAULT_NAME);
        timeSlot.setStartTime(DEFAULT_START_TIME);
        timeSlot.setEndTime(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createTimeSlot() throws Exception {
        int databaseSizeBeforeCreate = timeSlotRepository.findAll().size();

        // Create the TimeSlot

        restTimeSlotMockMvc.perform(post("/api/timeSlots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeSlot)))
                .andExpect(status().isCreated());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        assertThat(timeSlots).hasSize(databaseSizeBeforeCreate + 1);
        TimeSlot testTimeSlot = timeSlots.get(timeSlots.size() - 1);
        assertThat(testTimeSlot.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTimeSlot.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTimeSlot.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTimeSlots() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

        // Get all the timeSlots
        restTimeSlotMockMvc.perform(get("/api/timeSlots?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(timeSlot.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
                .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())));
    }

    @Test
    @Transactional
    public void getTimeSlot() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

        // Get the timeSlot
        restTimeSlotMockMvc.perform(get("/api/timeSlots/{id}", timeSlot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(timeSlot.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeSlot() throws Exception {
        // Get the timeSlot
        restTimeSlotMockMvc.perform(get("/api/timeSlots/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeSlot() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

		int databaseSizeBeforeUpdate = timeSlotRepository.findAll().size();

        // Update the timeSlot
        timeSlot.setName(UPDATED_NAME);
        timeSlot.setStartTime(UPDATED_START_TIME);
        timeSlot.setEndTime(UPDATED_END_TIME);

        restTimeSlotMockMvc.perform(put("/api/timeSlots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(timeSlot)))
                .andExpect(status().isOk());

        // Validate the TimeSlot in the database
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        assertThat(timeSlots).hasSize(databaseSizeBeforeUpdate);
        TimeSlot testTimeSlot = timeSlots.get(timeSlots.size() - 1);
        assertThat(testTimeSlot.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTimeSlot.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTimeSlot.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void deleteTimeSlot() throws Exception {
        // Initialize the database
        timeSlotRepository.saveAndFlush(timeSlot);

		int databaseSizeBeforeDelete = timeSlotRepository.findAll().size();

        // Get the timeSlot
        restTimeSlotMockMvc.perform(delete("/api/timeSlots/{id}", timeSlot.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeSlot> timeSlots = timeSlotRepository.findAll();
        assertThat(timeSlots).hasSize(databaseSizeBeforeDelete - 1);
    }
}
