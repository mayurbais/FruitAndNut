package com.mycompany.myapp.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.DairyEntryType;

/**
 * A DairyTemplate.
 */
@Entity
@Table(name = "dairy_template")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DairyTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @Column(name = "date")
    private String date;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type")
    private DairyEntryType entryType;

    @OneToOne
    private Course course;

    @OneToOne
    private Student student;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DairyEntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(DairyEntryType entryType) {
        this.entryType = entryType;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DairyTemplate dairyTemplate = (DairyTemplate) o;
        return Objects.equals(id, dairyTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DairyTemplate{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", entryType='" + entryType + "'" +
            '}';
    }
}
