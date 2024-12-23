package com.teacher.clients;

import com.teacher.models.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "COURSE-SERVICE")
public interface CourseClient {

    @GetMapping("/Course/{id}")
    List<Course> TeacherById(@PathVariable Long id);
}
