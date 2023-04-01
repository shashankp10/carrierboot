package com.AI_project.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.AI_project.entities.Users;

public interface UserRepo extends JpaRepository<Users, Long> {
	
	Users findByEmailAndPassword(String email, String password);
	Users findByUsername(String username);
	
	@Query("SELECT salt FROM Users WHERE username = :username")
	String getSaltByUsername(String username);

}
