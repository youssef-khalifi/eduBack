package com.teacher.repositories;

import com.teacher.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    Teacher findByEmail(String email);
}
