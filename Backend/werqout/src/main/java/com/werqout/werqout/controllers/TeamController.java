package com.werqout.werqout.controllers;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
@RequestMapping("/teams")
@Api(value = "TeamController", tags = {"Team"}, description = "REST APIs related to Team Entity")
public class TeamController {

	
	@Autowired
	TeamRepository teamRepository;
	
	@GetMapping("")
	@ApiOperation(value = "Gets list of Teams in the database", response = Iterable.class, tags = "getTeam")
	List<Team> getTeam(){
		return teamRepository.findAll();
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Gets a team (refrenced by the id) from the database", response = Iterable.class, tags = "findTeam")
	Team findTeam(@PathVariable long id) {
		return teamRepository.findById(id);
	}
	
	@PostMapping("")
	@ApiOperation(value = "Creates a Team in the databse", response = Iterable.class, tags = "createTeam")
	Team createTeam(@RequestBody Team group) {
		teamRepository.save(group);
		return teamRepository.findById(group.getId());
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Updates a team in the datbase", response = Iterable.class, tags = "updateGroup")
	Team updateGroup(@PathVariable long id, @RequestBody Team group) {
		Team toUpdate = teamRepository.findById(id);
		if(toUpdate == null)
			return null;
		teamRepository.save(group);
		return teamRepository.findById(id);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletes a Team from the database", response = Iterable.class, tags = "deleteGroup")
	String deleteGroup(@PathVariable long id) {
		teamRepository.deleteById(id);
		return "Group: " + teamRepository.findById(id).getName() + " deleted successfully!";
	}
	
	/*
	 * Below are methods which concern members of a team
	 */
	
	@GetMapping("/{id}/athletes")
	@ApiOperation(value = "Gets the Athletes in a Team (refrenced by the team ID)", response = Iterable.class, tags = "getMembers")
	List<Athlete> getMembers(@PathVariable long id){
		return teamRepository.findById(id).getMembers();
	}
	

	
	@PostMapping("/{id}/athletes")
	@ApiOperation(value = "Adds members to the team (refrenced by the ID)", response = Iterable.class, tags = "addMember")
	void addMember(@PathVariable long id, @RequestBody Athlete athlete) {
		Team team = teamRepository.findById(id);
		if(team != null)
			team.addMember(athlete);
	}
	
	@DeleteMapping("/{id}/athletes")
	@ApiOperation(value = "Removes an Athlete from a Team (refrenced by the ID)", response = Iterable.class, tags = "removeMember")
	void removeMember(@PathVariable int teamId, @RequestBody Athlete athlete) {
		Team group = teamRepository.findById(teamId);
		if(group != null && group.getMembers().contains(athlete)) {
			group.removeMember(athlete);
		}
	}
	
}
