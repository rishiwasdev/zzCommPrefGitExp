package com.ct.comm.service;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ct.comm.controller.EmailController;
import com.ct.comm.model.SMS;
import com.ct.comm.model.User;
import com.ct.comm.repository.SmsRepository;

@Service
public class SmsService {
	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);
	@Autowired
	private SmsRepository smsRepository;

	public List< SMS > fetchUnsentSms( User user ) {
		List< SMS > messages = smsRepository.findByUserId(user.getId());
		Iterator< SMS > itr = messages.iterator();
		while ( itr.hasNext() ) {
			SMS sms = itr.next();
			if( sms.getSent() )
				itr.remove();
		}
		return messages;
	}

	@Transactional
	public void setSentStatus( List< SMS > messages ) {
		for( SMS sms : messages )
			sms.setSent(true);
		smsRepository.saveAll(messages);
	}

	/** TO BE IMPLEMENTED */
	public boolean sendSms( User user, List< SMS > messages ) {
		for( int i = 0; i < messages.size(); i++ )
			logger.info( (i + 1) + ".\t" + messages.get(i));
		setSentStatus(messages);
		return true;
	}
}