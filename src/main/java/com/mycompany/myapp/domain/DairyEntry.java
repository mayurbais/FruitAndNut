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

import com.mycompany.myapp.domain.enumeration.DairyEntryType;

/**
 * A DairyEntry.
 */
@Entity
@Table(name = "dairy_entry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DairyEntry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private ZonedDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type")
    private DairyEntryType entryType;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "dairy_description")
    private String dairyDescription;

    @Column(name = "is_for_all")
    private Boolean isForAll;

    @OneToOne
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToOne
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public DairyEntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(DairyEntryType entryType) {
        this.entryType = entryType;
    }

    public String getDairyDescription() {
        return dairyDescription;
    }

    public void setDairyDescription(String dairyDescription) {
        this.dairyDescription = dairyDescription;
    }

    public Boolean getIsForAll() {
        return isForAll;
    }

    public void setIsForAll(Boolean isForAll) {
        this.isForAll = isForAll;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DairyEntry dairyEntry = (DairyEntry) o;
        return Objects.equals(id, dairyEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DairyEntry{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", entryType='" + entryType + "'" +
            ", dairyDescription='" + dairyDescription + "'" +
            ", isForAll='" + isForAll + "'" +
            '}';
    }
}
