package com.video.controller;


import com.video.client.CourseClient;
import com.video.configuration.FileModel;
import com.video.entity.Videos;
import com.video.exception.ControllerException;
import com.video.exception.ResourceNotFound;
import com.video.models.Course;
import com.video.repository.VideoRepository;
import com.video.service.FileInterface;
import com.video.service.VideoInterface;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequestMapping("/Video")
@RestController
@RequiredArgsConstructor
public class VideoController {

	@Value("${project.video}")
	private String path;


	private final VideoInterface service;

	private final FileInterface fileSevice;

	private final VideoRepository videoRepository;

	private final CourseClient courseCleint;

	@PostMapping("/save")
	public ResponseEntity<?> saveVideo(@RequestBody Videos video) {
		try {
			Videos saveVideos = service.createPost(video);
			return new ResponseEntity<Videos>(saveVideos, HttpStatus.OK);
		} catch (ResourceNotFound e) {
			ControllerException controllerException = new ControllerException(e.getErrorCode(),
					e.getErrorMessage() + e.getMessage());
			return new ResponseEntity<ControllerException>(controllerException, HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping("/get/{id}")
	public ResponseEntity<?> getDataById(@PathVariable Integer id) {
		try {
			Videos video = service.getVideosById(id);

			Course course = courseCleint.TeacherById(video.getCourseId());
			video.setCourse(course);

			return new ResponseEntity<Videos>(video, HttpStatus.OK);

		} catch (ResourceNotFound e) {
			ControllerException controllerException = new ControllerException(e.getErrorCode(),
					e.getErrorMessage() + e.getMessage());
			return new ResponseEntity<ControllerException>(controllerException, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException controllerException = new ControllerException("504", "id not found");
			return new ResponseEntity<ControllerException>(controllerException, HttpStatus.BAD_REQUEST);
		}
	}


	@SuppressWarnings("unused")
	@GetMapping("/all")
	public ResponseEntity<?> getListofData() {
		Videos videos = new Videos();
		try {
			if (videos == null) {
				throw new RuntimeException("401 eroor");
			}
			List<Videos> v = service.getAllVideos();
			return new ResponseEntity<List<Videos>>(v, HttpStatus.CREATED);
		} catch (Exception e) {
			ControllerException controllerException = new ControllerException("404", "Empty database is found");
			return new ResponseEntity<ControllerException>(controllerException, HttpStatus.BAD_REQUEST);
		}
	}

	//3. Posting Video api .
	@PostMapping("/upload/{id}")
	public ResponseEntity<Videos> uploadVideo(@RequestParam("video") MultipartFile video, @PathVariable Integer id)
			throws IOException {
		Videos v = this.service.getVideosById(id);
		FileModel fileModel = this.fileSevice.uploadVideo(path, video);
		v.setVideoName(fileModel.getVideoFileName());
		Videos uploadVideo = this.service.updatePost(v, id);
		Course course = courseCleint.TeacherById(uploadVideo.getCourseId());
		uploadVideo.setCourse(course);
		return new ResponseEntity<Videos>(uploadVideo, HttpStatus.OK);
	}

	//4.To play video .
	@GetMapping(value = "/play/{id}", produces = MediaType.ALL_VALUE)
	public void downloadImage(@PathVariable int id, HttpServletResponse response) throws IOException {
		Optional<Videos> video = videoRepository.findById(id);
		InputStream resource = this.fileSevice.getResource(path, video.get().getVideoName(), id);
		response.setContentType(MediaType.ALL_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

	//5. Delete videos by id .
	@DeleteMapping("/{id}")
	public void deleteVideo(@PathVariable Integer id) throws IOException {
		Optional<Videos> video = videoRepository.findById(id);
		Path exactPath = Paths.get(path + File.separator + video.get().getVideoName());
		System.out.println(exactPath);
		try {
			Files.deleteIfExists(exactPath);

		} catch (Exception e1) {
			e1.getMessage();
			System.out.println(e1.getMessage()+"can not delete now ");
		}
		this.service.deleteVideos(id);

	}
	
	//Update DataModel of Video .
//	@PutMapping("/update/{id}")
//	public ResponseEntity<UpdateModel> setVideoData(@RequestBody UpdateModel updateModel, @PathVariable int id){
//		try {
//		service.updateModel(updateModel, id);
//		return new ResponseEntity<UpdateModel>(updateModel, HttpStatus.OK);
//		}catch(Exception e) {
//			throw new ResourceNotFound("404","user id not found");
//		}
//	}

	@DeleteMapping("/course/{courseId}")
	public void deleteVideos(@PathVariable Long courseId) {

		this.service.deleteByCourse(courseId);

	}
	@GetMapping("/videos/{courseId}")
	public ResponseEntity<?> getVideoByCourseId(@PathVariable Long courseId) {
		Videos video = new Videos();
		try {
			if (video == null) {
				throw new RuntimeException("401 eroor");
			}
			List<Videos> v = service.getVideosByCourseId(courseId);
			return new ResponseEntity<List<Videos>>(v, HttpStatus.CREATED);
		} catch (Exception e) {
			ControllerException controllerException = new ControllerException("404", "Empty database is found");
			return new ResponseEntity<ControllerException>(controllerException, HttpStatus.BAD_REQUEST);
		}
	}

}
