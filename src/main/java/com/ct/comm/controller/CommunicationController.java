package com.ct.comm.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ct.comm.exception.ResourceNotFoundException;
import com.ct.comm.model.Communication;
import com.ct.comm.repository.CommunicationRepository;
import com.ct.comm.repository.UserRepository;

@RestController
@RequestMapping( "/api/v1" )
@ComponentScan( "com.ct.comm" )
public class CommunicationController {
	@Autowired
	private CommunicationRepository communicationRepository;
	@Autowired
	private UserRepository userRepository;

	@Cacheable( value = "cacheCommUserId" )
	@GetMapping( "/users/{userId}/communications" )
	public List< Communication > getAllCommunicationsByUserId( @PathVariable( value = "userId" ) Long userId )
			throws ResourceNotFoundException {
		if( !userRepository.existsById(userId) )
			throw new ResourceNotFoundException("User Id " + userId + " not found");
		return communicationRepository.findByUserId(userId);
	}

	@Transactional
	@PostMapping( "/users/{userId}/communications" )
	public Communication createCommunicationByUserId( @PathVariable( value = "userId" ) Long userId,
			@Valid @RequestBody Communication comm ) throws ResourceNotFoundException {
		return userRepository.findById(userId).map(user -> {
			comm.setUser(user);
			return communicationRepository.save(comm);
		}).orElseThrow(() -> new ResourceNotFoundException("User Id " + userId + " not found"));
	}

	@Transactional
	@PutMapping( "/users/{userId}/communications/{commId}" )
	public Communication updateCommunication( @PathVariable( value = "userId" ) Long userId,
			@PathVariable( value = "commId" ) Long commId, @Valid @RequestBody Communication c )
			throws ResourceNotFoundException {
		if( !userRepository.existsById(userId) )
			throw new ResourceNotFoundException("User Id " + userId + " not found");

		return communicationRepository.findById(commId).map(comm -> {
			comm.setFreq(c.getFreq());
			comm.setTimeUnit(c.getTimeUnit());
			comm.setTypeOfComm(c.getTypeOfComm());
			return communicationRepository.save(comm);
		}).orElseThrow(() -> new ResourceNotFoundException("Communication Id " + commId + "not found"));
	}

	// @PostMapping( "/communications" )
	// public Communication createCommunication( @Valid @RequestBody Communication comm ) {
	// return commRepository.save(comm);
	// }

	@Transactional
	@DeleteMapping( "/users/{userId}/communications/{commId}" )
	public @ResponseBody ResponseEntity< ? > deleteCommunication( @PathVariable( value = "userId" ) Long userId,
			@PathVariable( value = "commId" ) Long commId ) throws ResourceNotFoundException {
		return communicationRepository.findByIdAndUserId(commId, userId).map(comm -> {
			communicationRepository.delete(comm);
			return ResponseEntity.ok("Communication Deleted");
		}).orElseThrow(
				() -> new ResourceNotFoundException("Communication not found with id " + commId + " and userId " + userId));
	}
}
