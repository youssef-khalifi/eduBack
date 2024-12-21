package com.cour.model;


import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class Teacher {
    private Long id;
    private String fullName;
    private int age;
    private String gender;
    private String jobTitle;
    private String university;
    @Lob
    @Column(length = 10000)
    private byte[] image;
    private String email;
    private String password;
    private String phoneNumber;
}
