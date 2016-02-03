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
 * A AcedemicSession.
 */
@Entity
@Table(name = "acedemic_session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AcedemicSession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "session_start_date")
    private ZonedDateTime sessionStartDate;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "session_end_date")
    private ZonedDateTime sessionEndDate;

    @ManyToOne
    @JoinColumn(name = "school_details_id")
    private SchoolDetails schoolDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getSessionStartDate() {
        return sessionStartDate;
    }

    public void setSessionStartDate(ZonedDateTime sessionStartDate) {
        this.sessionStartDate = sessionStartDate;
    }

    public ZonedDateTime getSessionEndDate() {
        return sessionEndDate;
    }

    public void setSessionEndDate(ZonedDateTime sessionEndDate) {
        this.sessionEndDate = sessionEndDate;
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
        AcedemicSession acedemicSession = (AcedemicSession) o;
        return Objects.equals(id, acedemicSession.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AcedemicSession{" +
            "id=" + id +
            ", sessionStartDate='" + sessionStartDate + "'" +
            ", sessionEndDate='" + sessionEndDate + "'" +
            '}';
    }
}
