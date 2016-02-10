package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.DairyEntry;
import com.mycompany.myapp.repository.DairyEntryRepository;

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

import com.mycompany.myapp.domain.enumeration.DairyEntryType;

/**
 * Test class for the DairyEntryResource REST controller.
 *
 * @see DairyEntryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DairyEntryResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.format(DEFAULT_DATE);


    private static final DairyEntryType DEFAULT_ENTRY_TYPE = DairyEntryType.HOMEWORK;
    private static final DairyEntryType UPDATED_ENTRY_TYPE = DairyEntryType.PROJECT;
    private static final String DEFAULT_DAIRY_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DAIRY_DESCRIPTION = "BBBBB";

    private static final Boolean DEFAULT_IS_FOR_ALL = false;
    private static final Boolean UPDATED_IS_FOR_ALL = true;

    @Inject
    private DairyEntryRepository dairyEntryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDairyEntryMockMvc;

    private DairyEntry dairyEntry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DairyEntryResource dairyEntryResource = new DairyEntryResource();
        ReflectionTestUtils.setField(dairyEntryResource, "dairyEntryRepository", dairyEntryRepository);
        this.restDairyEntryMockMvc = MockMvcBuilders.standaloneSetup(dairyEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dairyEntry = new DairyEntry();
        dairyEntry.setDate(DEFAULT_DATE);
        dairyEntry.setEntryType(DEFAULT_ENTRY_TYPE);
        dairyEntry.setDairyDescription(DEFAULT_DAIRY_DESCRIPTION);
        dairyEntry.setIsForAll(DEFAULT_IS_FOR_ALL);
    }

    @Test
    @Transactional
    public void createDairyEntry() throws Exception {
        int databaseSizeBeforeCreate = dairyEntryRepository.findAll().size();

        // Create the DairyEntry

        restDairyEntryMockMvc.perform(post("/api/dairyEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dairyEntry)))
                .andExpect(status().isCreated());

        // Validate the DairyEntry in the database
        List<DairyEntry> dairyEntrys = dairyEntryRepository.findAll();
        assertThat(dairyEntrys).hasSize(databaseSizeBeforeCreate + 1);
        DairyEntry testDairyEntry = dairyEntrys.get(dairyEntrys.size() - 1);
        assertThat(testDairyEntry.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testDairyEntry.getEntryType()).isEqualTo(DEFAULT_ENTRY_TYPE);
        assertThat(testDairyEntry.getDairyDescription()).isEqualTo(DEFAULT_DAIRY_DESCRIPTION);
        assertThat(testDairyEntry.getIsForAll()).isEqualTo(DEFAULT_IS_FOR_ALL);
    }

    @Test
    @Transactional
    public void getAllDairyEntrys() throws Exception {
        // Initialize the database
        dairyEntryRepository.saveAndFlush(dairyEntry);

        // Get all the dairyEntrys
        restDairyEntryMockMvc.perform(get("/api/dairyEntrys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dairyEntry.getId().intValue())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE_STR)))
                .andExpect(jsonPath("$.[*].entryType").value(hasItem(DEFAULT_ENTRY_TYPE.toString())))
                .andExpect(jsonPath("$.[*].dairyDescription").value(hasItem(DEFAULT_DAIRY_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].isForAll").value(hasItem(DEFAULT_IS_FOR_ALL.booleanValue())));
    }

    @Test
    @Transactional
    public void getDairyEntry() throws Exception {
        // Initialize the database
        dairyEntryRepository.saveAndFlush(dairyEntry);

        // Get the dairyEntry
        restDairyEntryMockMvc.perform(get("/api/dairyEntrys/{id}", dairyEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dairyEntry.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.entryType").value(DEFAULT_ENTRY_TYPE.toString()))
            .andExpect(jsonPath("$.dairyDescription").value(DEFAULT_DAIRY_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.isForAll").value(DEFAULT_IS_FOR_ALL.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDairyEntry() throws Exception {
        // Get the dairyEntry
        restDairyEntryMockMvc.perform(get("/api/dairyEntrys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDairyEntry() throws Exception {
        // Initialize the database
        dairyEntryRepository.saveAndFlush(dairyEntry);

		int databaseSizeBeforeUpdate = dairyEntryRepository.findAll().size();

        // Update the dairyEntry
        dairyEntry.setDate(UPDATED_DATE);
        dairyEntry.setEntryType(UPDATED_ENTRY_TYPE);
        dairyEntry.setDairyDescription(UPDATED_DAIRY_DESCRIPTION);
        dairyEntry.setIsForAll(UPDATED_IS_FOR_ALL);

        restDairyEntryMockMvc.perform(put("/api/dairyEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dairyEntry)))
                .andExpect(status().isOk());

        // Validate the DairyEntry in the database
        List<DairyEntry> dairyEntrys = dairyEntryRepository.findAll();
        assertThat(dairyEntrys).hasSize(databaseSizeBeforeUpdate);
        DairyEntry testDairyEntry = dairyEntrys.get(dairyEntrys.size() - 1);
        assertThat(testDairyEntry.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testDairyEntry.getEntryType()).isEqualTo(UPDATED_ENTRY_TYPE);
        assertThat(testDairyEntry.getDairyDescription()).isEqualTo(UPDATED_DAIRY_DESCRIPTION);
        assertThat(testDairyEntry.getIsForAll()).isEqualTo(UPDATED_IS_FOR_ALL);
    }

    @Test
    @Transactional
    public void deleteDairyEntry() throws Exception {
        // Initialize the database
        dairyEntryRepository.saveAndFlush(dairyEntry);

		int databaseSizeBeforeDelete = dairyEntryRepository.findAll().size();

        // Get the dairyEntry
        restDairyEntryMockMvc.perform(delete("/api/dairyEntrys/{id}", dairyEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<DairyEntry> dairyEntrys = dairyEntryRepository.findAll();
        assertThat(dairyEntrys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
