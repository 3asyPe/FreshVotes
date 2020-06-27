package com.freshvotes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freshvotes.domain.User;
import com.freshvotes.security.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Integer>{

	Optional<Authority> findByUserAndAuthority(User user, String authority);

	void deleteByUserAndAuthority(User user, String authority);
}
