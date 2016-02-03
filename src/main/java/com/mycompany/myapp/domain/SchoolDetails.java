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

import com.mycompany.myapp.domain.enumeration.AttendanceType;

import com.mycompany.myapp.domain.enumeration.Days;

import com.mycompany.myapp.domain.enumeration.DateFormat;

import com.mycompany.myapp.domain.enumeration.GradingType;

import com.mycompany.myapp.domain.enumeration.InstitutionType;

/**
 * A SchoolDetails.
 */
@Entity
@Table(name = "school_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SchoolDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 5, max = 20)
    @Column(name = "school_name", length = 20)
    private String schoolName;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "phone_number", nullable = false)
    private Integer phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendance_type")
    private AttendanceType attendanceType;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Enumerated(EnumType.STRING)
    @Column(name = "start_day_of_the_week")
    private Days startDayOfTheWeek;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Enumerated(EnumType.STRING)
    @Column(name = "date_format")
    private DateFormat dateFormat;

    @NotNull
    @Column(name = "financial_start_date", nullable = false)
    private LocalDate financialStartDate;

    @NotNull
    @Column(name = "financial_end_date", nullable = false)
    private LocalDate financialEndDate;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "logo")
    private String logo;

    @Enumerated(EnumType.STRING)
    @Column(name = "grading_system")
    private GradingType gradingSystem;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "enable_auto_increament_of_admission_no")
    private Boolean enableAutoIncreamentOfAdmissionNo;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "enable_news_comments_moderation")
    private Boolean enableNewsCommentsModeration;

    @Column(name = "enable_sibling")
    private Boolean enableSibling;

    @Column(name = "enable_password_change_at_first_login")
    private Boolean enablePasswordChangeAtFirstLogin;

    @Column(name = "enable_roll_number_for_student")
    private Boolean enableRollNumberForStudent;

    @Enumerated(EnumType.STRING)
    @Column(name = "institution_type")
    private InstitutionType institutionType;

    @OneToOne
    private User user;

    @OneToOne
    private LanguageLOV languageLOV;

    @OneToOne
    private CurrancyLOV currancyLOV;

    @OneToOne
    private CountryLOV countryLOV;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }

    public void setAttendanceType(AttendanceType attendanceType) {
        this.attendanceType = attendanceType;
    }

    public Days getStartDayOfTheWeek() {
        return startDayOfTheWeek;
    }

    public void setStartDayOfTheWeek(Days startDayOfTheWeek) {
        this.startDayOfTheWeek = startDayOfTheWeek;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public LocalDate getFinancialStartDate() {
        return financialStartDate;
    }

    public void setFinancialStartDate(LocalDate financialStartDate) {
        this.financialStartDate = financialStartDate;
    }

    public LocalDate getFinancialEndDate() {
        return financialEndDate;
    }

    public void setFinancialEndDate(LocalDate financialEndDate) {
        this.financialEndDate = financialEndDate;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public GradingType getGradingSystem() {
        return gradingSystem;
    }

    public void setGradingSystem(GradingType gradingSystem) {
        this.gradingSystem = gradingSystem;
    }

    public Boolean getEnableAutoIncreamentOfAdmissionNo() {
        return enableAutoIncreamentOfAdmissionNo;
    }

    public void setEnableAutoIncreamentOfAdmissionNo(Boolean enableAutoIncreamentOfAdmissionNo) {
        this.enableAutoIncreamentOfAdmissionNo = enableAutoIncreamentOfAdmissionNo;
    }

    public Boolean getEnableNewsCommentsModeration() {
        return enableNewsCommentsModeration;
    }

    public void setEnableNewsCommentsModeration(Boolean enableNewsCommentsModeration) {
        this.enableNewsCommentsModeration = enableNewsCommentsModeration;
    }

    public Boolean getEnableSibling() {
        return enableSibling;
    }

    public void setEnableSibling(Boolean enableSibling) {
        this.enableSibling = enableSibling;
    }

    public Boolean getEnablePasswordChangeAtFirstLogin() {
        return enablePasswordChangeAtFirstLogin;
    }

    public void setEnablePasswordChangeAtFirstLogin(Boolean enablePasswordChangeAtFirstLogin) {
        this.enablePasswordChangeAtFirstLogin = enablePasswordChangeAtFirstLogin;
    }

    public Boolean getEnableRollNumberForStudent() {
        return enableRollNumberForStudent;
    }

    public void setEnableRollNumberForStudent(Boolean enableRollNumberForStudent) {
        this.enableRollNumberForStudent = enableRollNumberForStudent;
    }

    public InstitutionType getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(InstitutionType institutionType) {
        this.institutionType = institutionType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LanguageLOV getLanguageLOV() {
        return languageLOV;
    }

    public void setLanguageLOV(LanguageLOV languageLOV) {
        this.languageLOV = languageLOV;
    }

    public CurrancyLOV getCurrancyLOV() {
        return currancyLOV;
    }

    public void setCurrancyLOV(CurrancyLOV currancyLOV) {
        this.currancyLOV = currancyLOV;
    }

    public CountryLOV getCountryLOV() {
        return countryLOV;
    }

    public void setCountryLOV(CountryLOV countryLOV) {
        this.countryLOV = countryLOV;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SchoolDetails schoolDetails = (SchoolDetails) o;
        return Objects.equals(id, schoolDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SchoolDetails{" +
            "id=" + id +
            ", schoolName='" + schoolName + "'" +
            ", address='" + address + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            ", attendanceType='" + attendanceType + "'" +
            ", startDayOfTheWeek='" + startDayOfTheWeek + "'" +
            ", dateFormat='" + dateFormat + "'" +
            ", financialStartDate='" + financialStartDate + "'" +
            ", financialEndDate='" + financialEndDate + "'" +
            ", logo='" + logo + "'" +
            ", gradingSystem='" + gradingSystem + "'" +
            ", enableAutoIncreamentOfAdmissionNo='" + enableAutoIncreamentOfAdmissionNo + "'" +
            ", enableNewsCommentsModeration='" + enableNewsCommentsModeration + "'" +
            ", enableSibling='" + enableSibling + "'" +
            ", enablePasswordChangeAtFirstLogin='" + enablePasswordChangeAtFirstLogin + "'" +
            ", enableRollNumberForStudent='" + enableRollNumberForStudent + "'" +
            ", institutionType='" + institutionType + "'" +
            '}';
    }
}
