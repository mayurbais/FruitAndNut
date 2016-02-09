package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.BusDetails;
import com.mycompany.myapp.repository.BusDetailsRepository;

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
 * Test class for the BusDetailsResource REST controller.
 *
 * @see BusDetailsResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BusDetailsResourceIntTest {

    private static final String DEFAULT_BUS_NO = "AAAAA";
    private static final String UPDATED_BUS_NO = "BBBBB";
    private static final String DEFAULT_ROUTE = "AAAAA";
    private static final String UPDATED_ROUTE = "BBBBB";
    private static final String DEFAULT_TIMING = "AAAAA";
    private static final String UPDATED_TIMING = "BBBBB";
    private static final String DEFAULT_DRIVER_NAME = "AAAAA";
    private static final String UPDATED_DRIVER_NAME = "BBBBB";
    private static final String DEFAULT_DRIVER_CONTACT_NO = "AAAAA";
    private static final String UPDATED_DRIVER_CONTACT_NO = "BBBBB";

    @Inject
    private BusDetailsRepository busDetailsRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBusDetailsMockMvc;

    private BusDetails busDetails;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BusDetailsResource busDetailsResource = new BusDetailsResource();
        ReflectionTestUtils.setField(busDetailsResource, "busDetailsRepository", busDetailsRepository);
        this.restBusDetailsMockMvc = MockMvcBuilders.standaloneSetup(busDetailsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        busDetails = new BusDetails();
        busDetails.setBusNo(DEFAULT_BUS_NO);
        busDetails.setRoute(DEFAULT_ROUTE);
        busDetails.setTiming(DEFAULT_TIMING);
        busDetails.setDriverName(DEFAULT_DRIVER_NAME);
        busDetails.setDriverContactNo(DEFAULT_DRIVER_CONTACT_NO);
    }

    @Test
    @Transactional
    public void createBusDetails() throws Exception {
        int databaseSizeBeforeCreate = busDetailsRepository.findAll().size();

        // Create the BusDetails

        restBusDetailsMockMvc.perform(post("/api/busDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(busDetails)))
                .andExpect(status().isCreated());

        // Validate the BusDetails in the database
        List<BusDetails> busDetailss = busDetailsRepository.findAll();
        assertThat(busDetailss).hasSize(databaseSizeBeforeCreate + 1);
        BusDetails testBusDetails = busDetailss.get(busDetailss.size() - 1);
        assertThat(testBusDetails.getBusNo()).isEqualTo(DEFAULT_BUS_NO);
        assertThat(testBusDetails.getRoute()).isEqualTo(DEFAULT_ROUTE);
        assertThat(testBusDetails.getTiming()).isEqualTo(DEFAULT_TIMING);
        assertThat(testBusDetails.getDriverName()).isEqualTo(DEFAULT_DRIVER_NAME);
        assertThat(testBusDetails.getDriverContactNo()).isEqualTo(DEFAULT_DRIVER_CONTACT_NO);
    }

    @Test
    @Transactional
    public void getAllBusDetailss() throws Exception {
        // Initialize the database
        busDetailsRepository.saveAndFlush(busDetails);

        // Get all the busDetailss
        restBusDetailsMockMvc.perform(get("/api/busDetailss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(busDetails.getId().intValue())))
                .andExpect(jsonPath("$.[*].busNo").value(hasItem(DEFAULT_BUS_NO.toString())))
                .andExpect(jsonPath("$.[*].route").value(hasItem(DEFAULT_ROUTE.toString())))
                .andExpect(jsonPath("$.[*].timing").value(hasItem(DEFAULT_TIMING.toString())))
                .andExpect(jsonPath("$.[*].driverName").value(hasItem(DEFAULT_DRIVER_NAME.toString())))
                .andExpect(jsonPath("$.[*].driverContactNo").value(hasItem(DEFAULT_DRIVER_CONTACT_NO.toString())));
    }

    @Test
    @Transactional
    public void getBusDetails() throws Exception {
        // Initialize the database
        busDetailsRepository.saveAndFlush(busDetails);

        // Get the busDetails
        restBusDetailsMockMvc.perform(get("/api/busDetailss/{id}", busDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(busDetails.getId().intValue()))
            .andExpect(jsonPath("$.busNo").value(DEFAULT_BUS_NO.toString()))
            .andExpect(jsonPath("$.route").value(DEFAULT_ROUTE.toString()))
            .andExpect(jsonPath("$.timing").value(DEFAULT_TIMING.toString()))
            .andExpect(jsonPath("$.driverName").value(DEFAULT_DRIVER_NAME.toString()))
            .andExpect(jsonPath("$.driverContactNo").value(DEFAULT_DRIVER_CONTACT_NO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBusDetails() throws Exception {
        // Get the busDetails
        restBusDetailsMockMvc.perform(get("/api/busDetailss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusDetails() throws Exception {
        // Initialize the database
        busDetailsRepository.saveAndFlush(busDetails);

		int databaseSizeBeforeUpdate = busDetailsRepository.findAll().size();

        // Update the busDetails
        busDetails.setBusNo(UPDATED_BUS_NO);
        busDetails.setRoute(UPDATED_ROUTE);
        busDetails.setTiming(UPDATED_TIMING);
        busDetails.setDriverName(UPDATED_DRIVER_NAME);
        busDetails.setDriverContactNo(UPDATED_DRIVER_CONTACT_NO);

        restBusDetailsMockMvc.perform(put("/api/busDetailss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(busDetails)))
                .andExpect(status().isOk());

        // Validate the BusDetails in the database
        List<BusDetails> busDetailss = busDetailsRepository.findAll();
        assertThat(busDetailss).hasSize(databaseSizeBeforeUpdate);
        BusDetails testBusDetails = busDetailss.get(busDetailss.size() - 1);
        assertThat(testBusDetails.getBusNo()).isEqualTo(UPDATED_BUS_NO);
        assertThat(testBusDetails.getRoute()).isEqualTo(UPDATED_ROUTE);
        assertThat(testBusDetails.getTiming()).isEqualTo(UPDATED_TIMING);
        assertThat(testBusDetails.getDriverName()).isEqualTo(UPDATED_DRIVER_NAME);
        assertThat(testBusDetails.getDriverContactNo()).isEqualTo(UPDATED_DRIVER_CONTACT_NO);
    }

    @Test
    @Transactional
    public void deleteBusDetails() throws Exception {
        // Initialize the database
        busDetailsRepository.saveAndFlush(busDetails);

		int databaseSizeBeforeDelete = busDetailsRepository.findAll().size();

        // Get the busDetails
        restBusDetailsMockMvc.perform(delete("/api/busDetailss/{id}", busDetails.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BusDetails> busDetailss = busDetailsRepository.findAll();
        assertThat(busDetailss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
