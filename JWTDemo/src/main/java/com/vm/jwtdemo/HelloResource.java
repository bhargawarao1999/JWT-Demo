package com.vm.jwtdemo;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vm.jwtdemo.model.AuthenticationRequest;
import com.vm.jwtdemo.model.AuthenticationResponse;
import com.vm.jwtdemo.util.JwtUtil;

@RestController
public class HelloResource {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@RequestMapping("/hello")
	public String hello() {
		return "Hello world";
	}


	@RequestMapping(value ="/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
		authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),authenticationRequest.getPassword() )
				);

		final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUserName());

		String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}
}
