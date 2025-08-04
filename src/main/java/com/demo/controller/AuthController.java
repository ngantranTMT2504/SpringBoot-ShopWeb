package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.model.Account;
import com.demo.request.AuthRequest;
import com.demo.response.ApiResponse;
import com.demo.service.JwtService;
import com.demo.service.account.AccountService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				boolean active = accountService.checkActive(authRequest.getEmail());
				
				if (active) {
					String token = jwtService.generateToken(authRequest.getEmail());
					return ResponseEntity.ok(token);
				} else {
					return ResponseEntity.status(403).body("Account locked");
				}
			} else {
				throw new UsernameNotFoundException("Invalid account!");
			}
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody Account account) {
		try {
			Account createdAccount = accountService.create(account);
			return ResponseEntity.ok(createdAccount);
		} catch (Exception e) {
		    e.printStackTrace(); 
		    return ResponseEntity.status(403).body("Error");
		}
	}
}
