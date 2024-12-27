package com.teacher.web;

import com.teacher.entities.Teacher;
import com.teacher.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class APIGraphQl {

    private final TeacherService teacherService;

    @QueryMapping
    public Teacher getTeacherById(@Argument Long id) {
        return teacherService.getTeacherById(id);
    }

    @QueryMapping
    public Teacher getTeacherByEmail(@Argument String email) {
        return teacherService.getTeacherByEmail(email);
    }

    @QueryMapping
    public Map<String, String> getByEmail(@Argument String email) {
        Teacher teacher = teacherService.findByEmail(email);
        if (teacher != null) {
            Map<String, String> infos_user = new HashMap<>();
            infos_user.put("email", teacher.getEmail());
            infos_user.put("password", teacher.getPassword());
            infos_user.put("scope", teacher.getRole());
            return infos_user;
        }
        return null;
    }

    @MutationMapping
    public Boolean deleteTeacherById(@Argument Long id) {
        teacherService.deleteTeacherById(id);
        return true;
    }

    @MutationMapping
    public Teacher saveTeacher(
            @Argument String fullName,
            @Argument String email,
            @Argument int age,
            @Argument String gender,
            @Argument String password,
            @Argument String phoneNumber,
            @Argument String university,
            @Argument String jobTitle
    ) throws IOException {
        Teacher teacher = new Teacher();
        teacher.setFullName(fullName);
        teacher.setEmail(email);
        teacher.setAge(age);
        teacher.setGender(gender);
        teacher.setPassword(password);
        teacher.setPhoneNumber(phoneNumber);
        teacher.setUniversity(university);
        teacher.setJobTitle(jobTitle);

        return teacherService.saveTeacher(teacher, null);  // Image is not provided here, assuming it's optional.
    }
}
