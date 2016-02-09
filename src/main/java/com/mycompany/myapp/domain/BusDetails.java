package com.mycompany.myapp.domain;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Table(name = "bus_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BusDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "bus_no")
    private String busNo;

    @Column(name = "route")
    private String route;

    @Column(name = "timing")
    private String timing;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "driver_name")
    private String driverName;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "driver_contact_no")
    private String driverContactNo;

    @OneToOne
    private SchoolDetails schoolDetails;

    @OneToMany(mappedBy = "busDetails")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Student> students = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusNo() {
        return busNo;
    }

    public void setBusNo(String busNo) {
        this.busNo = busNo;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverContactNo() {
        return driverContactNo;
    }

    public void setDriverContactNo(String driverContactNo) {
        this.driverContactNo = driverContactNo;
    }

    public SchoolDetails getSchoolDetails() {
        return schoolDetails;
    }

    public void setSchoolDetails(SchoolDetails schoolDetails) {
        this.schoolDetails = schoolDetails;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BusDetails busDetails = (BusDetails) o;
        return Objects.equals(id, busDetails.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BusDetails{" +
            "id=" + id +
            ", busNo='" + busNo + "'" +
            ", route='" + route + "'" +
            ", timing='" + timing + "'" +
            ", driverName='" + driverName + "'" +
            ", driverContactNo='" + driverContactNo + "'" +
            '}';
    }
}
