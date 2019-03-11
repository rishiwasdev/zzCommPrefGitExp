package com.ct.comm.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.comm.exception.ResourceNotFoundException;
import com.ct.comm.model.Email;
import com.ct.comm.repository.EmailRepository;
import com.ct.comm.repository.UserRepository;

@RestController
@RequestMapping( "/api/v1" )
@ComponentScan( "com.ct.comm" )
public class EmailController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailRepository emailRepository;

	@Transactional
	@PostMapping( "/users/{userId}/emails" )
	public Email saveEmail( @PathVariable( value = "userId" ) Long userId, @Valid @RequestBody Email email )
			throws ResourceNotFoundException {
		return userRepository.findById(userId).map(user -> {
			email.setUser(user);
			return emailRepository.save(email);
		}).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found"));
	}

	@Cacheable( value = "cacheEmailUserId" )
	@GetMapping( "/users/{userId}/emails" )
	public List< Email > getAllEmailsByUserId( @PathVariable( value = "userId" ) Long userId ) throws ResourceNotFoundException {
		if( !userRepository.existsById(userId) )
			throw new ResourceNotFoundException("User Id " + userId + " not found");
		return emailRepository.findByUserId(userId);
	}
}
