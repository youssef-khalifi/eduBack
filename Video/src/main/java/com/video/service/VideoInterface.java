package com.video.service;



import com.video.entity.Videos;

import java.util.List;

public interface VideoInterface {
	
	public Videos createPost(Videos videos);
	
	public Videos getVideosById(Integer id);
	
	public List<Videos> getAllVideos();
	
	public Videos updatePost(Videos videos , Integer id);
	
	public void deleteVideos(Integer id);

	public void deleteByCourse(Long id);

	public List<Videos> getVideosByCourseId(Long id);

//public UpdateModel updateModel(UpdateModel updateModel, int id);
}
