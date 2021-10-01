package com.werqout.werqout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.werqout.werqout.models.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
	
	Group findById(int id);
	
	void deleteById(int id);

}
