package com.teacher.services;

import com.teacher.clients.CourseClient;
import com.teacher.entities.Teacher;
import com.teacher.models.Course;
import com.teacher.repositories.TeacherRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepo teacherRepo;

    @Mock
    private CourseClient courseClient;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void saveTeacher_ShouldSaveTeacherWithImage() throws Exception {
        Teacher teacher = new Teacher();
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());
        when(teacherRepo.save(any(Teacher.class))).thenReturn(teacher);

        Teacher result = teacherService.saveTeacher(teacher, image);

        assertThat(result).isNotNull();
        assertThat(result.getImage()).isEqualTo(image.getBytes());
        assertThat(result.getRole()).isEqualTo("Teacher");
        verify(teacherRepo).save(teacher);
    }

    @Test
    void getTeacherByEmail_ShouldReturnTeacherWithCourses() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setEmail("youssef@gmail.com");
        List<Course> courses = Arrays.asList(new Course(), new Course());

        when(teacherRepo.findByEmail("youssef@gmail.com")).thenReturn(teacher);
        when(courseClient.TeacherById(1L)).thenReturn(courses);

        Teacher result = teacherService.getTeacherByEmail("youssef@gmail.com");

        assertThat(result.getCourses()).isEqualTo(courses);
        verify(courseClient).TeacherById(1L);
    }

    @Test
    void updateTeacher_ShouldUpdateAllFields() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());

        when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepo.save(any(Teacher.class))).thenReturn(teacher);

        Teacher result = teacherService.updateTeacher(1L, "hassan", image, "Student",
                "UAE", "123456789", "pass");

        assertThat(result.getFullName()).isEqualTo("hassan");
        assertThat(result.getJobTitle()).isEqualTo("Student");
        assertThat(result.getUniversity()).isEqualTo("UAE");
        assertThat(result.getPhoneNumber()).isEqualTo("123456789");
        verify(teacherRepo).save(teacher);
    }

    @Test
    void getAllTeacher_ShouldReturnList() {
        List<Teacher> teachers = Arrays.asList(new Teacher(), new Teacher());
        when(teacherRepo.findAll()).thenReturn(teachers);

        List<Teacher> result = teacherService.getAllTeacher();

        assertThat(result).hasSize(2);
        verify(teacherRepo).findAll();
    }

    @Test
    void deleteTeacherById_ShouldCallRepo() {
        teacherService.deleteTeacherById(1L);
        verify(teacherRepo).deleteById(1L);
    }
}