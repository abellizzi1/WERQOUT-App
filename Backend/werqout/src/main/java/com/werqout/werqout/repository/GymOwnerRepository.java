package com.werqout.werqout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.werqout.werqout.models.GymOwner;

public interface GymOwnerRepository extends  JpaRepository<GymOwner, Long>{

    GymOwner findById(long id);

    void deleteById(long id);
}
