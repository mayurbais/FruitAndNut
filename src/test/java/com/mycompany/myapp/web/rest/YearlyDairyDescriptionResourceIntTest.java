package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.YearlyDairyDescription;
import com.mycompany.myapp.repository.YearlyDairyDescriptionRepository;

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
 * Test class for the YearlyDairyDescriptionResource REST controller.
 *
 * @see YearlyDairyDescriptionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YearlyDairyDescriptionResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_YEAR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_YEAR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_YEAR_STR = dateTimeFormatter.format(DEFAULT_YEAR);
    private static final String DEFAULT_THEME = "AAAAA";
    private static final String UPDATED_THEME = "BBBBB";
    private static final String DEFAULT_SUMMER_DRESS_CODE = "AAAAA";
    private static final String UPDATED_SUMMER_DRESS_CODE = "BBBBB";
    private static final String DEFAULT_WINTER_DRESS_CODE = "AAAAA";
    private static final String UPDATED_WINTER_DRESS_CODE = "BBBBB";

    private static final Boolean DEFAULT_IS_ENABLED = false;
    private static final Boolean UPDATED_IS_ENABLED = true;

    @Inject
    private YearlyDairyDescriptionRepository yearlyDairyDescriptionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYearlyDairyDescriptionMockMvc;

    private YearlyDairyDescription yearlyDairyDescription;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YearlyDairyDescriptionResource yearlyDairyDescriptionResource = new YearlyDairyDescriptionResource();
        ReflectionTestUtils.setField(yearlyDairyDescriptionResource, "yearlyDairyDescriptionRepository", yearlyDairyDescriptionRepository);
        this.restYearlyDairyDescriptionMockMvc = MockMvcBuilders.standaloneSetup(yearlyDairyDescriptionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yearlyDairyDescription = new YearlyDairyDescription();
        yearlyDairyDescription.setYear(DEFAULT_YEAR);
        yearlyDairyDescription.setTheme(DEFAULT_THEME);
        yearlyDairyDescription.setSummerDressCode(DEFAULT_SUMMER_DRESS_CODE);
        yearlyDairyDescription.setWinterDressCode(DEFAULT_WINTER_DRESS_CODE);
        yearlyDairyDescription.setIsEnabled(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void createYearlyDairyDescription() throws Exception {
        int databaseSizeBeforeCreate = yearlyDairyDescriptionRepository.findAll().size();

        // Create the YearlyDairyDescription

        restYearlyDairyDescriptionMockMvc.perform(post("/api/yearlyDairyDescriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yearlyDairyDescription)))
                .andExpect(status().isCreated());

        // Validate the YearlyDairyDescription in the database
        List<YearlyDairyDescription> yearlyDairyDescriptions = yearlyDairyDescriptionRepository.findAll();
        assertThat(yearlyDairyDescriptions).hasSize(databaseSizeBeforeCreate + 1);
        YearlyDairyDescription testYearlyDairyDescription = yearlyDairyDescriptions.get(yearlyDairyDescriptions.size() - 1);
        assertThat(testYearlyDairyDescription.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testYearlyDairyDescription.getTheme()).isEqualTo(DEFAULT_THEME);
        assertThat(testYearlyDairyDescription.getSummerDressCode()).isEqualTo(DEFAULT_SUMMER_DRESS_CODE);
        assertThat(testYearlyDairyDescription.getWinterDressCode()).isEqualTo(DEFAULT_WINTER_DRESS_CODE);
        assertThat(testYearlyDairyDescription.getIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void getAllYearlyDairyDescriptions() throws Exception {
        // Initialize the database
        yearlyDairyDescriptionRepository.saveAndFlush(yearlyDairyDescription);

        // Get all the yearlyDairyDescriptions
        restYearlyDairyDescriptionMockMvc.perform(get("/api/yearlyDairyDescriptions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yearlyDairyDescription.getId().intValue())))
                .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR_STR)))
                .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME.toString())))
                .andExpect(jsonPath("$.[*].summerDressCode").value(hasItem(DEFAULT_SUMMER_DRESS_CODE.toString())))
                .andExpect(jsonPath("$.[*].winterDressCode").value(hasItem(DEFAULT_WINTER_DRESS_CODE.toString())))
                .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getYearlyDairyDescription() throws Exception {
        // Initialize the database
        yearlyDairyDescriptionRepository.saveAndFlush(yearlyDairyDescription);

        // Get the yearlyDairyDescription
        restYearlyDairyDescriptionMockMvc.perform(get("/api/yearlyDairyDescriptions/{id}", yearlyDairyDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yearlyDairyDescription.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR_STR))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME.toString()))
            .andExpect(jsonPath("$.summerDressCode").value(DEFAULT_SUMMER_DRESS_CODE.toString()))
            .andExpect(jsonPath("$.winterDressCode").value(DEFAULT_WINTER_DRESS_CODE.toString()))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingYearlyDairyDescription() throws Exception {
        // Get the yearlyDairyDescription
        restYearlyDairyDescriptionMockMvc.perform(get("/api/yearlyDairyDescriptions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYearlyDairyDescription() throws Exception {
        // Initialize the database
        yearlyDairyDescriptionRepository.saveAndFlush(yearlyDairyDescription);

		int databaseSizeBeforeUpdate = yearlyDairyDescriptionRepository.findAll().size();

        // Update the yearlyDairyDescription
        yearlyDairyDescription.setYear(UPDATED_YEAR);
        yearlyDairyDescription.setTheme(UPDATED_THEME);
        yearlyDairyDescription.setSummerDressCode(UPDATED_SUMMER_DRESS_CODE);
        yearlyDairyDescription.setWinterDressCode(UPDATED_WINTER_DRESS_CODE);
        yearlyDairyDescription.setIsEnabled(UPDATED_IS_ENABLED);

        restYearlyDairyDescriptionMockMvc.perform(put("/api/yearlyDairyDescriptions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yearlyDairyDescription)))
                .andExpect(status().isOk());

        // Validate the YearlyDairyDescription in the database
        List<YearlyDairyDescription> yearlyDairyDescriptions = yearlyDairyDescriptionRepository.findAll();
        assertThat(yearlyDairyDescriptions).hasSize(databaseSizeBeforeUpdate);
        YearlyDairyDescription testYearlyDairyDescription = yearlyDairyDescriptions.get(yearlyDairyDescriptions.size() - 1);
        assertThat(testYearlyDairyDescription.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testYearlyDairyDescription.getTheme()).isEqualTo(UPDATED_THEME);
        assertThat(testYearlyDairyDescription.getSummerDressCode()).isEqualTo(UPDATED_SUMMER_DRESS_CODE);
        assertThat(testYearlyDairyDescription.getWinterDressCode()).isEqualTo(UPDATED_WINTER_DRESS_CODE);
        assertThat(testYearlyDairyDescription.getIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    public void deleteYearlyDairyDescription() throws Exception {
        // Initialize the database
        yearlyDairyDescriptionRepository.saveAndFlush(yearlyDairyDescription);

		int databaseSizeBeforeDelete = yearlyDairyDescriptionRepository.findAll().size();

        // Get the yearlyDairyDescription
        restYearlyDairyDescriptionMockMvc.perform(delete("/api/yearlyDairyDescriptions/{id}", yearlyDairyDescription.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YearlyDairyDescription> yearlyDairyDescriptions = yearlyDairyDescriptionRepository.findAll();
        assertThat(yearlyDairyDescriptions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
