package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AdmissionDetails.
 */
@Entity
@Table(name = "admission_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdmissionDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "admission_no", nullable = false)
    private String admissionNo;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @NotNull
    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @OneToOne
    private Student student;

    @OneToOne
    private PrevSchoolInfo prevSchoolDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public PrevSchoolInfo getPrevSchoolDetail() {
        return prevSchoolDetail;
    }

    public void setPrevSchoolDetail(PrevSchoolInfo prevSchoolInfo) {
        this.prevSchoolDetail = prevSchoolInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdmissionDetails admissionDetails = (AdmissionDetails) o;
        return Objects.equals(id, admissionDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AdmissionDetails{" +
            "id=" + id +
            ", admissionNo='" + admissionNo + "'" +
            ", admissionDate='" + admissionDate + "'" +
            '}';
    }
}
