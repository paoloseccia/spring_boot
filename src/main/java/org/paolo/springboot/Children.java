package org.paolo.springboot;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Children extends Person {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;


    /**
     * Unique constructor allowed
     */
    public Children(String firstName, String lastName, String emailAddress, Date dateOfBirth, String gender) {
        super(firstName, lastName, emailAddress, dateOfBirth, gender);
    }

    public long id() {
        return id;
    }
}