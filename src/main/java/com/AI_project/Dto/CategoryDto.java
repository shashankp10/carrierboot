package com.AI_project.Dto;

import lombok.Data;

@Data
public class CategoryDto {
	
	private long id;
	private String category;
	private String file_courselink;
	private String file_roadmap;
	private String file_ytlink;
	private String rating;
	private String views;
	private String price;
	private String description;
}
