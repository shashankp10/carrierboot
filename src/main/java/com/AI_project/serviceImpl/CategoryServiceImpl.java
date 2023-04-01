package com.AI_project.serviceImpl;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AI_project.Dto.CategoryDto;
import com.AI_project.Repo.CategoryRepo;
import com.AI_project.entities.Categories;
import com.AI_project.exception.ResourceNotFoundException;
import com.AI_project.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public CategoryDto createPost(CategoryDto createDto) {
		Categories post = this.dtoToCategory(createDto);
		Categories saveCategory = this.categoryRepo.save(post);
		return this.CategoryToDto(saveCategory);
	}

	@Override
	public CategoryDto updatePost(CategoryDto categoryDto, long userId) {
		  Categories category = this.categoryRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Notes : ","Id",userId));
		
		  category.setCategory(categoryDto.getCategory());
		  category.setDescription(categoryDto.getDescription());
		  category.setFile_link(categoryDto.getFile_link());
		  category.setFile_roadmap(categoryDto.getFile_roadmap());
		  category.setRating(categoryDto.getRating());
		  category.setViews(categoryDto.getViews());
		  
		  Categories updatedNotes = this.categoryRepo.save(category); 
		  CategoryDto notesDto1 = this.CategoryToDto(updatedNotes);
		  return notesDto1;
		 
	}

	@Override
	public List<Categories> getPostByCategory(String category) {
		return categoryRepo.findPostByCategory(category);
	}

	@Override
	public void deletePost(Long uid) {

		Categories category = this.categoryRepo.findById(uid)
				.orElseThrow(() -> new ResourceNotFoundException("Category","Id",uid));
		
		this.categoryRepo.delete(category);

	}
	private Categories dtoToCategory(CategoryDto categoryDto) {
		Categories category = new Categories();
		category.setCategory(categoryDto.getCategory());
		category.setDescription(categoryDto.getDescription());
		category.setFile_link(categoryDto.getFile_link());
		category.setFile_roadmap(categoryDto.getFile_roadmap());
		category.setRating(categoryDto.getRating());
		category.setViews(categoryDto.getViews());
		return category;
	}
	private CategoryDto CategoryToDto(Categories category) {
		CategoryDto categoryDto = new CategoryDto();
		categoryDto.setCategory(category.getCategory());
		categoryDto.setDescription(category.getDescription());
		categoryDto.setFile_link(category.getFile_link());
		categoryDto.setFile_roadmap(category.getFile_roadmap());
		categoryDto.setRating(category.getRating());
		categoryDto.setViews(category.getViews());
		return categoryDto;
	}
}
