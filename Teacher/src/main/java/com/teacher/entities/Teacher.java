package com.teacher.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JsonIgnore
    @Lob
    @Column(length = 10000)
    private byte[] image;
    private String email;
    private String password;
    private String phoneNumber;
    private String role;



}
