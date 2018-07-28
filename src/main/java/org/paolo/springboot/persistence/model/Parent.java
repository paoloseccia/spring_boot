package org.paolo.springboot.persistence.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@SequenceGenerator(name = "idgen", sequenceName = "parentseq")
public class Parent extends Person {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty
    private final List<Children> children;

    @JsonProperty(required = true)
    @Column(nullable = false)
    private final String title;


    /**
     * Unique constructor allowed
     */
    public Parent(String title, String firstName, String lastName, String emailAddress, Date dateOfBirth, String gender) {
        super(firstName, lastName, emailAddress, dateOfBirth, gender);
        this.title = title;
        this.children = new ArrayList<>();
    }


    public String getTitle() {
        return title;
    }

    public List<Children> getChildren() {
        return children;
    }

    public void addChildren(final Children children) {
        this.children.add(children);
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + getId() +
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
