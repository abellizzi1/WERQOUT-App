package com.werqout.werqout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.werqout.werqout.models.User;

@NoRepositoryBean
public interface UserRepository extends JpaRepository<User, Long>{
    
    User findById(int id);
	
	void deleteById(int id);
}
