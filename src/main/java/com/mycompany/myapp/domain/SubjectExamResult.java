package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SubjectExamResult.
 */
@Entity
@Table(name = "subject_exam_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubjectExamResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "marks_obtained")
    private Integer marksObtained;

    @Column(name = "grade")
    private String grade;

    @Column(name = "is_passed")
    private Boolean isPassed;

    @Column(name = "is_absent")
    private Boolean isAbsent;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "remark")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "exam_subjects_id")
    private ExamSubjects examSubjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(Integer marksObtained) {
        this.marksObtained = marksObtained;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Boolean getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(Boolean isPassed) {
        this.isPassed = isPassed;
    }

    public Boolean getIsAbsent() {
        return isAbsent;
    }

    public void setIsAbsent(Boolean isAbsent) {
        this.isAbsent = isAbsent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ExamSubjects getExamSubjects() {
        return examSubjects;
    }

    public void setExamSubjects(ExamSubjects examSubjects) {
        this.examSubjects = examSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubjectExamResult subjectExamResult = (SubjectExamResult) o;
        return Objects.equals(id, subjectExamResult.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SubjectExamResult{" +
            "id=" + id +
            ", studentId='" + studentId + "'" +
            ", marksObtained='" + marksObtained + "'" +
            ", grade='" + grade + "'" +
            ", isPassed='" + isPassed + "'" +
            ", isAbsent='" + isAbsent + "'" +
            ", remark='" + remark + "'" +
            '}';
    }
}
