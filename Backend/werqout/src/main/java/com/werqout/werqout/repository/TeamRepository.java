package com.werqout.werqout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.werqout.werqout.models.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
	
	Team findById(int id);
	
	void deleteById(int id);

}
