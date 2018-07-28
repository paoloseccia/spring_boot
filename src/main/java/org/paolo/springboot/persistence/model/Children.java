package org.paolo.springboot.persistence.model;


import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

@Entity
@SequenceGenerator(name = "idgen", sequenceName = "childrenseq")
public class Children extends Person {

    @ManyToOne(fetch = LAZY)
    private Parent parent;

    /**
     * Unique constructor allowed
     */
    public Children(String firstName, String lastName, String emailAddress, Date dateOfBirth, String gender) {
        super(firstName, lastName, emailAddress, dateOfBirth, gender);
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "Children{" +
                "id=" + getId() +
                ", secondName='" + getSecondName() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", emailAddress='" + getEmailAddress() + '\'' +
                ", dateOfBirth=" + getDateOfBirth() +
                ", gender='" + getGender() + '\'' +
                '}';

    }
}