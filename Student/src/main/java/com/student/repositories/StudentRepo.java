package com.student.repositories;

import com.student.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student, Long> {

    Student findByEmail(String email);
}
