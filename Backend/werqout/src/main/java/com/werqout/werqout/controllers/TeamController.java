package com.werqout.werqout.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.werqout.werqout.repository.TeamRepository;
import com.werqout.werqout.models.Athlete;
import com.werqout.werqout.models.Team;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
	@Autowired
	TeamRepository teamRepository;
	
	@GetMapping("/all")
	List<Team> getTeam(){
		return teamRepository.findAll();
	}
	
	@GetMapping("/team/{id}")
	Team findTeam(@PathVariable int id) {
		return teamRepository.findById(id);
	}
	
	@PostMapping("/team")
	Team createTeam(@RequestBody Team group) {
		teamRepository.save(group);
		return teamRepository.findById(group.getId()).get();
	}
	
	@PutMapping("/team/{id}")
	Team updateGroup(@PathVariable int id, @RequestBody Team group) {
		Team toUpdate = teamRepository.findById(id);
		if(toUpdate == null)
			return null;
		teamRepository.save(group);
		return teamRepository.findById(id);
	}
	
	@DeleteMapping("/team/{id}")
	String deleteGroup(@PathVariable int id) {
		teamRepository.deleteById(id);
		return "Group: " + teamRepository.findById(id).getName() + " deleted successfully!";
	}
	
	/*
	 * Below are methods which concern members of a team
	 */
	
	@GetMapping("/teams/{id}/athletes")
	List<Athlete> getMembers(@PathVariable int id){
		return teamRepository.findById(id).getMembers();
	}
	

	
	@PostMapping("/teams/{id}/athletes")
	void addMember(@PathVariable int id, @RequestBody Athlete athlete) {
		Team team = teamRepository.findById(id);
		if(team != null)
			team.addMember(athlete);
	}
	
	@DeleteMapping("/teams/{id}/athletes")
	void removeMember(@PathVariable int teamId, @RequestBody Athlete athlete) {
		Team group = teamRepository.findById(teamId);
		if(group != null && group.getMembers().contains(athlete)) {
			group.removeMember(athlete);
		}
	}
	
}
