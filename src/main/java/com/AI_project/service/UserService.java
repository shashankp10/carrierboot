package com.AI_project.service;

import com.AI_project.Dto.UserDto;
import com.AI_project.entities.Users;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto user, long userId);
	UserDto getUserById(Long id);
	void deleteUser(Long userId);
	Users findByEmailAndPassword(String email, String password);
	Users findByUsername(String username);
	String getSaltByUsername(String username);
}
