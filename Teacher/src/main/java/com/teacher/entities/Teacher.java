package com.teacher.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.teacher.models.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor@NoArgsConstructor
@Builder
@Data
public class Teacher {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private int age;
    private String gender;
    private String jobTitle;
    private String university;
    private Date createdAt;
    @Lob
    @Column(length = 10000)
    private byte[] image;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
    @Transient
    List<Course> courses = new ArrayList<>();



}
