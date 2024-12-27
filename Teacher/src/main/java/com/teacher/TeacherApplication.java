package com.teacher;

import com.teacher.entities.Teacher;
import com.teacher.repositories.TeacherRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class TeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }

    @Bean
    CommandLineRunner start(TeacherRepo repo) {
        return args -> {
            Teacher teacher = Teacher.builder()
                    .fullName("youssef khalifi")
                    .age(12)
                    .gender("male")
                    .phoneNumber(null)
                    .password("password")
                    .email("ussf@gmail.com")
                    .role("Teacher")
                    .build();
            repo.save(teacher);
        };
    }
}
