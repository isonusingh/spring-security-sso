package com.ustglobal.qubz.authentication;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements AuthenticationUserDetailsService {

	public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
		UserDetails userDetails = null;

		String credentials = (String) token.getCredentials();
		String principal = (String) token.getPrincipal();

		if (credentials != null && principal != null) {
			String name = principal;
			String password = credentials;

			// Setting user Authorities
			if ("edward".equalsIgnoreCase(name)) {
				userDetails = getAdminUser(name, password);
			}
		}

		if (userDetails == null) {
			throw new UsernameNotFoundException("Invalid user - " + principal);
		}

		return userDetails;
	}

	private UserDetails getAdminUser(String username, String password) {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("AUTH_USER"));
		return new User(username, password, true, true, true, true, grantedAuthorities);
	}
}
