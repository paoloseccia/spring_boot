package org.paolo.springboot.persistence.repository;

import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.paolo.springboot.persistence.model.Children;
import org.paolo.springboot.persistence.model.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ParentsRepositoryTest {

    @Autowired
    private ParentsRepository mRepository;

    @Autowired
    private TestEntityManager mEntityManager;


    //===


    @Before
    public void setUp() throws Exception {

        final Parent parent = new Parent("Mrs", "Jane", "Doe",
                "jane.doe@gohenry.co.uk",
                DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("1990-06-30").toDate(), "female");

        final Parent parentWithChildren = new Parent("Mr", "Mike", "Ross",
                "mike.ross@gohenry.co.uk",
                DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("1978-05-04").toDate(), "male");

        parentWithChildren.setSecondName("Philip");
        parentWithChildren.addChildren(new Children("Will", "Ross",
                "will.ross@gohenry.co.uk",
                DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2010-03-02").toDate(), "male"));

        mEntityManager.persist(parent);
        mEntityManager.persist(parentWithChildren);

        mEntityManager.flush();
    }

    @Test
    public void whenFindById_thenReturnParentWithNoChildren() {

        final Parent actualParent = mRepository.findById(1L).orElse(null);

        assertThat(actualParent.getId()).isEqualTo(1);
        assertThat(actualParent.getTitle()).isEqualTo("Mrs");
        assertThat(actualParent.getFirstName()).isEqualTo("Jane");
        assertThat(actualParent.getLastName()).isEqualTo("Doe");
        assertThat(actualParent.getDateOfBirth()).isEqualTo(DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("1990-06-30").toDate());
        assertThat(actualParent.getEmailAddress()).isEqualTo("jane.doe@gohenry.co.uk");
        assertThat(actualParent.getGender()).isEqualTo("female");
        assertThat(actualParent.getSecondName()).isEqualTo("");
        assertThat(actualParent.getChildren()).isEmpty();

    }

    @Test
    public void whenFindById_thenReturnParentWithChildren() {

        final Parent actualParent = mRepository.findById(2L).orElse(null);

        assertThat(actualParent.getId()).isEqualTo(2);
        assertThat(actualParent.getTitle()).isEqualTo("Mr");
        assertThat(actualParent.getFirstName()).isEqualTo("Mike");
        assertThat(actualParent.getLastName()).isEqualTo("Ross");
        assertThat(actualParent.getDateOfBirth()).isEqualTo(DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("1978-05-04").toDate());
        assertThat(actualParent.getEmailAddress()).isEqualTo("mike.ross@gohenry.co.uk");
        assertThat(actualParent.getGender()).isEqualTo("male");
        assertThat(actualParent.getSecondName()).isEqualTo("Philip");
        assertThat(actualParent.getChildren()).isNotEmpty();
        assertThat(actualParent.getChildren().size()).isEqualTo(1);

        final Children actualChildren = actualParent.getChildren().get(0);
        assertThat(actualChildren.getId()).isEqualTo(1);
        assertThat(actualChildren.getFirstName()).isEqualTo("Will");
        assertThat(actualChildren.getLastName()).isEqualTo("Ross");
        assertThat(actualChildren.getDateOfBirth()).isEqualTo(DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2010-03-02").toDate());
        assertThat(actualChildren.getEmailAddress()).isEqualTo("will.ross@gohenry.co.uk");
        assertThat(actualChildren.getGender()).isEqualTo("male");

    }
}