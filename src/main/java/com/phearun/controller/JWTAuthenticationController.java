package com.phearun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phearun.model.JwtAuthenticationRequest;
import com.phearun.model.JwtAuthenticationResponse;
import com.phearun.utility.JwtTokenUtil;

@RestController
public class JWTAuthenticationController {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/auth/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest user, Device device) throws AuthenticationException {

		System.out.println("=> " + user);
		//Authenticate user
    	UserDetails userDetails =  userDetailsService.loadUserByUsername(user.getUsername());
    	System.out.println(userDetails);

    	if(!user.getPassword().equals(userDetails.getPassword())) {
			System.out.println("=> Bad Credential!");
			throw new BadCredentialsException(user.getUsername());
		}

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        // Return the token
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
}
