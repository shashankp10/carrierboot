package com.AI_project.controllers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AI_project.Dto.UserDto;
import com.AI_project.entities.Users;
import com.AI_project.exception.ApiResponse;
import com.AI_project.exception.ErrorResponse;
import com.AI_project.service.UserService;

@RestController
@RequestMapping("/auth/api") 
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto) throws NoSuchAlgorithmException{
		if (StringUtils.isAnyBlank(userDto.getEmail(), userDto.getPassword(), userDto.getUsername())){
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(new ErrorResponse("One or more field(s) is/are empty or null"));
	    }
		if(userDto.getUsername().length()<= 4 || userDto.getPassword().length()<= 4) {
			String message = "Username must be of 4 characters long and password must be"
					+ " atleast 4 characters long!!";
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(message));
		}
	    Users registerUser = userService.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
	    UserDto createUserDto = new UserDto();
	    if(registerUser!=null) {      
	    	return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("User already exists!!"));
	    }
	    else { 
	    	 //String salt = UUID.randomUUID().toString();
	         // Encrypt the password using the salt
//	         String encryptedPassword = encryptPassword(userDto.getPassword(), salt);
//	         userDto.setSalt(salt);
//	         userDto.setPassword(encryptedPassword);
	         createUserDto = this.userService.createUser(userDto);
	         UserDto responseDto = new UserDto();
	         responseDto.setUsername(createUserDto.getUsername());
	         responseDto.setPassword(createUserDto.getPassword());
	         responseDto.setEmail(createUserDto.getEmail());
	         return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(new ErrorResponse("User registered successfully!!"));
	    }
		
	}
	private String encryptPassword(String password, String salt) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    String text = password + salt;
	    md.update(text.getBytes(StandardCharsets.UTF_8));
	    byte[] digest = md.digest();
	    String encryptedPassword = Base64.getEncoder().encodeToString(digest);
	    return encryptedPassword;
	}
		// clicking on --> Create an account

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDto userDto) throws NoSuchAlgorithmException{
		
		if(StringUtils.isAnyBlank(userDto.getPassword(),userDto.getEmail())) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("Email or password cannot be empty!!"));	    
		}
//		String salt = userService.getSaltByUsername(userDto.getUsername());
//		System.out.println("salt : " + salt);
		if(userService.findByEmailAndPassword(userDto.getEmail(),userDto.getPassword()) == null) {					
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("Invalid credentials"));

		}
		
		Users loginUser = userService.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());		
		if(loginUser!=null) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ErrorResponse("Logged in!!"));

		}
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse("Invalid credentials"));
		
		
	} 
	
		// Update related setting
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId")Long uid){
		if (StringUtils.isAnyBlank(userDto.getEmail(), userDto.getPassword(), userDto.getUsername())){
//	        return ResponseEntity
//	                .status(HttpStatus.BAD_REQUEST)
//	                .body(new ErrorResponse());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		UserDto updateUser = this.userService.updateUser(userDto, uid);
		System.out.println("Detail has been updated succesfully!!");
		return ResponseEntity.ok(updateUser);
	}
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long uid){
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted"),HttpStatus.OK);
	}
}
