package com.mycompany.myapp.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * <Enter note text here>
 */
@ApiModel(description = ""
    + "<Enter note text here>")
@Entity
@Table(name = "exam_subjects")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExamSubjects implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "max_marks")
    private Integer maxMarks;

    @Column(name = "min_pass_mark")
    private Integer minPassMark;

    @Column(name = "is_grade")
    private Boolean isGrade;

    @Column(name = "start_time")
    private ZonedDateTime startTime;

    @Column(name = "end_time")
    private ZonedDateTime endTime;

    @Column(name = "conducting_date")
    private ZonedDateTime conductingDate;

    @Column(name = "is_result_published")
    private Boolean isResultPublished;

    @Column(name = "class_average")
    private Integer classAverage;

    @Column(name = "remark_by_principal")
    private String remarkByPrincipal;

    @Column(name = "remark_by_head_teacher")
    private String remarkByHeadTeacher;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @OneToOne
    private Subject subject;

    @OneToOne
    private Teacher teacher;

    @OneToOne
    private Room room;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Integer maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Integer getMinPassMark() {
        return minPassMark;
    }

    public void setMinPassMark(Integer minPassMark) {
        this.minPassMark = minPassMark;
    }

    public Boolean getIsGrade() {
        return isGrade;
    }

    public void setIsGrade(Boolean isGrade) {
        this.isGrade = isGrade;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public ZonedDateTime getConductingDate() {
        return conductingDate;
    }

    public void setConductingDate(ZonedDateTime conductingDate) {
        this.conductingDate = conductingDate;
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

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExamSubjects examSubjects = (ExamSubjects) o;
        return Objects.equals(id, examSubjects.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExamSubjects{" +
            "id=" + id +
            ", maxMarks='" + maxMarks + "'" +
            ", minPassMark='" + minPassMark + "'" +
            ", isGrade='" + isGrade + "'" +
            ", startTime='" + startTime + "'" +
            ", endTime='" + endTime + "'" +
            ", conductingDate='" + conductingDate + "'" +
            ", isResultPublished='" + isResultPublished + "'" +
            ", classAverage='" + classAverage + "'" +
            ", remarkByPrincipal='" + remarkByPrincipal + "'" +
            ", remarkByHeadTeacher='" + remarkByHeadTeacher + "'" +
            '}';
    }
}
