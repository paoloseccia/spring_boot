package org.paolo.springboot;

import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.paolo.springboot.controller.ParentsController;
import org.paolo.springboot.persistence.model.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ParentsController.class)
public class ParentsApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ParentsController parentsController;

	@Test
	public void testCreate() throws Exception {

		final Parent parent = new Parent("Mrs", "Jane", "Doe", "jane.doe@gohenry.co.uk",
				DateTimeFormat.forPattern("yyyy-MM-dddd").parseDateTime("1990-06-03").toDate(), "female");

		when(parentsController.create(parent)).thenReturn(parent);

		mvc.perform(post("/parents", parent))
				.andExpect(status().isOk())
				.andExpect(jsonPath("title").value("Mrs"))
				.andExpect(jsonPath("firstName").value("Jane"))
				.andExpect(jsonPath("lastName").value("Doe"))
				.andExpect(jsonPath("emailAddress").value("jane.doe@gohenry.co.uk"))
				.andExpect(jsonPath("dateOfBirth").value("1990-06-03"))
				.andExpect(jsonPath("gender").value("female"));
	}


	@Test
	public void testUpdateParent() {
		fail();
	}

	@Test
	public void testUpdateChildren() {
		fail();
	}

	@Test
	public void testRead() throws Exception {


		when(parentsController.read(anyLong()))
				.thenReturn(
						new Parent("Mrs", "Jane", "Doe", "jane.doe@gohenry.co.uk",
								DateTimeFormat.forPattern("yyyy-MM-dddd").parseDateTime("1990-06-03").toDate(), "female"));

		mvc.perform(get("/parents/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("title").value("Mrs"))
				.andExpect(jsonPath("firstName").value("Jane"))
				.andExpect(jsonPath("lastName").value("Doe"))
				.andExpect(jsonPath("emailAddress").value("jane.doe@gohenry.co.uk"))
				.andExpect(jsonPath("dateOfBirth").value("1990-06-03"))
				.andExpect(jsonPath("gender").value("female"));


	}

	@Test
	public void testDelete() {
		fail();
	}
}
