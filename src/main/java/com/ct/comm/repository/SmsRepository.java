package com.ct.comm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ct.comm.model.SMS;

@Repository
public interface SmsRepository extends JpaRepository< SMS, Long > {
	Optional< SMS > findById( long id );

	List< SMS > findByUserId( Long userId );

	Optional< SMS > findByIdAndUserId( Long id, Long userId );
}
