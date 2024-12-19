package com.student.web;

import com.student.entities.Student;
import com.student.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/Students")
@RestController
@RequiredArgsConstructor
@CrossOrigin
public class StudentApi {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> saveStudent
            (@RequestPart MultipartFile image , @RequestParam String fullName, @RequestParam String email, @RequestParam int age,
             @RequestParam String phone, @RequestParam String password, @RequestParam String gender) throws IOException {

        Student teacher = Student.builder()
                .fullName(fullName)
                .email(email)
                .age(age)
                .gender(gender)
                .password(password)
                .phoneNumber(phone)
                .build();
        Student savedTeacher = studentService.saveStudent(teacher , image);

        return ResponseEntity.ok(savedTeacher);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Map<String,String>> getByEmail(@PathVariable String email){

        Student student = studentService.findByEmail(email);
        if (student!=null) {
            Map<String, String> infos_user = new HashMap<>();

            infos_user.put("email", student.getEmail());
            infos_user.put("password", student.getPassword());
            infos_user.put("scope", student.getRole());
            return ResponseEntity.ok(infos_user);
        }
        return ResponseEntity.notFound().build();
    }
}
