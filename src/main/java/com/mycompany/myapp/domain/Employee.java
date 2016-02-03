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

import com.mycompany.myapp.domain.enumeration.EmployeeCategory;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private EmployeeCategory category;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_on_leave")
    private Boolean isOnLeave;

    @Column(name = "leave_from")
    private ZonedDateTime leaveFrom;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "leave_till")
    private ZonedDateTime leaveTill;

    @OneToOne
    private IrisUser irisUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeCategory getCategory() {
        return category;
    }

    public void setCategory(EmployeeCategory category) {
        this.category = category;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsOnLeave() {
        return isOnLeave;
    }

    public void setIsOnLeave(Boolean isOnLeave) {
        this.isOnLeave = isOnLeave;
    }

    public ZonedDateTime getLeaveFrom() {
        return leaveFrom;
    }

    public void setLeaveFrom(ZonedDateTime leaveFrom) {
        this.leaveFrom = leaveFrom;
    }

    public ZonedDateTime getLeaveTill() {
        return leaveTill;
    }

    public void setLeaveTill(ZonedDateTime leaveTill) {
        this.leaveTill = leaveTill;
    }

    public IrisUser getIrisUser() {
        return irisUser;
    }

    public void setIrisUser(IrisUser irisUser) {
        this.irisUser = irisUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", category='" + category + "'" +
            ", isActive='" + isActive + "'" +
            ", isOnLeave='" + isOnLeave + "'" +
            ", leaveFrom='" + leaveFrom + "'" +
            ", leaveTill='" + leaveTill + "'" +
            '}';
    }
}
