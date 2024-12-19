package com.student.services;

import com.student.entities.Student;
import com.student.repositories.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepo studentRepo;

    public Student saveStudent(Student student , MultipartFile image) throws IOException {

        student.setImage(image.getBytes());
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

}
