package com.werqout.werqout.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

import com.werqout.werqout.repository.CoachRepository;
import com.werqout.werqout.models.Coach;
import com.werqout.werqout.models.User;

@RestController
@RequestMapping("/coaches")
public class CoachController {
	
	@Autowired
	CoachRepository coachRepository;
	
	@GetMapping("/")
	public List<User> getAllCoaches(){
		return coachRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Coach getCoach(@PathVariable long id) {
		return (Coach) coachRepository.findById(id);
	}
	
	@PostMapping("/")
	public Coach createCoach(@RequestBody Coach coach) {
		coachRepository.save(coach);
		return (Coach) coachRepository.findById(coach.getId());
	}
	
	@PutMapping("/{id}")
	public Coach updateCoach(@PathVariable long id, @RequestBody Coach coach) {
		if(coachRepository.findById(id) == null)
			return null;
		coachRepository.save(coach);
		return coachRepository.findById(id);
	}
	
	@Transactional
	@DeleteMapping
	public String deleteAthlete(@PathVariable long id) {
		if(coachRepository.findById(id) == null)
			return "No Such Coach";
		String coachName = coachRepository.findById(id).getUserName();
		return "Coach: " + coachName + " Deleted";
	}
}
