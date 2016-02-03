package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.SmsSetting;
import com.mycompany.myapp.repository.SmsSettingRepository;

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
 * Test class for the SmsSettingResource REST controller.
 *
 * @see SmsSettingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SmsSettingResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_CODE = "AAAAA";
    private static final String UPDATED_CODE = "BBBBB";
    private static final String DEFAULT_IS_ENABLED = "AAAAA";
    private static final String UPDATED_IS_ENABLED = "BBBBB";

    @Inject
    private SmsSettingRepository smsSettingRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSmsSettingMockMvc;

    private SmsSetting smsSetting;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SmsSettingResource smsSettingResource = new SmsSettingResource();
        ReflectionTestUtils.setField(smsSettingResource, "smsSettingRepository", smsSettingRepository);
        this.restSmsSettingMockMvc = MockMvcBuilders.standaloneSetup(smsSettingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        smsSetting = new SmsSetting();
        smsSetting.setName(DEFAULT_NAME);
        smsSetting.setCode(DEFAULT_CODE);
        smsSetting.setIsEnabled(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void createSmsSetting() throws Exception {
        int databaseSizeBeforeCreate = smsSettingRepository.findAll().size();

        // Create the SmsSetting

        restSmsSettingMockMvc.perform(post("/api/smsSettings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsSetting)))
                .andExpect(status().isCreated());

        // Validate the SmsSetting in the database
        List<SmsSetting> smsSettings = smsSettingRepository.findAll();
        assertThat(smsSettings).hasSize(databaseSizeBeforeCreate + 1);
        SmsSetting testSmsSetting = smsSettings.get(smsSettings.size() - 1);
        assertThat(testSmsSetting.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSmsSetting.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSmsSetting.getIsEnabled()).isEqualTo(DEFAULT_IS_ENABLED);
    }

    @Test
    @Transactional
    public void getAllSmsSettings() throws Exception {
        // Initialize the database
        smsSettingRepository.saveAndFlush(smsSetting);

        // Get all the smsSettings
        restSmsSettingMockMvc.perform(get("/api/smsSettings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(smsSetting.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].isEnabled").value(hasItem(DEFAULT_IS_ENABLED.toString())));
    }

    @Test
    @Transactional
    public void getSmsSetting() throws Exception {
        // Initialize the database
        smsSettingRepository.saveAndFlush(smsSetting);

        // Get the smsSetting
        restSmsSettingMockMvc.perform(get("/api/smsSettings/{id}", smsSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(smsSetting.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.isEnabled").value(DEFAULT_IS_ENABLED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSmsSetting() throws Exception {
        // Get the smsSetting
        restSmsSettingMockMvc.perform(get("/api/smsSettings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmsSetting() throws Exception {
        // Initialize the database
        smsSettingRepository.saveAndFlush(smsSetting);

		int databaseSizeBeforeUpdate = smsSettingRepository.findAll().size();

        // Update the smsSetting
        smsSetting.setName(UPDATED_NAME);
        smsSetting.setCode(UPDATED_CODE);
        smsSetting.setIsEnabled(UPDATED_IS_ENABLED);

        restSmsSettingMockMvc.perform(put("/api/smsSettings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(smsSetting)))
                .andExpect(status().isOk());

        // Validate the SmsSetting in the database
        List<SmsSetting> smsSettings = smsSettingRepository.findAll();
        assertThat(smsSettings).hasSize(databaseSizeBeforeUpdate);
        SmsSetting testSmsSetting = smsSettings.get(smsSettings.size() - 1);
        assertThat(testSmsSetting.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSmsSetting.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSmsSetting.getIsEnabled()).isEqualTo(UPDATED_IS_ENABLED);
    }

    @Test
    @Transactional
    public void deleteSmsSetting() throws Exception {
        // Initialize the database
        smsSettingRepository.saveAndFlush(smsSetting);

		int databaseSizeBeforeDelete = smsSettingRepository.findAll().size();

        // Get the smsSetting
        restSmsSettingMockMvc.perform(delete("/api/smsSettings/{id}", smsSetting.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SmsSetting> smsSettings = smsSettingRepository.findAll();
        assertThat(smsSettings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
