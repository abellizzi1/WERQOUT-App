package com.werqout.werqout.repository;

import org.springframework.stereotype.Repository;

import com.werqout.werqout.models.Athlete;

@Repository
public interface AthleteRepository extends UserRepository{
    
	Athlete findById(int id);
	
	void deleteById(int id);
}
