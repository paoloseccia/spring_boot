package org.paolo.springboot.persistence.repository;


import org.paolo.springboot.persistence.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentsRepository extends JpaRepository<Parent, Long> { }
