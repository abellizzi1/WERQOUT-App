package com.werqout.werqout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.werqout.werqout.models.athlete;

public interface athleteRepository extends JpaRepository<athlete, Long>{
    
}
