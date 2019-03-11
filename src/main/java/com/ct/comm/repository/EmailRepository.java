package com.ct.comm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ct.comm.model.Email;

@Repository
public interface EmailRepository extends JpaRepository< Email, Long > {
	Optional< Email > findById( Long id );

	List< Email > findByUserId( Long userId );

	Optional< Email > findByIdAndUserId( Long id, Long userId );
}
