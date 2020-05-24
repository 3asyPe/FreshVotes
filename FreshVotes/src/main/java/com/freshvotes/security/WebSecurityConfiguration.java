package com.freshvotes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(getPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/dashboard").permitAll()
				.antMatchers("/products/*").permitAll()
				.antMatchers("/products/*/features/*").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/javaScript/**").permitAll()
				.antMatchers("/images/**").permitAll()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/login/**").permitAll()
				.antMatchers("/register").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().hasRole("USER")
			.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/dashboard")
			.and()
			.logout()
				.logoutUrl("/logout")
				.permitAll();
			
	}
}
