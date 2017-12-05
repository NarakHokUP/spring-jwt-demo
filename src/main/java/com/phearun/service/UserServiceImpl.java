package com.phearun.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.phearun.model.CustomUserDetail;

@Service
public class UserServiceImpl implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//load user
		//authenticate user
		if(!username.equals("phearun"))
			return null;
		
		CustomUserDetail userDetail = new CustomUserDetail(username, "password", "ADMIN");		
		return userDetail;
	}
}
