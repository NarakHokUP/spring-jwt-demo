package com.phearun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/auth/token", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest user, Device device) /*throws AuthenticationException*/ {

    	// Perform the security, instead of check matching password by yourself like the code below
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        /*if(!user.getPassword().equals(userDetails.getPassword())) {
		System.out.println("=> Bad Credential!");
		throw new BadCredentialsException(user.getUsername());
		}*/
        
		System.out.println("=> " + user);
		//Authenticate user
    	UserDetails userDetails =  userDetailsService.loadUserByUsername(user.getUsername());
    	System.out.println(userDetails);

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        final String token = jwtTokenUtil.generateToken(userDetails, device);
        
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }
}
