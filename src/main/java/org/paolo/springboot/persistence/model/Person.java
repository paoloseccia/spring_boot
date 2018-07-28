package org.paolo.springboot.persistence.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class Person {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgen")
    private long id;

    @Column
    private String secondName;

    @JsonProperty(required = true)
    @Column(nullable = false)
    private String firstName;

    @JsonProperty(required = true)
    @Column(nullable = false)
    private String lastName;

    @JsonProperty(required = true)
    @Column(nullable = false)
    private String emailAddress;

    @JsonProperty(required = true)
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateOfBirth;

    @JsonProperty(required = true)
    @Column(nullable = false)
    private String gender;

    public Person() { }

    public Person(String firstName, String lastName, String emailAddress,
                  Date dateOfBirth, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getSecondName() {
        return secondName == null ? "" : secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
