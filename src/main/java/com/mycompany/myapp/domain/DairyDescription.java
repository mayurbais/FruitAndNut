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
 * A DairyDescription.
 */
@Entity
@Table(name = "dairy_description")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DairyDescription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "rules")
    private String rules;

    @Column(name = "contact_no_of_managment")
    private String contactNoOfManagment;

    @Column(name = "mission")
    private String mission;

    @Column(name = "objective")
    private String objective;

    @Column(name = "declaration")
    private String declaration;

    @OneToOne
    private SchoolDetails schoolDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getContactNoOfManagment() {
        return contactNoOfManagment;
    }

    public void setContactNoOfManagment(String contactNoOfManagment) {
        this.contactNoOfManagment = contactNoOfManagment;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
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
        DairyDescription dairyDescription = (DairyDescription) o;
        return Objects.equals(id, dairyDescription.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DairyDescription{" +
            "id=" + id +
            ", rules='" + rules + "'" +
            ", contactNoOfManagment='" + contactNoOfManagment + "'" +
            ", mission='" + mission + "'" +
            ", objective='" + objective + "'" +
            ", declaration='" + declaration + "'" +
            '}';
    }
}
