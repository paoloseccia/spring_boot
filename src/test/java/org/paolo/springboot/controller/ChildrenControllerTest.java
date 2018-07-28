package org.paolo.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.paolo.springboot.exception.NotFoundException;
import org.paolo.springboot.persistence.model.Children;
import org.paolo.springboot.persistence.repository.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ChildrenController.class)
public class ChildrenControllerTest {


    @Autowired
    private MockMvc mMockMvc;

    @MockBean
    private ChildrenRepository mChildrenRepository;



    @Test
    public void whenPut_thenIsOk() throws Exception {

        final Children children = new Children("Will", "Bow", "will.bow@gohenry.co.uk",
                DateTimeFormat.forPattern("yyyy-MM-dddd").parseDateTime("2001-06-03").toDate(), "male");

        children.setId(1L);

        when(mChildrenRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(children));

        ObjectMapper mapper = new ObjectMapper();

        mMockMvc.perform(put("/children/1")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writer().writeValueAsString(children)))
                .andExpect(status().isOk());

    }

    @Test
    public void whenPut_thenReturnNotFound() throws Exception {

        final Children children = new Children("Will", "Bow", "will.bow@gohenry.co.uk",
                DateTimeFormat.forPattern("yyyy-MM-dddd").parseDateTime("2001-06-03").toDate(), "male");
        children.setId(1L);

        when(mChildrenRepository.findById(1L)).thenThrow(new NotFoundException());

        ObjectMapper mapper = new ObjectMapper();

        mMockMvc.perform(put("/children/1")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writer().writeValueAsString(children)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Not found"));

    }

    @Test
    public void whenPut_thenIsBadRequest() throws Exception {

        final Children children = new Children("Will", "Bow", "will.bow@gohenry.co.uk",
                DateTimeFormat.forPattern("yyyy-MM-dddd").parseDateTime("2001-06-03").toDate(), "male");
        children.setId(1L);
        when(mChildrenRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(children));

        ObjectMapper mapper = new ObjectMapper();

        mMockMvc.perform(put("/children/2")
                .contentType(MediaType.APPLICATION_JSON).content(mapper.writer().writeValueAsString(children)))
                .andExpect(status().isBadRequest());

    }
}