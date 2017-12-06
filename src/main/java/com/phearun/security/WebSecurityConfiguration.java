package com.phearun.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password("admin").roles("ADMIN").and()
			.withUser("user").password("user").roles("USER");
	}*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
			.httpBasic().disable()
			//.formLogin().disable();
		;
		//Don't create session
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		//Handling exception
		http.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint);
		
		//Protect resources
		http.authorizeRequests()
			.antMatchers("/auth/token").permitAll()
			.antMatchers("/api/**").authenticated();
			//.anyRequest().authenticated();
		
		 //Custom JWT based security filter
		http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		//Disable page caching
        http.headers().cacheControl();
	}
	
	@Bean
	public JWTAuthenticationFilter authenticationFilter(){
		return new JWTAuthenticationFilter();
	}
	
	
}
