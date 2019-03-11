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
import com.ct.comm.model.SMS;
import com.ct.comm.repository.SmsRepository;
import com.ct.comm.repository.UserRepository;

@RestController
@RequestMapping( "/api/v1" )
@ComponentScan( "com.ct.comm" )
public class SmsController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private SmsRepository smsRepository;

	@Transactional
	@PostMapping( "/users/{userId}/messages" )
	public SMS saveSms( @PathVariable( value = "userId" ) Long userId, @Valid @RequestBody SMS sms )
			throws ResourceNotFoundException {
		return userRepository.findById(userId).map(user -> {
			sms.setUser(user);
			return smsRepository.save(sms);
		}).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found"));
	}

	@Cacheable( value = "cacheSmsSmsUserId" )
	@GetMapping( "/users/{userId}/messages" )
	public List< SMS > getAllSmsByUserId( @PathVariable( value = "userId" ) Long userId ) throws ResourceNotFoundException {
		if( !userRepository.existsById(userId) )
			throw new ResourceNotFoundException("User Id " + userId + " not found");
		return smsRepository.findByUserId(userId);
	}
}
