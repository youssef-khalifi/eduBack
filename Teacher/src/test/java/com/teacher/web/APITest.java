package com.teacher.web;

import com.teacher.entities.Teacher;
import com.teacher.services.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(API.class)
class APITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Test
    void saveTeacher_ShouldReturnCreatedTeacher() throws Exception {
        Teacher teacher = Teacher.builder()
                .id(1L)
                .fullName("youssef khalifi")
                .email("youssef@gmail.com")
                .build();

        when(teacherService.saveTeacher(any(Teacher.class), any(MockMultipartFile.class)))
                .thenReturn(teacher);

        MockMultipartFile image = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "test".getBytes());

        mockMvc.perform(multipart("/Teachers")
                        .file(image)
                        .param("fullName", "youssef khalifi")
                        .param("email", "youssef@gmail.com")
                        .param("age", "30")
                        .param("phone", "123456789")
                        .param("password", "password")
                        .param("gender", "Male")
                        .param("university", "UUET")
                        .param("jobTitle", "Professor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("youssef khalifi"))
                .andExpect(jsonPath("$.email").value("youssef@gmail.com"));
    }

    @Test
    void getAllTeachers_ShouldReturnList() throws Exception {
        List<Teacher> teachers = Arrays.asList(
                Teacher.builder().id(1L).fullName("teacher 1").build(),
                Teacher.builder().id(2L).fullName("teacher 2").build()
        );

        when(teacherService.getAllTeacher()).thenReturn(teachers);

        mockMvc.perform(get("/Teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("teacher 1"))
                .andExpect(jsonPath("$[1].fullName").value("teacher 2"));
    }

    @Test
    void getByEmail_WhenTeacherExists_ShouldReturnInfos() throws Exception {
        Teacher teacher = Teacher.builder()
                .email("youssef@gmail.com")
                .password("password")
                .role("Teacher")
                .build();

        when(teacherService.findByEmail("youssef@gmail.com")).thenReturn(teacher);

        mockMvc.perform(get("/Teachers/email/youssef@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("youssef@gmail.com"))
                .andExpect(jsonPath("$.scope").value("Teacher"));
    }

    @Test
    void getByEmail_WhenTeacherNotExists_ShouldReturn404() throws Exception {
        when(teacherService.findByEmail("notfound@gmail.com")).thenReturn(null);

        mockMvc.perform(get("/Teachers/email/notfound@gmail.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTeacher_ShouldReturnUpdatedTeacher() throws Exception {
        Teacher updatedTeacher = Teacher.builder()
                .id(1L)
                .fullName("youssef khalifi")
                .build();

        when(teacherService.updateTeacher(eq(1L), any(), any(), any(), any(), any(), any()))
                .thenReturn(updatedTeacher);

        MockMultipartFile image = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "test".getBytes());

        mockMvc.perform(multipart("/Teachers/1")
                        .file(image)
                        .param("fullName", "youssef")
                        .param("jobTitle", "RH")
                        .param("university", "UAE")
                        .param("phoneNumber", "987654321")
                        .param("password", "PASSWORD")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("youssef khalifi"));
    }
}