package com.ct.comm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ct.comm.model.Communication;

@Repository
public interface CommunicationRepository extends JpaRepository< Communication, Long > {
	Optional< Communication > findById( long id );

	List< Communication > findByUserId( Long userId );

	Optional< Communication > findByIdAndUserId( Long commId, Long userId );

	// Communication save( Communication persisted );
}
