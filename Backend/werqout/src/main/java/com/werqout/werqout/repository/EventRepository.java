package com.werqout.werqout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.werqout.werqout.models.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    Event findById(long id);

    void deleteById(long id);
}
