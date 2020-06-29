package com.freshvotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.User;
import com.freshvotes.domain.VarifyEmail;

public interface EmailRepository extends JpaRepository<VarifyEmail, Integer>{
	
	List<VarifyEmail> findByEmailAddress(String emailAddress);

	void deleteByEmailAddress(String emailAddress);
}
