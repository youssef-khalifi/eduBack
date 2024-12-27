package com.cour.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "VIDEO-SERVICE")
public interface VideoClient {

    @DeleteMapping("/Video/course/{courseId}")
    public void deleteVideos(@PathVariable Long courseId);
}
