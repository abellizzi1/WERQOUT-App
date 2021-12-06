package com.werqout.werqout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.werqout.werqout.models.AthleteDM;

public interface AthleteDMRepository extends JpaRepository<AthleteDM, Long> {
	
	AthleteDM findById(long id);
}
