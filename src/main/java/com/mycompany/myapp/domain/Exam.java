package com.mycompany.myapp.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.ExamType;

import com.mycompany.myapp.domain.enumeration.ProgressStatus;

/**
 * <Enter note text here>
 */
@ApiModel(description = ""
    + "<Enter note text here>")
@Entity
@Table(name = "exam")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Exam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ExamType type;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @Column(name = "is_published")
    private Boolean isPublished;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress_status")
    private ProgressStatus progressStatus;

    @Column(name = "is_result_published")
    private Boolean isResultPublished;

    @Column(name = "class_average")
    private Integer classAverage;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "remark_by_principal")
    private String remarkByPrincipal;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "remark_by_head_teacher")
    private String remarkByHeadTeacher;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "exam")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ExamSubjects> examSubjectss = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExamType getType() {
        return type;
    }

    public void setType(ExamType type) {
        this.type = type;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public ProgressStatus getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }

    public Boolean getIsResultPublished() {
        return isResultPublished;
    }

    public void setIsResultPublished(Boolean isResultPublished) {
        this.isResultPublished = isResultPublished;
    }

    public Integer getClassAverage() {
        return classAverage;
    }

    public void setClassAverage(Integer classAverage) {
        this.classAverage = classAverage;
    }

    public String getRemarkByPrincipal() {
        return remarkByPrincipal;
    }

    public void setRemarkByPrincipal(String remarkByPrincipal) {
        this.remarkByPrincipal = remarkByPrincipal;
    }

    public String getRemarkByHeadTeacher() {
        return remarkByHeadTeacher;
    }

    public void setRemarkByHeadTeacher(String remarkByHeadTeacher) {
        this.remarkByHeadTeacher = remarkByHeadTeacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Set<ExamSubjects> getExamSubjectss() {
        return examSubjectss;
    }

    public void setExamSubjectss(Set<ExamSubjects> examSubjectss) {
        this.examSubjectss = examSubjectss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exam exam = (Exam) o;
        return Objects.equals(id, exam.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Exam{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            ", isPublished='" + isPublished + "'" +
            ", progressStatus='" + progressStatus + "'" +
            ", isResultPublished='" + isResultPublished + "'" +
            ", classAverage='" + classAverage + "'" +
            ", remarkByPrincipal='" + remarkByPrincipal + "'" +
            ", remarkByHeadTeacher='" + remarkByHeadTeacher + "'" +
            '}';
    }
}
