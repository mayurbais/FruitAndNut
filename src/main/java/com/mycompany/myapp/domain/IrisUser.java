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

import com.mycompany.myapp.domain.enumeration.Gender;

import com.mycompany.myapp.domain.enumeration.BloodGroup;

/**
 * A IrisUser.
 */
@Entity
@Table(name = "iris_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IrisUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 10)
    @Column(name = "first_name", length = 10, nullable = false)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 10)
    @Column(name = "last_name", length = 10, nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    /**
     * <Enter note text here>
     */
    @ApiModelProperty(value = ""
        + "<Enter note text here>")
    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group")
    private BloodGroup bloodGroup;

    @Column(name = "birth_place")
    private String birthPlace;

    @Column(name = "religion")
    private String religion;

    @Column(name = "photo")
    private String photo;

    @Column(name = "phone")
    private Long phone;

    @OneToOne
    private User user;

    @OneToOne
    private LanguageLOV languageLOV;

    @OneToOne
    private CountryLOV countryLOV;

    @OneToOne
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
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

    public CountryLOV getCountryLOV() {
        return countryLOV;
    }

    public void setCountryLOV(CountryLOV countryLOV) {
        this.countryLOV = countryLOV;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IrisUser irisUser = (IrisUser) o;
        return Objects.equals(id, irisUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IrisUser{" +
            "id=" + id +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", middleName='" + middleName + "'" +
            ", dateOfBirth='" + dateOfBirth + "'" +
            ", gender='" + gender + "'" +
            ", bloodGroup='" + bloodGroup + "'" +
            ", birthPlace='" + birthPlace + "'" +
            ", religion='" + religion + "'" +
            ", photo='" + photo + "'" +
            ", phone='" + phone + "'" +
            '}';
    }
}
