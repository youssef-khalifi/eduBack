package com.cour.services;

import com.cour.clients.TeacherClient;
import com.cour.clients.VideoClient;
import com.cour.entities.Course;
import com.cour.model.Teacher;
import com.cour.repositories.CourseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {


    private final CourseRepo courseRepo;
    private final TeacherClient teacherClient;
    private final VideoClient videoClient;




    public Course saveCourse(Course course, MultipartFile image ) throws IOException {

        course.setCreatedAt(new Date());
        course.setImage(image.getBytes());
        return courseRepo.save(course);
    }


    public Course updateCourse(Long id, String name, MultipartFile image,String courseType,
                               String courseLevel,String description) throws IOException {


        Optional<Course> existingCourseOpt = courseRepo.findById(id);
        if (existingCourseOpt.isPresent()) {

            Course existingCourse = existingCourseOpt.get();

            existingCourse.setName(name);
            existingCourse.setImage(image.getBytes());
            existingCourse.setDescription(description);
            existingCourse.setCourseLevel(courseLevel);
            existingCourse.setCourType(courseType);

            return courseRepo.save(existingCourse);
        } else {
            throw new IOException("Course with ID " + id + " not found.");
        }
    }


    public void deleteCourse(Long id) throws IOException {
        if (courseRepo.existsById(id)) {

            this.videoClient.deleteVideos(id);
            courseRepo.deleteById(id);
        } else {
            throw new IOException("Course with ID " + id + " not found.");
        }
    }


    public Course getCourseById(Long id) throws IOException {
        return courseRepo.findById(id)
                .orElseThrow(() -> new IOException("Course with ID " + id + " not found."));
    }


    public Course getCoursesByName(String name) {
        Course course = courseRepo.findByNameContainingIgnoreCase(name);
        Teacher teacher = teacherClient.getTeacherById(course.getEnseignantId()).getBody();

        course.setTeacher(teacher);

        return course;
    }

    public List<Course> getByEnseignantId(Long enseignantId) {
        List<Course> courses = courseRepo.findByEnseignantId(enseignantId);

        if (courses.isEmpty()) {
            return null;
        }
        for (Course course : courses) {

            Teacher teacher = teacherClient.getTeacherById(course.getEnseignantId()).getBody();
            course.setTeacher(teacher);
        }
        return courses;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = courseRepo.findAll();

        for (Course course : courses) {
            course.setTeacher(teacherClient.getTeacherById(course.getEnseignantId()).getBody());
        }
        return courses;
    }
}
