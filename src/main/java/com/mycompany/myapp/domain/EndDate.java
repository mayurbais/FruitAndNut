package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.ZonedDateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Days;

/**
 * A EndDate.
 */
@Entity
@Table(name = "end_date")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EndDate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day")
    private Days day;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "end_date_time_slot",
               joinColumns = @JoinColumn(name="end_dates_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="time_slots_id", referencedColumnName="ID"))
    private Set<TimeSlot> timeSlots = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "end_date_class_room_session",
               joinColumns = @JoinColumn(name="end_dates_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="class_room_sessions_id", referencedColumnName="ID"))
    private Set<ClassRoomSession> classRoomSessions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "end_date_section",
               joinColumns = @JoinColumn(name="end_dates_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="sections_id", referencedColumnName="ID"))
    private Set<Section> sections = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Days getDay() {
        return day;
    }

    public void setDay(Days day) {
        this.day = day;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public Set<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(Set<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    public Set<ClassRoomSession> getClassRoomSessions() {
        return classRoomSessions;
    }

    public void setClassRoomSessions(Set<ClassRoomSession> classRoomSessions) {
        this.classRoomSessions = classRoomSessions;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EndDate endDate = (EndDate) o;
        return Objects.equals(id, endDate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EndDate{" +
            "id=" + id +
            ", day='" + day + "'" +
            ", startDate='" + startDate + "'" +
            ", endDate='" + endDate + "'" +
            '}';
    }
}
