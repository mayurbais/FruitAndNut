package com.mycompany.myapp.domain;

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
 * A YearlyDairyDescription.
 */
@Entity
@Table(name = "yearly_dairy_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class YearlyDairyDescription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "year")
    private ZonedDateTime year;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "theme")
    private String theme;

    @Column(name = "summer_dress_code")
    private String summerDressCode;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "winter_dress_code")
    private String winterDressCode;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @OneToOne
    private SchoolDetails schoolDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getYear() {
        return year;
    }

    public void setYear(ZonedDateTime year) {
        this.year = year;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSummerDressCode() {
        return summerDressCode;
    }

    public void setSummerDressCode(String summerDressCode) {
        this.summerDressCode = summerDressCode;
    }

    public String getWinterDressCode() {
        return winterDressCode;
    }

    public void setWinterDressCode(String winterDressCode) {
        this.winterDressCode = winterDressCode;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public SchoolDetails getSchoolDetails() {
        return schoolDetails;
    }

    public void setSchoolDetails(SchoolDetails schoolDetails) {
        this.schoolDetails = schoolDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YearlyDairyDescription yearlyDairyDescription = (YearlyDairyDescription) o;
        return Objects.equals(id, yearlyDairyDescription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "YearlyDairyDescription{" +
            "id=" + id +
            ", year='" + year + "'" +
            ", theme='" + theme + "'" +
            ", summerDressCode='" + summerDressCode + "'" +
            ", winterDressCode='" + winterDressCode + "'" +
            ", isEnabled='" + isEnabled + "'" +
            '}';
    }
}
