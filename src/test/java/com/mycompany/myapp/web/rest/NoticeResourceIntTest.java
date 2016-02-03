package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.Notice;
import com.mycompany.myapp.repository.NoticeRepository;

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

import com.mycompany.myapp.domain.enumeration.NoticeType;
import com.mycompany.myapp.domain.enumeration.PriorityLevel;
import com.mycompany.myapp.domain.enumeration.NoticeSensitivity;

/**
 * Test class for the NoticeResource REST controller.
 *
 * @see NoticeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class NoticeResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));



    private static final NoticeType DEFAULT_NOTICE_TYPE = NoticeType.NOTIFICATION;
    private static final NoticeType UPDATED_NOTICE_TYPE = NoticeType.REMINDER;


    private static final PriorityLevel DEFAULT_PRIORITY = PriorityLevel.HIGH;
    private static final PriorityLevel UPDATED_PRIORITY = PriorityLevel.NORMAL;


    private static final NoticeSensitivity DEFAULT_SENSITIVITY = NoticeSensitivity.PERSONAL;
    private static final NoticeSensitivity UPDATED_SENSITIVITY = NoticeSensitivity.PRIVATE;

    private static final ZonedDateTime DEFAULT_SEND_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SEND_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SEND_DATE_STR = dateTimeFormatter.format(DEFAULT_SEND_DATE);
    private static final String DEFAULT_IS_READ = "AAAAA";
    private static final String UPDATED_IS_READ = "BBBBB";
    private static final String DEFAULT_SUBJECT = "AAAAA";
    private static final String UPDATED_SUBJECT = "BBBBB";
    private static final String DEFAULT_SENT_BY = "AAAAA";
    private static final String UPDATED_SENT_BY = "BBBBB";
    private static final String DEFAULT_SENT_TO = "AAAAA";
    private static final String UPDATED_SENT_TO = "BBBBB";
    private static final String DEFAULT_MESSAGE = "AAAAA";
    private static final String UPDATED_MESSAGE = "BBBBB";

    @Inject
    private NoticeRepository noticeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restNoticeMockMvc;

    private Notice notice;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        NoticeResource noticeResource = new NoticeResource();
        ReflectionTestUtils.setField(noticeResource, "noticeRepository", noticeRepository);
        this.restNoticeMockMvc = MockMvcBuilders.standaloneSetup(noticeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        notice = new Notice();
        notice.setNoticeType(DEFAULT_NOTICE_TYPE);
        notice.setPriority(DEFAULT_PRIORITY);
        notice.setSensitivity(DEFAULT_SENSITIVITY);
        notice.setSendDate(DEFAULT_SEND_DATE);
        notice.setIsRead(DEFAULT_IS_READ);
        notice.setSubject(DEFAULT_SUBJECT);
        notice.setSentBy(DEFAULT_SENT_BY);
        notice.setSentTo(DEFAULT_SENT_TO);
        notice.setMessage(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createNotice() throws Exception {
        int databaseSizeBeforeCreate = noticeRepository.findAll().size();

        // Create the Notice

        restNoticeMockMvc.perform(post("/api/notices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(notice)))
                .andExpect(status().isCreated());

        // Validate the Notice in the database
        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices).hasSize(databaseSizeBeforeCreate + 1);
        Notice testNotice = notices.get(notices.size() - 1);
        assertThat(testNotice.getNoticeType()).isEqualTo(DEFAULT_NOTICE_TYPE);
        assertThat(testNotice.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testNotice.getSensitivity()).isEqualTo(DEFAULT_SENSITIVITY);
        assertThat(testNotice.getSendDate()).isEqualTo(DEFAULT_SEND_DATE);
        assertThat(testNotice.getIsRead()).isEqualTo(DEFAULT_IS_READ);
        assertThat(testNotice.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testNotice.getSentBy()).isEqualTo(DEFAULT_SENT_BY);
        assertThat(testNotice.getSentTo()).isEqualTo(DEFAULT_SENT_TO);
        assertThat(testNotice.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllNotices() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the notices
        restNoticeMockMvc.perform(get("/api/notices?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(notice.getId().intValue())))
                .andExpect(jsonPath("$.[*].noticeType").value(hasItem(DEFAULT_NOTICE_TYPE.toString())))
                .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY.toString())))
                .andExpect(jsonPath("$.[*].sensitivity").value(hasItem(DEFAULT_SENSITIVITY.toString())))
                .andExpect(jsonPath("$.[*].sendDate").value(hasItem(DEFAULT_SEND_DATE_STR)))
                .andExpect(jsonPath("$.[*].isRead").value(hasItem(DEFAULT_IS_READ.toString())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].sentBy").value(hasItem(DEFAULT_SENT_BY.toString())))
                .andExpect(jsonPath("$.[*].sentTo").value(hasItem(DEFAULT_SENT_TO.toString())))
                .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }

    @Test
    @Transactional
    public void getNotice() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get the notice
        restNoticeMockMvc.perform(get("/api/notices/{id}", notice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(notice.getId().intValue()))
            .andExpect(jsonPath("$.noticeType").value(DEFAULT_NOTICE_TYPE.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY.toString()))
            .andExpect(jsonPath("$.sensitivity").value(DEFAULT_SENSITIVITY.toString()))
            .andExpect(jsonPath("$.sendDate").value(DEFAULT_SEND_DATE_STR))
            .andExpect(jsonPath("$.isRead").value(DEFAULT_IS_READ.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.sentBy").value(DEFAULT_SENT_BY.toString()))
            .andExpect(jsonPath("$.sentTo").value(DEFAULT_SENT_TO.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNotice() throws Exception {
        // Get the notice
        restNoticeMockMvc.perform(get("/api/notices/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotice() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

		int databaseSizeBeforeUpdate = noticeRepository.findAll().size();

        // Update the notice
        notice.setNoticeType(UPDATED_NOTICE_TYPE);
        notice.setPriority(UPDATED_PRIORITY);
        notice.setSensitivity(UPDATED_SENSITIVITY);
        notice.setSendDate(UPDATED_SEND_DATE);
        notice.setIsRead(UPDATED_IS_READ);
        notice.setSubject(UPDATED_SUBJECT);
        notice.setSentBy(UPDATED_SENT_BY);
        notice.setSentTo(UPDATED_SENT_TO);
        notice.setMessage(UPDATED_MESSAGE);

        restNoticeMockMvc.perform(put("/api/notices")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(notice)))
                .andExpect(status().isOk());

        // Validate the Notice in the database
        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices).hasSize(databaseSizeBeforeUpdate);
        Notice testNotice = notices.get(notices.size() - 1);
        assertThat(testNotice.getNoticeType()).isEqualTo(UPDATED_NOTICE_TYPE);
        assertThat(testNotice.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testNotice.getSensitivity()).isEqualTo(UPDATED_SENSITIVITY);
        assertThat(testNotice.getSendDate()).isEqualTo(UPDATED_SEND_DATE);
        assertThat(testNotice.getIsRead()).isEqualTo(UPDATED_IS_READ);
        assertThat(testNotice.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testNotice.getSentBy()).isEqualTo(UPDATED_SENT_BY);
        assertThat(testNotice.getSentTo()).isEqualTo(UPDATED_SENT_TO);
        assertThat(testNotice.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void deleteNotice() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

		int databaseSizeBeforeDelete = noticeRepository.findAll().size();

        // Get the notice
        restNoticeMockMvc.perform(delete("/api/notices/{id}", notice.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices).hasSize(databaseSizeBeforeDelete - 1);
    }
}
