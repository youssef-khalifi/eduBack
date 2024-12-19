package com.teacher.services;

import com.teacher.entities.Teacher;
import com.teacher.repositories.TeacherRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepo teacherRepo;

    public Teacher saveTeacher(Teacher teacher , MultipartFile image) throws IOException {

        teacher.setImage(image.getBytes());
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

}
