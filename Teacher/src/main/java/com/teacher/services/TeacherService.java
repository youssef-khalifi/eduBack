package com.teacher.services;

import com.teacher.clients.CourseClient;
import com.teacher.entities.Teacher;
import com.teacher.models.Course;
import com.teacher.repositories.TeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepo teacherRepo;
    private final CourseClient courseClient;

    public Teacher saveTeacher(Teacher teacher , MultipartFile image) throws IOException {

        teacher.setImage(image.getBytes());
        teacher.setCreatedAt(new Date());
        teacher.setRole("Teacher");
        return teacherRepo.save(teacher);
    }

    public Teacher getTeacherById(Long id){
        return teacherRepo.findById(id).get();
    }

    public List<Teacher> getAllTeacher(){
        return teacherRepo.findAll();
    }

    public void deleteTeacherById(Long id){
        teacherRepo.deleteById(id);
    }

    public Teacher findByEmail(String email){
        return teacherRepo.findByEmail(email);
    }

    public Teacher getTeacherByEmail(String email){
        Teacher teacher = teacherRepo.findByEmail(email);
        List<Course> courses = courseClient.TeacherById(teacher.getId());

        teacher.setCourses(courses);

        return teacher;
    }

    public Teacher updateTeacher
            (Long id , String fullName , MultipartFile image,
             String jobTitle,String university,String phoneNumber, String password) throws IOException {
        Teacher teacher = teacherRepo.findById(id).get();
        teacher.setFullName(fullName);
        teacher.setJobTitle(jobTitle);
        teacher.setUniversity(university);
        teacher.setImage(image.getBytes());
        teacher.setPhoneNumber(phoneNumber);
        teacher.setPassword(password);
        teacherRepo.save(teacher);
        return teacher;

    }

}
