package com.phearun.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phearun.model.City;

@RestController
public class CityController {

	
	@Secured("ROLE_USER")
	@GetMapping("/api/user/cities")
	public List<City> findUserCity(Authentication auth, Principal principal){
		System.out.println(auth.getAuthorities());
		System.out.println(principal.getName());
		return Arrays.asList(new City(/*1, "Phnom Penh"*/), new City(/*2, "Takhmoa"*/));
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/api/admin/cities")
	public List<City> findAdminCity(Authentication auth, Principal principal){
		System.out.println(auth.getAuthorities());
		System.out.println(principal.getName());
		return Arrays.asList(new City(/*1, "Phnom Penh"*/), new City(/*2, "Takhmoa"*/));
	}
}
