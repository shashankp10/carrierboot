package com.AI_project.Repo;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.AI_project.Dto.CategoryDto;
import com.AI_project.entities.Categories;

public interface CategoryRepo extends JpaRepository<Categories, Long> {
	
	@Query(value = "SELECT * FROM categories WHERE category = :category", nativeQuery = true)
	List<Categories> findPostByCategory(@Param("category")String category);
}  