package com.cour.web;

import com.cour.entities.Course;
import com.cour.services.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Blob;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseAPI.class)
class CourseAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void saveCourse_ShouldReturnCreatedCourse() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setName("Math 101");
        course.setDescription("Basic Math course");
        course.setCourseLevel("Beginner");
        course.setCourType("Online");
        course.setEnseignantId(1L);

        when(courseService.saveCourse(any(Course.class), any(MockMultipartFile.class)))
                .thenReturn(course);

        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile", "test.jpg", "image/jpeg", "test image content".getBytes());

        mockMvc.perform(multipart("/Course/{teacherId}", 1L)
                        .file(imageFile)
                        .param("name", "Math 101")
                        .param("description", "Basic Math course")
                        .param("courseLevel", "Beginner")
                        .param("courType", "Online"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Math 101"))
                .andExpect(jsonPath("$.description").value("Basic Math course"));
    }

    @Test
    void getAllCourses_ShouldReturnList() throws Exception {

        Course cour1 = Course.builder()
                .id(1L)
                .name("Math 101")
                .description("Basic Math course")
                .courseLevel("Beginner")
                .courType("Beginner")
                .enseignantId(1l)
                .build();
        Course cour2 = Course.builder()
                .id(1L)
                .name("Physics 101")
                .description("Basic Physics course")
                .courseLevel("Intermediate")
                .courType("Beginner")
                .enseignantId(1l)
                .build();

        List<Course> courses = Arrays.asList(
                cour1,cour2


        );

        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/Course"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Math 101"))
                .andExpect(jsonPath("$[1].name").value("Physics 101"));
    }

    @Test
    void getById_WhenCourseExists_ShouldReturnCourse() throws Exception {
        Course cour = Course.builder()
                .id(1L)
                .name("Math 101")
                .description("Basic Physics course")
                .courseLevel("Intermediate")
                .courType("Beginner")
                .enseignantId(1l)
                .build();

        when(courseService.getCourseById(1L)).thenReturn(cour);

        mockMvc.perform(get("/Course/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Math 101"))
                .andExpect(jsonPath("$.description").value("Basic Physics course"));
    }


    @Test
    void updateCourse_ShouldReturnUpdatedCourse() throws Exception {

        Course updatedCourse = Course.builder()
                .id(1L)
                .name("Advanced Math")
                .description("Advanced level Math course")
                .courseLevel("Advanced")
                .courType("Beginner")
                .enseignantId(1l)
                .build();

        when(courseService.updateCourse(eq(1L), any(), any(), any(), any(), any()))
                .thenReturn(updatedCourse);

        MockMultipartFile imageFile = new MockMultipartFile(
                "imageFile", "test.jpg", "image/jpeg", "test image content".getBytes());

        mockMvc.perform(multipart("/Course/{id}", 1L)
                        .file(imageFile)
                        .param("name", "Advanced Math")
                        .param("description", "Advanced level Math course")
                        .param("courseLevel", "Advanced")
                        .param("courType", "Online")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Advanced Math"))
                .andExpect(jsonPath("$.description").value("Advanced level Math course"));
    }

    @Test
    void getCoursesByTeacherId_ShouldReturnList() throws Exception {
        Course cour1 = Course.builder()
                .id(1L)
                .name("Math 101")
                .description("Basic Math course")
                .courseLevel("Beginner")
                .courType("Beginner")
                .enseignantId(1l)
                .build();
        Course cour2 = Course.builder()
                .id(1L)
                .name("Physics 101")
                .description("Basic Physics course")
                .courseLevel("Intermediate")
                .courType("Beginner")
                .enseignantId(1l)
                .build();
        List<Course> courses = Arrays.asList(
                cour1,cour2
        );

        when(courseService.getByEnseignantId(1L)).thenReturn(courses);

        mockMvc.perform(get("/Course/teacher/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Math 101"))
                .andExpect(jsonPath("$[1].name").value("Physics 101"));
    }
}
