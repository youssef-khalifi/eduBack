package com.cour.repositories;

import com.cour.entities.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CourseRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CourseRepo courseRepo;

    @Test
    void findByNameContainingIgnoreCase_ShouldFindCourse() {
        Course course = new Course();
        course.setName("Java Programming");
        entityManager.persist(course);
        entityManager.flush();

        Course found = courseRepo.findByNameContainingIgnoreCase("java");

        assertThat(found.getName()).isEqualTo("Java Programming");
    }

    @Test
    void findByEnseignantId_ShouldReturnCourses() {
        Course course1 = new Course();
        course1.setEnseignantId(1L);
        Course course2 = new Course();
        course2.setEnseignantId(1L);
        Course course3 = new Course();
        course3.setEnseignantId(2L);

        entityManager.persist(course1);
        entityManager.persist(course2);
        entityManager.persist(course3);
        entityManager.flush();

        List<Course> found = courseRepo.findByEnseignantId(1L);

        assertThat(found).hasSize(2);
        assertThat(found).allMatch(c -> c.getEnseignantId().equals(1L));
    }
}