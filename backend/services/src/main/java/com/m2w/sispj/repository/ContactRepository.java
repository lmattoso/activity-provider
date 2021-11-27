package com.m2w.sispj.repository;

import com.m2w.sispj.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);
}