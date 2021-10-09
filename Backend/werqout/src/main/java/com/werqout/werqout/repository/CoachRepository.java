package com.werqout.werqout.repository;

import org.springframework.stereotype.Repository;

import com.werqout.werqout.models.Coach;

@Repository
public interface CoachRepository extends UserRepository {

    Coach findById(long id);

    void deleteById(long id);
    
}
