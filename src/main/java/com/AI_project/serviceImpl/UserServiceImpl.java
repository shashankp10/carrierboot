package com.AI_project.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AI_project.Dto.UserDto;
import com.AI_project.Repo.UserRepo;
import com.AI_project.entities.Users;
import com.AI_project.exception.ResourceNotFoundException;
import com.AI_project.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		Users user = this.dtoToUsers(userDto);
		Users savedUser = this.userRepo.save(user);
		return this.usersToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, long userId) {
		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		
		user.setEmail(userDto.getEmail());
		user.setUsername(userDto.getUsername());
		user.setPassword(userDto.getPassword());
		user.setSalt(userDto.getSalt());
		
		Users updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.usersToDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUserById(Long id) {
		Users user = this.userRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User","Id",id));
		
		return this.usersToDto(user);
	}

	@Override
	public void deleteUser(Long userId) {
		Users user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
		
		this.userRepo.delete(user);
		
	}

	
	private Users dtoToUsers(UserDto userDto) {
		Users users = new Users();
		users.setEmail(userDto.getEmail());
		users.setId(userDto.getId());
		users.setUsername(userDto.getUsername());
		users.setPassword(userDto.getPassword());
		users.setSalt(userDto.getSalt());
		return users;
	}
	private UserDto usersToDto(Users user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setEmail(user.getEmail());
		userDto.setUsername(user.getUsername());
		userDto.setPassword(user.getPassword());
		userDto.setSalt(user.getSalt());
		return userDto;
	}	
	
	@Override
	public Users findByEmailAndPassword(String email, String password) {
		return userRepo.findByEmailAndPassword(email, password);
	}
	@Override
	public Users findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public String getSaltByUsername(String username) {
		return userRepo.getSaltByUsername(username);
	}

}
