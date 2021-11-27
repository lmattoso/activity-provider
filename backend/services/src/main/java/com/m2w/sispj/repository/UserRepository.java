package com.m2w.sispj.repository;

import com.m2w.sispj.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByDeletedFalse(Pageable pageable);
    Optional<User> findByEmailAndDeletedFalse(String email);
    Optional<User> findByEmail(String email);

    boolean existsByNameAndDeleted(String email, boolean deleted);
    boolean existsByNameAndIdNotAndDeleted(String email, Long id, boolean deleted);
    boolean existsByEmailAndDeleted(String email, boolean deleted);
    boolean existsByEmailAndIdNotAndDeleted(String email, Long id, boolean deleted);
}