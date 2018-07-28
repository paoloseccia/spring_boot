package org.paolo.springboot.controller;

import org.paolo.springboot.persistence.model.Parent;
import org.paolo.springboot.persistence.model.Person;
import org.paolo.springboot.persistence.repository.ParentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class ParentsController {

    @Autowired
    private ParentsRepository mParentsRepository;

    @PostMapping("/parents")
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody final Parent parent) {
        return mParentsRepository.save(parent);
    }


    @GetMapping("/parents/{id}")
    public Person read(@PathVariable("id") long id) {
        return mParentsRepository.getOne(id);
    }

}
