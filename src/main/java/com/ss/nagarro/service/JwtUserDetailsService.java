package com.ss.nagarro.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("admin".equals(username)) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
			List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
			updatedAuthorities.add(authority);
			return new User("admin", "$2a$10$GDXn.VEjxOPNEb.hWVR70OZXmaqS4OklQP2cP74Cm/HIMYbPx96JS",
					updatedAuthorities);
		} else if ("user".equals(username)) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
			List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<>();
			updatedAuthorities.add(authority);
			return new User("user", "$2a$10$lb6L1VqE6EDPPT2VAYSraugFltiN15Vhrz8o6liLykM.jSGZ6DoLm",
					updatedAuthorities);
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}