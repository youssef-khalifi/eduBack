package com.student.services;

import com.student.entities.Student;
import com.student.repositories.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo studentRepo;

    public Student saveStudent(Student student ) throws IOException {

        student.setCreatedAt(new Date());
        student.setRole("Student");
        return studentRepo.save(student);
    }


    public Student getStudentById(Long id){

        if (studentRepo.existsById(id)){
            return studentRepo.findById(id).get();
        }
        return null;
    }

    public List<Student> getAllStudents(){
        return studentRepo.findAll();
    }

    public void deleteStudentById(Long id){
        studentRepo.deleteById(id);
    }

    public Student findByEmail(String email){
        return studentRepo.findByEmail(email);
    }

    public Student update
            (Long id , String fullName , MultipartFile image,
             String jobTitle,String university,String phoneNumber, String password) throws IOException {
        Student student = studentRepo.findById(id).get();
        student.setFullName(fullName);
        student.setJobTitle(jobTitle);
        student.setUniversity(university);
        student.setImage(image.getBytes());
        student.setPhoneNumber(phoneNumber);
        student.setPassword(password);
        studentRepo.save(student);
        return student;

    }

}
