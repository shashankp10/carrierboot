package com.AI_project.controllers;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AI_project.Dto.CategoryDto;
import com.AI_project.entities.Categories;
import com.AI_project.exception.ApiResponse;
import com.AI_project.service.CategoryService;

import io.micrometer.common.util.StringUtils;

@RestController
@RequestMapping("/auth/api") 
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/upload")
	public ResponseEntity<CategoryDto> uploadLink(@RequestBody CategoryDto categoryDto){
		if(categoryDto.getCategory()==null || categoryDto.getDescription()==null ||
				categoryDto.getFile_link()==null || categoryDto.getFile_roadmap()==null ||
				categoryDto.getRating()==null || categoryDto.getViews()==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
			
		CategoryDto createCategoryDto = this.categoryService.createPost(categoryDto);
		System.out.println("Link uploaded successfully!!");
		return new ResponseEntity<>(createCategoryDto, HttpStatus.CREATED);
	}
	
	@GetMapping(value="/fetch/{category}")
	public ResponseEntity<List<Categories>> getNotes(@PathVariable String category){
		if(StringUtils.isBlank(category)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(this.categoryService.getPostByCategory(category));
	}
	
	@DeleteMapping("/delete/{uid}")
	public ResponseEntity<ApiResponse> deleteNotes(@PathVariable Long uid){
		// check if uid is valid
		// validation to check if user has logged in
		// requested uid for deletion must belongs to the logged in user
		this.categoryService.deletePost(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully"),HttpStatus.OK);

	}
}
