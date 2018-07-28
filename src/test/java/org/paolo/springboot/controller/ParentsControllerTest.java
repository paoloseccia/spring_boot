package org.paolo.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.paolo.springboot.persistence.model.Parent;
import org.paolo.springboot.persistence.repository.ParentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ParentsController.class)
public class ParentsControllerTest {


    @Autowired
    private MockMvc mMockMvc;

    @MockBean
    private ParentsRepository mParentsRepository;



    @Test
    public void whenCreateNewParent_thenCreated() throws Exception {

        final Parent parent = new Parent("Mrs", "Jane", "Doe", "jane.doe@gohenry.co.uk",
                DateTimeFormat.forPattern("yyyy-MM-dddd").parseDateTime("1990-06-03").toDate(), "female");

        when(mParentsRepository.save(parent)).thenReturn(parent);

        ObjectMapper mapper = new ObjectMapper();

        mMockMvc.perform(post("/parents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writer().writeValueAsString(parent)))
                .andExpect(status().isCreated());

    }

    @Test
    public void whenRead_thenReturnParent() throws Exception {

        final Parent parent = new Parent("Mrs", "Jane", "Doe", "jane.doe@gohenry.co.uk",
                DateTimeFormat.forPattern("yyyy-MM-dddd").parseDateTime("1990-06-03").toDate(), "female");

        when(mParentsRepository.getOne(1L)).thenReturn(parent);


        mMockMvc.perform(get("/parents/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}