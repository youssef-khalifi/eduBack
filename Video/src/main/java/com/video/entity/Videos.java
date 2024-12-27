package com.video.entity;


import com.video.models.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="video")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Videos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	private String description;
	private String tags;
	private String videoName;
	private Date addedDate;
	private Long courseId;
	@Transient
	private Course course;

}

