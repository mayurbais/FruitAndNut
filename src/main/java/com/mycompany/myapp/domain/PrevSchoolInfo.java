package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PrevSchoolInfo.
 */
@Entity
@Table(name = "prev_school_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrevSchoolInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "grade")
    private String grade;

    @Column(name = "remark_by")
    private String remarkBy;

    @Column(name = "remark")
    private String remark;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "contact_of_remark")
    private String contactOfRemark;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "reason_for_change")
    private String reasonForChange;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRemarkBy() {
        return remarkBy;
    }

    public void setRemarkBy(String remarkBy) {
        this.remarkBy = remarkBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContactOfRemark() {
        return contactOfRemark;
    }

    public void setContactOfRemark(String contactOfRemark) {
        this.contactOfRemark = contactOfRemark;
    }

    public String getReasonForChange() {
        return reasonForChange;
    }

    public void setReasonForChange(String reasonForChange) {
        this.reasonForChange = reasonForChange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PrevSchoolInfo prevSchoolInfo = (PrevSchoolInfo) o;
        return Objects.equals(id, prevSchoolInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PrevSchoolInfo{" +
            "id=" + id +
            ", schoolName='" + schoolName + "'" +
            ", grade='" + grade + "'" +
            ", remarkBy='" + remarkBy + "'" +
            ", remark='" + remark + "'" +
            ", contactOfRemark='" + contactOfRemark + "'" +
            ", reasonForChange='" + reasonForChange + "'" +
            '}';
    }
}
