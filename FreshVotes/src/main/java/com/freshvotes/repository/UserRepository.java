package com.freshvotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);
}
