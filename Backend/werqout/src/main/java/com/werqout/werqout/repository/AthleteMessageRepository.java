package com.werqout.werqout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.werqout.werqout.models.AthleteMessage;

public interface AthleteMessageRepository extends JpaRepository<AthleteMessage, Long> {
	
	AthleteMessage findById(long id);
}
