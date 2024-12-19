package com.cour.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class Teacher {
    private String fullName;
    private int age;
    private String gender;
    private byte[] image;
    private String email;
    private String phoneNumber;
}
