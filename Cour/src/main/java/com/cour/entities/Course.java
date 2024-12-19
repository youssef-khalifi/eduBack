package com.cour.entities;


import com.cour.model.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@AllArgsConstructor@NoArgsConstructor@Data
public class Course {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Transient
    private Teacher teacher;



}
