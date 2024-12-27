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
public class StudentApi {

    private final StudentService studentService;

    @PostMapping("/save")
    public ResponseEntity<Student> saveStudent
            (@RequestParam String fullName, @RequestParam String email, @RequestParam int age,
             @RequestParam String password, @RequestParam String gender) throws IOException {

        Student teacher = Student.builder()
                .fullName(fullName)
                .email(email)
                .age(age)
                .gender(gender)
                .password(password)
                .build();
        Student saved = studentService.saveStudent(teacher);

        return ResponseEntity.ok(saved);
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


    @GetMapping("/profile/{email}")
    public ResponseEntity<Student> getTeacherByEmail(@PathVariable String email){
        return ResponseEntity.ok(studentService.findByEmail(email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateTeacher(
            @PathVariable Long id,
            @RequestParam String fullName,
            @RequestParam String jobTitle,
            @RequestParam String university,
            @RequestParam String phoneNumber,
            @RequestParam String password,
            @RequestParam("image") MultipartFile image) throws IOException {

        Student student = studentService.update
                (id,fullName,image,jobTitle,university,phoneNumber,password);

        return ResponseEntity.ok(student);

    }
}
