package com.mycompany.myapp.domain;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.IrisUserRole;

/**
 * <Enter note text here>
 */
@ApiModel(description = ""
    + "<Enter note text here>")
@Entity
@Table(name = "attendance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Attendance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_for")
    private IrisUserRole attendanceFor;

    @Column(name = "date")
    private ZonedDateTime date;

    @Column(name = "is_present")
    private Boolean isPresent;

    @Size(min = 10, max = 50)
    @Column(name = "reason_for_absent", length = 50)
    private String reasonForAbsent;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "attribute")
    private String attribute;

    @OneToOne
    private IrisUser irisUser;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IrisUserRole getAttendanceFor() {
        return attendanceFor;
    }

    public void setAttendanceFor(IrisUserRole attendanceFor) {
        this.attendanceFor = attendanceFor;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    public String getReasonForAbsent() {
        return reasonForAbsent;
    }

    public void setReasonForAbsent(String reasonForAbsent) {
        this.reasonForAbsent = reasonForAbsent;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public IrisUser getIrisUser() {
        return irisUser;
    }

    public void setIrisUser(IrisUser irisUser) {
        this.irisUser = irisUser;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance attendance = (Attendance) o;
        return Objects.equals(id, attendance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Attendance{" +
            "id=" + id +
            ", attendanceFor='" + attendanceFor + "'" +
            ", date='" + date + "'" +
            ", isPresent='" + isPresent + "'" +
            ", reasonForAbsent='" + reasonForAbsent + "'" +
            ", isApproved='" + isApproved + "'" +
            ", approvedBy='" + approvedBy + "'" +
            ", attribute='" + attribute + "'" +
            '}';
    }
}
