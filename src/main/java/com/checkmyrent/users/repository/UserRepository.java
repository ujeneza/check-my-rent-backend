package com.checkmyrent.users.repository;



import com.checkmyrent.users.io.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // UserDTO createUser(UserDTO user);
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	List<UserEntity> findAll();
	// UserEntity findUserByEmailVerificationToken(String token);


}
