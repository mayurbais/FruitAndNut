package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.PrevSchoolInfo;
import com.mycompany.myapp.repository.PrevSchoolInfoRepository;

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
 * Test class for the PrevSchoolInfoResource REST controller.
 *
 * @see PrevSchoolInfoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PrevSchoolInfoResourceIntTest {

    private static final String DEFAULT_SCHOOL_NAME = "AAAAA";
    private static final String UPDATED_SCHOOL_NAME = "BBBBB";
    private static final String DEFAULT_GRADE = "AAAAA";
    private static final String UPDATED_GRADE = "BBBBB";
    private static final String DEFAULT_REMARK_BY = "AAAAA";
    private static final String UPDATED_REMARK_BY = "BBBBB";
    private static final String DEFAULT_REMARK = "AAAAA";
    private static final String UPDATED_REMARK = "BBBBB";
    private static final String DEFAULT_CONTACT_OF_REMARK = "AAAAA";
    private static final String UPDATED_CONTACT_OF_REMARK = "BBBBB";
    private static final String DEFAULT_REASON_FOR_CHANGE = "AAAAA";
    private static final String UPDATED_REASON_FOR_CHANGE = "BBBBB";

    @Inject
    private PrevSchoolInfoRepository prevSchoolInfoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPrevSchoolInfoMockMvc;

    private PrevSchoolInfo prevSchoolInfo;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PrevSchoolInfoResource prevSchoolInfoResource = new PrevSchoolInfoResource();
        ReflectionTestUtils.setField(prevSchoolInfoResource, "prevSchoolInfoRepository", prevSchoolInfoRepository);
        this.restPrevSchoolInfoMockMvc = MockMvcBuilders.standaloneSetup(prevSchoolInfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        prevSchoolInfo = new PrevSchoolInfo();
        prevSchoolInfo.setSchoolName(DEFAULT_SCHOOL_NAME);
        prevSchoolInfo.setGrade(DEFAULT_GRADE);
        prevSchoolInfo.setRemarkBy(DEFAULT_REMARK_BY);
        prevSchoolInfo.setRemark(DEFAULT_REMARK);
        prevSchoolInfo.setContactOfRemark(DEFAULT_CONTACT_OF_REMARK);
        prevSchoolInfo.setReasonForChange(DEFAULT_REASON_FOR_CHANGE);
    }

    @Test
    @Transactional
    public void createPrevSchoolInfo() throws Exception {
        int databaseSizeBeforeCreate = prevSchoolInfoRepository.findAll().size();

        // Create the PrevSchoolInfo

        restPrevSchoolInfoMockMvc.perform(post("/api/prevSchoolInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(prevSchoolInfo)))
                .andExpect(status().isCreated());

        // Validate the PrevSchoolInfo in the database
        List<PrevSchoolInfo> prevSchoolInfos = prevSchoolInfoRepository.findAll();
        assertThat(prevSchoolInfos).hasSize(databaseSizeBeforeCreate + 1);
        PrevSchoolInfo testPrevSchoolInfo = prevSchoolInfos.get(prevSchoolInfos.size() - 1);
        assertThat(testPrevSchoolInfo.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testPrevSchoolInfo.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testPrevSchoolInfo.getRemarkBy()).isEqualTo(DEFAULT_REMARK_BY);
        assertThat(testPrevSchoolInfo.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testPrevSchoolInfo.getContactOfRemark()).isEqualTo(DEFAULT_CONTACT_OF_REMARK);
        assertThat(testPrevSchoolInfo.getReasonForChange()).isEqualTo(DEFAULT_REASON_FOR_CHANGE);
    }

    @Test
    @Transactional
    public void getAllPrevSchoolInfos() throws Exception {
        // Initialize the database
        prevSchoolInfoRepository.saveAndFlush(prevSchoolInfo);

        // Get all the prevSchoolInfos
        restPrevSchoolInfoMockMvc.perform(get("/api/prevSchoolInfos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(prevSchoolInfo.getId().intValue())))
                .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME.toString())))
                .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
                .andExpect(jsonPath("$.[*].remarkBy").value(hasItem(DEFAULT_REMARK_BY.toString())))
                .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())))
                .andExpect(jsonPath("$.[*].contactOfRemark").value(hasItem(DEFAULT_CONTACT_OF_REMARK.toString())))
                .andExpect(jsonPath("$.[*].reasonForChange").value(hasItem(DEFAULT_REASON_FOR_CHANGE.toString())));
    }

    @Test
    @Transactional
    public void getPrevSchoolInfo() throws Exception {
        // Initialize the database
        prevSchoolInfoRepository.saveAndFlush(prevSchoolInfo);

        // Get the prevSchoolInfo
        restPrevSchoolInfoMockMvc.perform(get("/api/prevSchoolInfos/{id}", prevSchoolInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(prevSchoolInfo.getId().intValue()))
            .andExpect(jsonPath("$.schoolName").value(DEFAULT_SCHOOL_NAME.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.remarkBy").value(DEFAULT_REMARK_BY.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()))
            .andExpect(jsonPath("$.contactOfRemark").value(DEFAULT_CONTACT_OF_REMARK.toString()))
            .andExpect(jsonPath("$.reasonForChange").value(DEFAULT_REASON_FOR_CHANGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrevSchoolInfo() throws Exception {
        // Get the prevSchoolInfo
        restPrevSchoolInfoMockMvc.perform(get("/api/prevSchoolInfos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrevSchoolInfo() throws Exception {
        // Initialize the database
        prevSchoolInfoRepository.saveAndFlush(prevSchoolInfo);

		int databaseSizeBeforeUpdate = prevSchoolInfoRepository.findAll().size();

        // Update the prevSchoolInfo
        prevSchoolInfo.setSchoolName(UPDATED_SCHOOL_NAME);
        prevSchoolInfo.setGrade(UPDATED_GRADE);
        prevSchoolInfo.setRemarkBy(UPDATED_REMARK_BY);
        prevSchoolInfo.setRemark(UPDATED_REMARK);
        prevSchoolInfo.setContactOfRemark(UPDATED_CONTACT_OF_REMARK);
        prevSchoolInfo.setReasonForChange(UPDATED_REASON_FOR_CHANGE);

        restPrevSchoolInfoMockMvc.perform(put("/api/prevSchoolInfos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(prevSchoolInfo)))
                .andExpect(status().isOk());

        // Validate the PrevSchoolInfo in the database
        List<PrevSchoolInfo> prevSchoolInfos = prevSchoolInfoRepository.findAll();
        assertThat(prevSchoolInfos).hasSize(databaseSizeBeforeUpdate);
        PrevSchoolInfo testPrevSchoolInfo = prevSchoolInfos.get(prevSchoolInfos.size() - 1);
        assertThat(testPrevSchoolInfo.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testPrevSchoolInfo.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testPrevSchoolInfo.getRemarkBy()).isEqualTo(UPDATED_REMARK_BY);
        assertThat(testPrevSchoolInfo.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testPrevSchoolInfo.getContactOfRemark()).isEqualTo(UPDATED_CONTACT_OF_REMARK);
        assertThat(testPrevSchoolInfo.getReasonForChange()).isEqualTo(UPDATED_REASON_FOR_CHANGE);
    }

    @Test
    @Transactional
    public void deletePrevSchoolInfo() throws Exception {
        // Initialize the database
        prevSchoolInfoRepository.saveAndFlush(prevSchoolInfo);

		int databaseSizeBeforeDelete = prevSchoolInfoRepository.findAll().size();

        // Get the prevSchoolInfo
        restPrevSchoolInfoMockMvc.perform(delete("/api/prevSchoolInfos/{id}", prevSchoolInfo.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PrevSchoolInfo> prevSchoolInfos = prevSchoolInfoRepository.findAll();
        assertThat(prevSchoolInfos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
