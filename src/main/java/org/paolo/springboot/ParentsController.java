package org.paolo.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ParentsController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ParentsController.class);


    @RequestMapping(method = RequestMethod.POST, value = "/parents")
    @ResponseBody
    public Person create(final Parent parent) {

        LOGGER.info("Saving parent  {}", parent);

        return null;
    }


    @RequestMapping(method = RequestMethod.GET, value = "/parents/{id}")
    @ResponseBody
    public Person read(@PathVariable("id") long id) {

        LOGGER.info("Reading parent with id {}", id);

        return null;
    }

}
