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
import com.werqout.werqout.models.Team;

@RestController
@RequestMapping("/coaches")
public class CoachController {
	
	@Autowired
	CoachRepository coachRepository;
	
	@GetMapping("/")
	public List<Coach> getAllCoaches(){
		return coachRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Coach getCoach(@PathVariable int id) {
		return coachRepository.findById(id);
	}
	
	@PostMapping("/")
	public Coach createCoach(@RequestBody Coach coach) {
		coachRepository.save(coach);
		return coachRepository.findById(coach.getId());
	}
	
	@PutMapping("/{id}")
	public Coach updateCoach(@PathVariable long id, @RequestBody Coach coach) {
		if(coachRepository.findById(id) == null)
			return null;
		coachRepository.save(coach);
		return coachRepository.findById(id);
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	public String deleteAthlete(@PathVariable int id) {
		if(coachRepository.findById(id) == null)
			return "No Such Coach";
		String coachName = coachRepository.findById(id).getUserName();
		return "Coach: " + coachName + " Deleted";
	}
	
	/*
	 * Methods that handle managedGroup
	 */
	
	@GetMapping("/{id}/teams")
	public Team getManagedTeam(@PathVariable int id) {
		return coachRepository.findById(id).getManagedTeam();
	}
	
	@PutMapping("/{id}/teams")
	public Team setManagedTeam(@PathVariable int id, @RequestBody Team team) {
		Coach coach = coachRepository.findById(id);
		if(coach == null)
			return null;
		coach.setManagedTeam(team);
		coachRepository.save(coach);
		return coachRepository.findById(id).getManagedTeam();
	}
	
	@Transactional
	@DeleteMapping("/{id}/teams")
	public boolean removeManagedTeam(@PathVariable long id) {
		Coach coach = coachRepository.findById(id);
		if(coach == null)
			return false;
		coach.removeManagedTeam();
		coachRepository.save(coach);
		return true;
	}
}
