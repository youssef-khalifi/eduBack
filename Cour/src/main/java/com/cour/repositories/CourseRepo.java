package com.cour.repositories;

import com.cour.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course , Long> {

    List<Course> findByNameContainingIgnoreCase(String name);
    List<Course> findByEnseignantId(Long id);
}
