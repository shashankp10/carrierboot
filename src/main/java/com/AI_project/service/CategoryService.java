package com.AI_project.service;

import java.util.List;
import java.util.Locale.Category;

import com.AI_project.Dto.CategoryDto;
import com.AI_project.entities.Categories;

public interface CategoryService {
	
	CategoryDto createPost(CategoryDto createDto);
	CategoryDto updatePost(CategoryDto user, long userId);
	List<Categories> getPostByCategory(String category);
	void deletePost(Long Id);
}
