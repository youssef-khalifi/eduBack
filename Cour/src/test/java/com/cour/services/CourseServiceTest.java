package com.cour.services;

import com.cour.clients.TeacherClient;
import com.cour.clients.VideoClient;
import com.cour.entities.Course;
import com.cour.model.Teacher;
import com.cour.repositories.CourseRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private TeacherClient teacherClient;

    @Mock
    private VideoClient videoClient;

    @InjectMocks
    private CourseService courseService;

    @Test
    void saveCourse_ShouldSaveWithImageAndDate() throws IOException {
        Course course = new Course();
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());
        when(courseRepo.save(any(Course.class))).thenReturn(course);

        Course result = courseService.saveCourse(course, image);

        assertThat(result.getImage()).isEqualTo(image.getBytes());
        assertThat(result.getCreatedAt()).isNotNull();
        verify(courseRepo).save(course);
    }

    @Test
    void updateCourse_ShouldUpdateAllFields() throws IOException {
        Course existingCourse = new Course();
        existingCourse.setId(1L);
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());

        when(courseRepo.findById(1L)).thenReturn(Optional.of(existingCourse));
        when(courseRepo.save(any(Course.class))).thenReturn(existingCourse);

        Course result = courseService.updateCourse(1L, "course name", image, "Info", "Expert", "description");

        assertThat(result.getName()).isEqualTo("course name");
        assertThat(result.getCourType()).isEqualTo("Info");
        assertThat(result.getCourseLevel()).isEqualTo("Expert");
        verify(courseRepo).save(existingCourse);
    }

    @Test
    void deleteCourse_ShouldDeleteVideosAndCourse() throws IOException {
        when(courseRepo.existsById(1L)).thenReturn(true);

        courseService.deleteCourse(1L);

        verify(videoClient).deleteVideos(1L);
        verify(courseRepo).deleteById(1L);
    }

    @Test
    void getCoursesByName_ShouldReturnCourseWithTeacher() {
        Course course = new Course();
        course.setEnseignantId(1L);
        Teacher teacher = new Teacher();

        when(courseRepo.findByNameContainingIgnoreCase("test")).thenReturn(course);
        when(teacherClient.getTeacherById(1L)).thenReturn(ResponseEntity.ok(teacher));

        Course result = courseService.getCoursesByName("test");

        assertThat(result.getTeacher()).isEqualTo(teacher);
    }

    @Test
    void getAllCourses_ShouldReturnCoursesWithTeachers() {
        Course course1 = new Course();
        course1.setEnseignantId(1L);
        Course course2 = new Course();
        course2.setEnseignantId(2L);
        Teacher teacher = new Teacher();

        when(courseRepo.findAll()).thenReturn(Arrays.asList(course1, course2));
        when(teacherClient.getTeacherById(any())).thenReturn(ResponseEntity.ok(teacher));

        List<Course> results = courseService.getAllCourses();

        assertThat(results).hasSize(2);
        assertThat(results).allMatch(c -> c.getTeacher() != null);
    }
}