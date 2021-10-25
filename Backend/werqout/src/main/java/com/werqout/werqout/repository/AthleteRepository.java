package com.werqout.werqout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.werqout.werqout.models.Athlete;

public interface AthleteRepository extends JpaRepository<Athlete, Long>{
    
	Athlete findById(long id);
	
	void deleteById(long id);
}
