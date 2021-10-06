package com.werqout.werqout.repository;

import org.springframework.stereotype.Repository;

import com.werqout.werqout.models.GymOwner;

@Repository
public interface GymOwnerRepository extends UserRepository {

    GymOwner findById(long id);

    void deleteById(long id);
}
