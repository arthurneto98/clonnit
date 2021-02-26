package com.clonnit.demo.repository;

import com.clonnit.demo.model.Subclonnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubclonnitRepository extends JpaRepository<Subclonnit, Integer> {
    Optional<Subclonnit> findByName(String subclonnitName);
}