package com.video.models;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data@AllArgsConstructor@NoArgsConstructor
public class Course {
    private Long id;
    private String name;
    private String description;
    @Lob
    @Column(length = 10000)
    private byte[] image;
    private Date createdAt;
    private String courseLevel;
    private String courType;
    private Long enseignantId;
}
