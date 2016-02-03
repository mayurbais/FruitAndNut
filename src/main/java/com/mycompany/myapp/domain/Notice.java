package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.NoticeType;

import com.mycompany.myapp.domain.enumeration.PriorityLevel;

import com.mycompany.myapp.domain.enumeration.NoticeSensitivity;

/**
 * A Notice.
 */
@Entity
@Table(name = "notice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "notice_type")
    private NoticeType noticeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private PriorityLevel priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "sensitivity")
    private NoticeSensitivity sensitivity;

    @Column(name = "send_date")
    private ZonedDateTime sendDate;

    @Column(name = "is_read")
    private String isRead;

    @Column(name = "subject")
    private String subject;

    @Column(name = "sent_by")
    private String sentBy;

    @Column(name = "sent_to")
    private String sentTo;

    @Column(name = "message")
    private String message;

    @OneToOne
    private IrisUser irisUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NoticeType getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(NoticeType noticeType) {
        this.noticeType = noticeType;
    }

    public PriorityLevel getPriority() {
        return priority;
    }

    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public NoticeSensitivity getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(NoticeSensitivity sensitivity) {
        this.sensitivity = sensitivity;
    }

    public ZonedDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(ZonedDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IrisUser getIrisUser() {
        return irisUser;
    }

    public void setIrisUser(IrisUser irisUser) {
        this.irisUser = irisUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notice notice = (Notice) o;
        return Objects.equals(id, notice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Notice{" +
            "id=" + id +
            ", noticeType='" + noticeType + "'" +
            ", priority='" + priority + "'" +
            ", sensitivity='" + sensitivity + "'" +
            ", sendDate='" + sendDate + "'" +
            ", isRead='" + isRead + "'" +
            ", subject='" + subject + "'" +
            ", sentBy='" + sentBy + "'" +
            ", sentTo='" + sentTo + "'" +
            ", message='" + message + "'" +
            '}';
    }
}
