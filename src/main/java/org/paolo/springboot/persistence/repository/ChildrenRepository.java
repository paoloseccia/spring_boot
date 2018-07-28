package org.paolo.springboot.persistence.repository;

import org.paolo.springboot.persistence.model.Children;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildrenRepository extends JpaRepository<Children, Long> { }
