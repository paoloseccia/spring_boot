package org.paolo.springboot;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.paolo.springboot.persistence.model.Children;
import org.paolo.springboot.persistence.model.Parent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ParentsApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ParentsApplicationTests {


	@LocalServerPort
	private int mPort;

	@Test
	public void whenCreateNewParent_thenCreated() throws Exception {

		final String apiRoot = "http://localhost:" + mPort + "/parents";

		final Parent parent = new Parent("Mrs", "Jane", "Doe", "jane.doe@gohenry.co.uk",
				DateTimeFormat.forPattern("yyyy-MM-dddd").parseDateTime("1990-06-03").toDate(), "female");

		parent.addChildren(new Children("Will", "Ross",
				"will.ross@gohenry.co.uk",
				DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2010-03-02").toDate(), "male"));


		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(parent)
				.post(apiRoot);

		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());

		final Parent actualParent = response.getBody().as(Parent.class);

		assertEquals("Mrs", actualParent.getTitle());
		assertEquals("Jane", actualParent.getFirstName());
		assertEquals("Doe", actualParent.getLastName());
		assertEquals("jane.doe@gohenry.co.uk", actualParent.getEmailAddress());
		assertEquals("1990-06-03", DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime(actualParent.getDateOfBirth().getTime())));
		assertEquals("female", actualParent.getGender());
		assertEquals(1, actualParent.getChildren().size());

		final Children actualChildren = actualParent.getChildren().get(0);

		assertEquals("Will", actualChildren.getFirstName());
		assertEquals("Ross", actualChildren.getLastName());
		assertEquals("will.ross@gohenry.co.uk", actualChildren.getEmailAddress());
		assertEquals("2010-03-02", DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime(actualChildren.getDateOfBirth().getTime())));
		assertEquals("male", actualChildren.getGender());
	}

	@Test
	public void whenInvalidNewParent_thenError() throws Exception {
		final String apiRoot = "http://localhost:" + mPort + "/parents";

		final Parent parent = new Parent();

		parent.setTitle(null);

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(parent)
				.post(apiRoot);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}

	@Test
	public void whenUpdateParent_thenUpdated() {
		final Parent parentToUpdate = createParent();

		parentToUpdate.setSecondName("Joanna");

		parentToUpdate.getChildren().get(0).setSecondName("Matt");

		final String apiRoot = "http://localhost:" + mPort + "/parents/" + parentToUpdate.getId();

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(parentToUpdate)
				.put(apiRoot);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());

		final Parent actualParent = response.getBody().as(Parent.class);

		assertEquals("Mrs", actualParent.getTitle());
		assertEquals("Jane", actualParent.getFirstName());
		assertEquals("Doe", actualParent.getLastName());
		assertEquals("jane.doe@gohenry.co.uk", actualParent.getEmailAddress());
		assertEquals("1990-06-03", DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime(actualParent.getDateOfBirth().getTime())));
		assertEquals("female", actualParent.getGender());
		assertEquals("Joanna", actualParent.getSecondName());
		assertEquals(1, actualParent.getChildren().size());

		final Children actualChildren = actualParent.getChildren().get(0);

		assertEquals("Will", actualChildren.getFirstName());
		assertEquals("Ross", actualChildren.getLastName());
		assertEquals("will.ross@gohenry.co.uk", actualChildren.getEmailAddress());
		assertEquals("2010-03-02", DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime(actualChildren.getDateOfBirth().getTime())));
		assertEquals("male", actualChildren.getGender());
		assertEquals("Matt", actualChildren.getSecondName());
	}

	@Test
	public void whenUpdateMismatchParentId_thenError() {
		final Parent parentToUpdate = createParent();

		final String apiRoot = "http://localhost:" + mPort + "/parents/34";

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(parentToUpdate)
				.put(apiRoot);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}

	@Test
	public void whenUpdateInvalidParentId_thenNotFound() {
		final Parent parentToUpdate = new Parent("Mrs", "Jane", "Doe", "jane.doe@gohenry.co.uk",
				DateTimeFormat.forPattern("yyyy-MM-dddd").parseDateTime("1990-06-03").toDate(), "female");


		final String apiRoot = "http://localhost:" + mPort + "/parents/0";

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(parentToUpdate)
				.put(apiRoot);

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}

	@Test
	public void whenUpdateChildren_thenOk() {

		final Children childrenToUpdate = createParent().getChildren().get(0);

		childrenToUpdate.setSecondName("Matt");

		final String apiRoot = "http://localhost:" + mPort + "/children/" + childrenToUpdate.getId();

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(childrenToUpdate)
				.put(apiRoot);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());

		final Children actualChildren = response.getBody().as(Children.class);

		assertEquals("Will", actualChildren.getFirstName());
		assertEquals("Ross", actualChildren.getLastName());
		assertEquals("will.ross@gohenry.co.uk", actualChildren.getEmailAddress());
		assertEquals("2010-03-02", DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime(actualChildren.getDateOfBirth().getTime())));
		assertEquals("male", actualChildren.getGender());
		assertEquals("Matt", actualChildren.getSecondName());
	}

	@Test
	public void whenUpdateMismatchChildrenId_thenError() {

		final Children childrenToUpdate = createParent().getChildren().get(0);

		final String apiRoot = "http://localhost:" + mPort + "/children/23";

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(childrenToUpdate)
				.put(apiRoot);

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
	}

	@Test
	public void whenUpdateInvalidChildrenId_thenNotFound() {

		final Children childrenToUpdate = new Children("Will", "Ross",
				"will.ross@gohenry.co.uk",
				DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2010-03-02").toDate(), "male");

		final String apiRoot = "http://localhost:" + mPort + "/children/0";

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(childrenToUpdate)
				.put(apiRoot);

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}

	@Test
	public void whenGetParentById_thenOK() throws Exception {

		long id = createParent().getId();

		final String apiRoot = "http://localhost:" + mPort + "/parents/" + id;

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.get(apiRoot);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());

		final Parent actualParent = response.getBody().as(Parent.class);

		assertEquals("Mrs", actualParent.getTitle());
		assertEquals("Jane", actualParent.getFirstName());
		assertEquals("Doe", actualParent.getLastName());
		assertEquals("jane.doe@gohenry.co.uk", actualParent.getEmailAddress());
		assertEquals("1990-06-03", DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime(actualParent.getDateOfBirth().getTime())));
		assertEquals("female", actualParent.getGender());
		assertEquals(1, actualParent.getChildren().size());

		final Children actualChildren = actualParent.getChildren().get(0);

		assertEquals("Will", actualChildren.getFirstName());
		assertEquals("Ross", actualChildren.getLastName());
		assertEquals("will.ross@gohenry.co.uk", actualChildren.getEmailAddress());
		assertEquals("2010-03-02", DateTimeFormat.forPattern("yyyy-MM-dd").print(new DateTime(actualChildren.getDateOfBirth().getTime())));
		assertEquals("male", actualChildren.getGender());

	}

	@Test
	public void whenGetInvalidParentById_thenNotFound() throws Exception {

		final String apiRoot = "http://localhost:" + mPort + "/parents/1";

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.get(apiRoot);

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}

	@Test
	public void whenDeleteParent_thenOK() {
		final Parent parentToDelete = createParent();

		final String apiRoot = "http://localhost:" + mPort + "/parents/" + parentToDelete.getId();

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.delete(apiRoot);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
	}

	@Test
	public void whenDeleteInvalidParentId_thenNotFound() {

		final String apiRoot = "http://localhost:" + mPort + "/parents/1";

		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.delete(apiRoot);

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}


	// private methods
	private Parent createParent() {
		final String apiRoot = "http://localhost:" + mPort + "/parents";

		final Parent parent = new Parent("Mrs", "Jane", "Doe", "jane.doe@gohenry.co.uk",
				DateTimeFormat.forPattern("yyyy-MM-dddd").parseDateTime("1990-06-03").toDate(), "female");

		parent.addChildren(new Children("Will", "Ross",
				"will.ross@gohenry.co.uk",
				DateTimeFormat.forPattern("yyyy-MM-dd").parseDateTime("2010-03-02").toDate(), "male"));


		Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.body(parent)
				.post(apiRoot);

		return response.getBody().as(Parent.class);
	}
}
