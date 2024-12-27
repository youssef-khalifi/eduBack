package com.video.client;

import com.video.models.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "COURSE-SERVICE")
public interface CourseClient {

    @GetMapping("/Course/{id}")
    Course TeacherById(@PathVariable Long id);
}
