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
@CrossOrigin
public class CourseAPI {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> saveCourse
            (@RequestPart("imageFile") MultipartFile imageFile, @RequestParam String name,@RequestParam String description,
             @RequestParam String createdAt, @RequestParam String courseLevel,@RequestParam String courType,
             @RequestParam Long enseignantId) throws IOException {

        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setCourseLevel(courseLevel);
        course.setCourType(courType);
        course.setEnseignantId(enseignantId);


        Course savedCourse = courseService.saveCourse(course , imageFile , createdAt);

        return ResponseEntity.ok(savedCourse);

    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Course>> getCourseByName(@PathVariable String name) {
        List<Course> courses = courseService.getCoursesByName(name);
        return ResponseEntity.ok(courses);
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

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestPart("course") Course updatedCourse,
            @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            Course updated = courseService.updateCourse(id, updatedCourse, image);
            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found: " + e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) throws IOException {
        courseService.deleteCourse(id);
    }
}
