package org.paolo.springboot;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
public class Parent extends Person {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private final List<Children> children;
    private final String title;


    /**
     * Unique constructor allowed
     */
    public Parent(String title, String firstName, String lastName, String emailAddress, Date dateOfBirth, String gender) {
        super(firstName, lastName, emailAddress, dateOfBirth, gender);
        this.title = title;
        this.children = new ArrayList<>();
    }


    @JsonProperty(required = true, value = "title")
    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public long id() {
        return id;
    }

    @OneToMany(cascade = CascadeType.REMOVE, targetEntity = Children.class)
    @JsonProperty
    public List<Children> children() {
        return children;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", secondName='" + getSecondName() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", emailAddress='" + getEmailAddress() + '\'' +
                ", dateOfBirth=" + getDateOfBirth() +
                ", gender='" + getGender() + '\'' +
                ", children=" + children +
                '}';

    }
}
