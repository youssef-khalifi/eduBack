package com.cour.clients;

import com.cour.model.Teacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "TEACHER-SERVICE")
public interface TeacherClient {
    @GetMapping("/Teachers/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id);
}
