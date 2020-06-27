package com.freshvotes.security;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.freshvotes.domain.User;

public class CustomSecurityUser extends User implements UserDetails{

	private static final long serialVersionUID = -8351482943638214015L;
	
	public CustomSecurityUser() {
		
	}
	
	public CustomSecurityUser(User user) {
		this.setAuthorities(user.getAuthorities());
		this.setId(user.getId());
		this.setName(user.getName());
		this.setPassword(user.getPassword());
		this.setUsername(user.getUsername());
		this.setImageURL(user.getImageURL());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		for(Authority auth : this.getAuthorities()) {
			if(auth.getAuthority().equals("ROLE_UNABLE")) {
				return false;
			}
		}
		return true;
	}
	
}
