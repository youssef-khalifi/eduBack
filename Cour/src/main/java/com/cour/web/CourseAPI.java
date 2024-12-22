package com.cour.web;


import com.cour.entities.Course;
import com.cour.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RequestMapping("/Course")
@RestController
public class CourseAPI {

    @Autowired
    private CourseService courseService;

    @PostMapping("/{teacherId}")
    public ResponseEntity<Course> saveCourse
            (@RequestParam("imageFile") MultipartFile imageFile, @RequestParam String name,@RequestParam String description,
             @RequestParam String courseLevel,@RequestParam String courType,
             @PathVariable Long teacherId) throws IOException {

        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setCourseLevel(courseLevel);
        course.setCourType(courType);
        course.setEnseignantId(teacherId);


        Course savedCourse = courseService.saveCourse(course , imageFile );

        return ResponseEntity.ok(savedCourse);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse
            (@RequestParam("imageFile") MultipartFile imageFile, @RequestParam String name,@RequestParam String description,
             @RequestParam String courseLevel,@RequestParam String courType,@PathVariable Long id
             ) throws IOException {




        Course savedCourse = courseService.updateCourse(id ,name, imageFile, description, courseLevel, courType );

        return ResponseEntity.ok(savedCourse);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Course> getCourseByName(@PathVariable String name) {
        Course course = courseService.getCoursesByName(name);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<Course>> getCoursesByEnseignantId(@PathVariable Long id) {
        List<Course> courses = courseService.getByEnseignantId(id);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) throws IOException {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) throws IOException {
        courseService.deleteCourse(id);
    }
}
