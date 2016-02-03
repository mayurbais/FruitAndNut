package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.IrisUser;
import com.mycompany.myapp.repository.IrisUserRepository;

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

import com.mycompany.myapp.domain.enumeration.Gender;
import com.mycompany.myapp.domain.enumeration.BloodGroup;

/**
 * Test class for the IrisUserResource REST controller.
 *
 * @see IrisUserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class IrisUserResourceIntTest {

    private static final String DEFAULT_FIRST_NAME = "AAA";
    private static final String UPDATED_FIRST_NAME = "BBB";
    private static final String DEFAULT_LAST_NAME = "AAA";
    private static final String UPDATED_LAST_NAME = "BBB";
    private static final String DEFAULT_MIDDLE_NAME = "AAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());


    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;


    private static final BloodGroup DEFAULT_BLOOD_GROUP = BloodGroup.A_PLUS;
    private static final BloodGroup UPDATED_BLOOD_GROUP = BloodGroup.A_MINUS;
    private static final String DEFAULT_BIRTH_PLACE = "AAAAA";
    private static final String UPDATED_BIRTH_PLACE = "BBBBB";
    private static final String DEFAULT_RELIGION = "AAAAA";
    private static final String UPDATED_RELIGION = "BBBBB";
    private static final String DEFAULT_PHOTO = "AAAAA";
    private static final String UPDATED_PHOTO = "BBBBB";

    private static final Long DEFAULT_PHONE = 1L;
    private static final Long UPDATED_PHONE = 2L;

    @Inject
    private IrisUserRepository irisUserRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restIrisUserMockMvc;

    private IrisUser irisUser;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IrisUserResource irisUserResource = new IrisUserResource();
        ReflectionTestUtils.setField(irisUserResource, "irisUserRepository", irisUserRepository);
        this.restIrisUserMockMvc = MockMvcBuilders.standaloneSetup(irisUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        irisUser = new IrisUser();
        irisUser.setFirstName(DEFAULT_FIRST_NAME);
        irisUser.setLastName(DEFAULT_LAST_NAME);
        irisUser.setMiddleName(DEFAULT_MIDDLE_NAME);
        irisUser.setDateOfBirth(DEFAULT_DATE_OF_BIRTH);
        irisUser.setGender(DEFAULT_GENDER);
        irisUser.setBloodGroup(DEFAULT_BLOOD_GROUP);
        irisUser.setBirthPlace(DEFAULT_BIRTH_PLACE);
        irisUser.setReligion(DEFAULT_RELIGION);
        irisUser.setPhoto(DEFAULT_PHOTO);
        irisUser.setPhone(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createIrisUser() throws Exception {
        int databaseSizeBeforeCreate = irisUserRepository.findAll().size();

        // Create the IrisUser

        restIrisUserMockMvc.perform(post("/api/irisUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(irisUser)))
                .andExpect(status().isCreated());

        // Validate the IrisUser in the database
        List<IrisUser> irisUsers = irisUserRepository.findAll();
        assertThat(irisUsers).hasSize(databaseSizeBeforeCreate + 1);
        IrisUser testIrisUser = irisUsers.get(irisUsers.size() - 1);
        assertThat(testIrisUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testIrisUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testIrisUser.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testIrisUser.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testIrisUser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testIrisUser.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testIrisUser.getBirthPlace()).isEqualTo(DEFAULT_BIRTH_PLACE);
        assertThat(testIrisUser.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testIrisUser.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testIrisUser.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = irisUserRepository.findAll().size();
        // set the field null
        irisUser.setFirstName(null);

        // Create the IrisUser, which fails.

        restIrisUserMockMvc.perform(post("/api/irisUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(irisUser)))
                .andExpect(status().isBadRequest());

        List<IrisUser> irisUsers = irisUserRepository.findAll();
        assertThat(irisUsers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = irisUserRepository.findAll().size();
        // set the field null
        irisUser.setLastName(null);

        // Create the IrisUser, which fails.

        restIrisUserMockMvc.perform(post("/api/irisUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(irisUser)))
                .andExpect(status().isBadRequest());

        List<IrisUser> irisUsers = irisUserRepository.findAll();
        assertThat(irisUsers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = irisUserRepository.findAll().size();
        // set the field null
        irisUser.setDateOfBirth(null);

        // Create the IrisUser, which fails.

        restIrisUserMockMvc.perform(post("/api/irisUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(irisUser)))
                .andExpect(status().isBadRequest());

        List<IrisUser> irisUsers = irisUserRepository.findAll();
        assertThat(irisUsers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIrisUsers() throws Exception {
        // Initialize the database
        irisUserRepository.saveAndFlush(irisUser);

        // Get all the irisUsers
        restIrisUserMockMvc.perform(get("/api/irisUsers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(irisUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
                .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
                .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
                .andExpect(jsonPath("$.[*].birthPlace").value(hasItem(DEFAULT_BIRTH_PLACE.toString())))
                .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION.toString())))
                .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())));
    }

    @Test
    @Transactional
    public void getIrisUser() throws Exception {
        // Initialize the database
        irisUserRepository.saveAndFlush(irisUser);

        // Get the irisUser
        restIrisUserMockMvc.perform(get("/api/irisUsers/{id}", irisUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(irisUser.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.birthPlace").value(DEFAULT_BIRTH_PLACE.toString()))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIrisUser() throws Exception {
        // Get the irisUser
        restIrisUserMockMvc.perform(get("/api/irisUsers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIrisUser() throws Exception {
        // Initialize the database
        irisUserRepository.saveAndFlush(irisUser);

		int databaseSizeBeforeUpdate = irisUserRepository.findAll().size();

        // Update the irisUser
        irisUser.setFirstName(UPDATED_FIRST_NAME);
        irisUser.setLastName(UPDATED_LAST_NAME);
        irisUser.setMiddleName(UPDATED_MIDDLE_NAME);
        irisUser.setDateOfBirth(UPDATED_DATE_OF_BIRTH);
        irisUser.setGender(UPDATED_GENDER);
        irisUser.setBloodGroup(UPDATED_BLOOD_GROUP);
        irisUser.setBirthPlace(UPDATED_BIRTH_PLACE);
        irisUser.setReligion(UPDATED_RELIGION);
        irisUser.setPhoto(UPDATED_PHOTO);
        irisUser.setPhone(UPDATED_PHONE);

        restIrisUserMockMvc.perform(put("/api/irisUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(irisUser)))
                .andExpect(status().isOk());

        // Validate the IrisUser in the database
        List<IrisUser> irisUsers = irisUserRepository.findAll();
        assertThat(irisUsers).hasSize(databaseSizeBeforeUpdate);
        IrisUser testIrisUser = irisUsers.get(irisUsers.size() - 1);
        assertThat(testIrisUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testIrisUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testIrisUser.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testIrisUser.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testIrisUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testIrisUser.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testIrisUser.getBirthPlace()).isEqualTo(UPDATED_BIRTH_PLACE);
        assertThat(testIrisUser.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testIrisUser.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testIrisUser.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void deleteIrisUser() throws Exception {
        // Initialize the database
        irisUserRepository.saveAndFlush(irisUser);

		int databaseSizeBeforeDelete = irisUserRepository.findAll().size();

        // Get the irisUser
        restIrisUserMockMvc.perform(delete("/api/irisUsers/{id}", irisUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<IrisUser> irisUsers = irisUserRepository.findAll();
        assertThat(irisUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
