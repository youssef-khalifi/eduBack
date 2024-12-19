package com.teacher.web;


import com.teacher.entities.Teacher;
import com.teacher.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Teachers")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class API {

    private final TeacherService teacherService;


    @PostMapping
    public ResponseEntity<Teacher> saveTeacher
            (@RequestPart MultipartFile image , @RequestParam String fullName, @RequestParam String email,@RequestParam int age,
             @RequestParam String phone, @RequestParam String password, @RequestParam String gender) throws IOException {

        Teacher teacher = Teacher.builder()
                .fullName(fullName)
                .email(email)
                .age(age)
                .gender(gender)
                .password(password)
                .phoneNumber(phone)
                .build();
        Teacher savedTeacher = teacherService.saveTeacher(teacher , image);

        return ResponseEntity.ok(savedTeacher);
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeacher();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }
    
    @DeleteMapping("/{id}")
    public void deleteTeacherById(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String,String>> getByEmail(@PathVariable String email){

        Teacher teacher = teacherService.findByEmail(email);
        if (teacher!=null) {
            Map<String, String> infos_user = new HashMap<>();

            infos_user.put("email", teacher.getEmail());
            infos_user.put("password", teacher.getPassword());
            infos_user.put("scope", teacher.getRole());
            return ResponseEntity.ok(infos_user);
        }
        return ResponseEntity.notFound().build();
    }
}
