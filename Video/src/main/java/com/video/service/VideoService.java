package com.video.service;


import com.video.client.CourseClient;
import com.video.entity.Videos;
import com.video.models.Course;
import com.video.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class VideoService implements VideoInterface   {
	
	@Autowired
	private VideoRepository videoRepository ;

	@Autowired
	private CourseClient courseCleint ;



	@Override
	public Videos createPost(Videos videos) {
		if(videos.getTitle().isEmpty()) {
			throw new RuntimeException("401 not found");
		}
		try {
			Videos saveVideo = videoRepository.save(videos);
			videos.setAddedDate(new Date());
			videos.setVideoName("default.mp4");

			return videoRepository.save(saveVideo);
		}catch(IllegalArgumentException i) {
			throw new RuntimeException("401 not found");
		}catch(Exception e) {
			throw new RuntimeException("401 sommting wrong "+e.getMessage());
		}
	}

	@Override
	public Videos getVideosById(Integer id) {
	Videos video = this.videoRepository.findById(id).orElseThrow(() -> new RuntimeException("501 Id not found"));
	return video ;
	}

	@Override
	public List<Videos> getAllVideos() {
	List<Videos> listOfVideo  = null ;
	try {
		listOfVideo = this.videoRepository.findAll();
		return listOfVideo ;
	}catch(Exception e) {
		throw new RuntimeException("401 problem "+e.getMessage());
	}
	}

	@Override
	public Videos updatePost(Videos videos, Integer id) {
		Videos video = this.videoRepository.findById(id).orElseThrow(()-> new RuntimeException("501 Id not found"));
		
		video.setTitle(videos.getTitle());
		video.setDescription(videos.getDescription());
		video.setTags(videos.getTags());
		video.setAddedDate(new Date());
		Videos updateVideo =this.videoRepository.save(video);
		return updateVideo ;
	}

	@Override
	public void deleteVideos(Integer id) {
	Videos video = this.videoRepository.findById(id).orElseThrow(()-> new RuntimeException("501 Id not found"));
	this.videoRepository.delete(video);
		
	}

	@Transactional
	public void deleteByCourse(Long id) {

		this.videoRepository.deleteAllByCourseId(id);

	}

	@Override
	public List<Videos> getVideosByCourseId(Long id) {

		List<Videos> videos = this.videoRepository.findAllByCourseId(id);

		for (Videos video : videos) {
			Course course = courseCleint.TeacherById(video.getCourseId());
			video.setCourse(course);
		}
		return videos;
	}

//	@Override
//	public UpdateModel updateModel(UpdateModel updateModel, int id) {
//Videos video = this.videoRepository.findById(id).orElseThrow(()-> new RuntimeException("501 Id not found"));
//		updateModel.setId(id);
//		video.setTitle(updateModel.getTitle());
//		video.setDescription(updateModel.getDescription());
//		video.setTags(updateModel.getTags());
//		video.setAddedDate(new Date());
//	this.videoRepository.save(video);
//		return updateModel ;
//	}

	
}
