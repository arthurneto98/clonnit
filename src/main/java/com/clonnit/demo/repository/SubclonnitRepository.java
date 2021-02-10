package com.clonnit.demo.repository;

import com.clonnit.demo.model.Subclonnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubclonnitRepository extends JpaRepository<Subclonnit, Integer> {
}
