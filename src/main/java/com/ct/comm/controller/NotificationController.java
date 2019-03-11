package com.ct.comm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ct.comm.model.Communication;
import com.ct.comm.model.Text;
import com.ct.comm.model.User;
import com.ct.comm.repository.CommunicationRepository;
import com.ct.comm.repository.UserRepository;
import com.ct.comm.service.NotificationService;

@RestController
@RequestMapping( "/api/v1" )
public class NotificationController {
	@Autowired
	NotificationService notifService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommunicationRepository communicationRepository;

	@PostMapping( "/users/{userId}/sendNotification" )
	public ResponseEntity< ? > createCommunicationByUserId( @PathVariable( value = "userId" ) Long userId,
			@Valid @RequestBody Text text ) {
		User user = userRepository.findById(userId).get();
		List< Communication > communications = communicationRepository.findByUserId(userId);
		notifService.sendCommunication(user, communications, text.getText());
		return ResponseEntity.ok("The message has been sent to - " + user);
	}
}
