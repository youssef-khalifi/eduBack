package com.teacher.repositories;

import com.teacher.entities.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TeacherRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeacherRepo teacherRepo;

    @Test
    void findByEmail_ShouldReturnTeacher_WhenEmailExists() {
        Teacher teacher = new Teacher();
        teacher.setEmail("youssef@gmail.com");
        teacher.setFullName("youssef khalifi");
        entityManager.persist(teacher);
        entityManager.flush();


        Teacher found = teacherRepo.findByEmail("youssef@gmail.com");

        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("youssef@gmail.com");
        assertThat(found.getFullName()).isEqualTo("youssef khalifi");
    }

    @Test
    void findByEmail_ShouldReturnNull_WhenEmailDoesNotExist() {

        Teacher found = teacherRepo.findByEmail("test@gmail.com");

        assertThat(found).isNull();
    }
}