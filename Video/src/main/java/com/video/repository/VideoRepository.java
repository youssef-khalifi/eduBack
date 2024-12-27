package com.video.repository;

import com.video.entity.Videos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Videos, Integer> {

    void deleteAllByCourseId(Long courseId);
    List<Videos> findAllByCourseId(Long courseId);
	
	
	
	
	

	

}
