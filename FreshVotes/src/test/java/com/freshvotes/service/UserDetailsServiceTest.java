package com.freshvotes.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsServiceTest {

	@Test
	public void generateEncryptedPassword() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "asdfasdf";
		String encodedPassword = encoder.encode(rawPassword);
		System.out.println(encodedPassword);
		
		if(rawPassword == encodedPassword) {
			fail();
		}
		
	}
}
