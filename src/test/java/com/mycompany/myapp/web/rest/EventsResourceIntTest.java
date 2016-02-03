package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Events;
import com.mycompany.myapp.repository.EventsRepository;

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
 * Test class for the EventsResource REST controller.
 *
 * @see EventsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class EventsResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_DATE_STR = dateTimeFormatter.format(DEFAULT_START_DATE);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_END_DATE_STR = dateTimeFormatter.format(DEFAULT_END_DATE);

    private static final Boolean DEFAULT_IS_HOLIDAY = false;
    private static final Boolean UPDATED_IS_HOLIDAY = true;

    private static final Boolean DEFAULT_IS_COMMON_TO_ALL = false;
    private static final Boolean UPDATED_IS_COMMON_TO_ALL = true;

    @Inject
    private EventsRepository eventsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restEventsMockMvc;

    private Events events;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EventsResource eventsResource = new EventsResource();
        ReflectionTestUtils.setField(eventsResource, "eventsRepository", eventsRepository);
        this.restEventsMockMvc = MockMvcBuilders.standaloneSetup(eventsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        events = new Events();
        events.setTitle(DEFAULT_TITLE);
        events.setDescription(DEFAULT_DESCRIPTION);
        events.setStartDate(DEFAULT_START_DATE);
        events.setEndDate(DEFAULT_END_DATE);
        events.setIsHoliday(DEFAULT_IS_HOLIDAY);
        events.setIsCommonToAll(DEFAULT_IS_COMMON_TO_ALL);
    }

    @Test
    @Transactional
    public void createEvents() throws Exception {
        int databaseSizeBeforeCreate = eventsRepository.findAll().size();

        // Create the Events

        restEventsMockMvc.perform(post("/api/eventss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(events)))
                .andExpect(status().isCreated());

        // Validate the Events in the database
        List<Events> eventss = eventsRepository.findAll();
        assertThat(eventss).hasSize(databaseSizeBeforeCreate + 1);
        Events testEvents = eventss.get(eventss.size() - 1);
        assertThat(testEvents.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEvents.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEvents.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEvents.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEvents.getIsHoliday()).isEqualTo(DEFAULT_IS_HOLIDAY);
        assertThat(testEvents.getIsCommonToAll()).isEqualTo(DEFAULT_IS_COMMON_TO_ALL);
    }

    @Test
    @Transactional
    public void getAllEventss() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get all the eventss
        restEventsMockMvc.perform(get("/api/eventss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(events.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE_STR)))
                .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE_STR)))
                .andExpect(jsonPath("$.[*].isHoliday").value(hasItem(DEFAULT_IS_HOLIDAY.booleanValue())))
                .andExpect(jsonPath("$.[*].isCommonToAll").value(hasItem(DEFAULT_IS_COMMON_TO_ALL.booleanValue())));
    }

    @Test
    @Transactional
    public void getEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

        // Get the events
        restEventsMockMvc.perform(get("/api/eventss/{id}", events.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(events.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE_STR))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE_STR))
            .andExpect(jsonPath("$.isHoliday").value(DEFAULT_IS_HOLIDAY.booleanValue()))
            .andExpect(jsonPath("$.isCommonToAll").value(DEFAULT_IS_COMMON_TO_ALL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEvents() throws Exception {
        // Get the events
        restEventsMockMvc.perform(get("/api/eventss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

		int databaseSizeBeforeUpdate = eventsRepository.findAll().size();

        // Update the events
        events.setTitle(UPDATED_TITLE);
        events.setDescription(UPDATED_DESCRIPTION);
        events.setStartDate(UPDATED_START_DATE);
        events.setEndDate(UPDATED_END_DATE);
        events.setIsHoliday(UPDATED_IS_HOLIDAY);
        events.setIsCommonToAll(UPDATED_IS_COMMON_TO_ALL);

        restEventsMockMvc.perform(put("/api/eventss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(events)))
                .andExpect(status().isOk());

        // Validate the Events in the database
        List<Events> eventss = eventsRepository.findAll();
        assertThat(eventss).hasSize(databaseSizeBeforeUpdate);
        Events testEvents = eventss.get(eventss.size() - 1);
        assertThat(testEvents.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEvents.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEvents.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEvents.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEvents.getIsHoliday()).isEqualTo(UPDATED_IS_HOLIDAY);
        assertThat(testEvents.getIsCommonToAll()).isEqualTo(UPDATED_IS_COMMON_TO_ALL);
    }

    @Test
    @Transactional
    public void deleteEvents() throws Exception {
        // Initialize the database
        eventsRepository.saveAndFlush(events);

		int databaseSizeBeforeDelete = eventsRepository.findAll().size();

        // Get the events
        restEventsMockMvc.perform(delete("/api/eventss/{id}", events.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Events> eventss = eventsRepository.findAll();
        assertThat(eventss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
