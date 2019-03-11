package com.ct.comm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.comm.exception.ResourceNotFoundException;
import com.ct.comm.model.User;
import com.ct.comm.repository.CommunicationRepository;
import com.ct.comm.repository.EmailRepository;
import com.ct.comm.repository.SmsRepository;
import com.ct.comm.repository.UserRepository;
import com.ct.comm.service.SchedulerService;

@RestController
@RequestMapping( "/api/v1" )
@ComponentScan( "com.ct.comm" )
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommunicationRepository communicationRepository;
	@Autowired
	private EmailRepository emailRepository;
	@Autowired
	private SmsRepository smsRepository;
	@Autowired
	SchedulerService scheduler;

	@Cacheable( value = "cacheUsers" )
	@GetMapping( "/users" )
	public List< User > getAllUsers() {
		return userRepository.findAll();
	}

	@Cacheable( value = "cacheUserId" )
	@GetMapping( "/users/{id}" )
	public ResponseEntity< User > getUserById( @PathVariable( value = "id" ) Long userId ) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		return ResponseEntity.ok().body(user);
	}

	@Transactional
	@PostMapping( "/users" )
	public User createUser( @Valid @RequestBody User user ) {
		return userRepository.save(user);
	}

	@Transactional
	@PutMapping( "/users/{id}" )
	public ResponseEntity< User > updateUser( @PathVariable( value = "id" ) Long userId, @Valid @RequestBody User userDetails )
			throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		user.setEmail(userDetails.getEmail());
		user.setLastName(userDetails.getLastName());
		user.setFirstName(userDetails.getFirstName());
		user.setContact(userDetails.getContact());
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	@Transactional
	@DeleteMapping( "/users/{id}" )
	public Map< String, Boolean > deleteUser( @PathVariable( value = "id" ) Long userId ) throws ResourceNotFoundException {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		communicationRepository.deleteAll(communicationRepository.findByUserId(userId));
		emailRepository.deleteAll(emailRepository.findByUserId(userId));
		smsRepository.deleteAll(smsRepository.findByUserId(userId));
		userRepository.delete(user);
		Map< String, Boolean > response = new HashMap<>();
		response.put("User Deleted", Boolean.TRUE);
		return response;
	}
}
