package org.paolo.springboot;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.persistence.*;
import java.time.LocalDate;

import java.util.Date;

public abstract class Person {

    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

    private String secondName;

    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final Date dateOfBirth;
    private final String gender;


    public Person(String firstName, String lastName, String emailAddress,
                  Date dateOfBirth, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    @JsonProperty(required = true)
    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty(required = true)
    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    @JsonProperty(required = true)
    @Column(nullable = false)
    public String getEmailAddress() {
        return emailAddress;
    }

    @JsonProperty(required = true)
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    public String getDateOfBirth() {
        return DATE_FORMATTER.print(dateOfBirth.getTime());
    }

    @JsonProperty(required = true)
    @Column(nullable = false)
    public String getGender() {
        return gender;
    }

    public String getSecondName() {
        return secondName == null ? "" : secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
