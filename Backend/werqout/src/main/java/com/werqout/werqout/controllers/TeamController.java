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
	Team findGroup(@PathVariable int id) {
		return teamRepository.findById(id);
	}
	
	@PostMapping("/")
	Team createGroup(@RequestBody Team group) {
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
	
}
