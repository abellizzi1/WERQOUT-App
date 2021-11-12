package com.werqout.werqout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.werqout.werqout.models.Coach;


public interface CoachRepository extends JpaRepository<Coach, Long> {

    Coach findById(long id);

    void deleteById(long id);
}
