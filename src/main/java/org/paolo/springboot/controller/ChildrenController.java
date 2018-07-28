package org.paolo.springboot.controller;

import org.paolo.springboot.exception.IDMismatchException;
import org.paolo.springboot.exception.NotFoundException;
import org.paolo.springboot.persistence.model.Children;
import org.paolo.springboot.persistence.repository.ChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChildrenController {

    @Autowired
    private ChildrenRepository mChildrenRepository;

    @PutMapping("/children/{id}")
    public Children update(@RequestBody final Children children, @PathVariable("id") long id) {

        if(children.getId() != id) {
            throw new IDMismatchException();
        }
        mChildrenRepository.findById(id).orElseThrow(NotFoundException::new);

        return mChildrenRepository.save(children);
    }
}
