package com.werqout.werqout.controllers;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

import com.werqout.werqout.repository.CoachRepository;
import com.werqout.werqout.models.Coach;
import com.werqout.werqout.models.Team;

@Api(value = "AthleteController", tags = {"Coach"}, description = "REST APIs related to Coach Entity")
@RestController
@RequestMapping("/coaches")
public class CoachController {
	
	@Autowired
	CoachRepository coachRepository;
	
	@GetMapping("/")
	@ApiOperation(value = "Get list of Coaches in the database", response = Iterable.class, tags = "getAllCoaches")
	public List<Coach> getAllCoaches(){
		return coachRepository.findAll();
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Get a Coach by their ID", response = Iterable.class, tags = "getCoach")
	public Coach getCoach(@PathVariable int id) {
		return coachRepository.findById(id);
	}
	
	@PostMapping("/")
	@ApiOperation(value = "Creates a Coach in the database", response = Iterable.class, tags = "createCoach")
	public Coach createCoach(@RequestBody Coach coach) {
		coachRepository.save(coach);
		return coachRepository.findById(coach.getId());
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Updates a Coach by their ID", response = Iterable.class, tags = "updateCoach")
	public Coach updateCoach(@PathVariable long id, @RequestBody Coach coach) {
		if(coachRepository.findById(id) == null)
			return null;
		coach.setId(id);
		coachRepository.save(coach);
		return coachRepository.findById(id);
	}
	
	@Transactional
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletes a Coach from the database by their ID", response = Iterable.class, tags = "deleteCoach")
	public String deleteCoach(@PathVariable int id) {
		if(coachRepository.findById(id) == null)
			return "No Such Coach";
		String coachName = coachRepository.findById(id).getUserName();
		return "Coach: " + coachName + " Deleted";
	}
	
	/*
	 * Methods that handle managedGroup
	 */
	
	@GetMapping("/{id}/teams")
	@ApiOperation(value = "Gets the teams the Coach belongs to", response = Iterable.class, tags = "getManagedTeam")
	public Team getManagedTeam(@PathVariable int id) {
		return coachRepository.findById(id).getManagedTeam();
	}
	
	@PutMapping("/{id}/teams")
	@ApiOperation(value = "Adds the Coach to a Team", response = Iterable.class, tags = "setManageTeam")
	public Team setManagedTeam(@PathVariable int id, @RequestBody Team team) {
		Coach coach = coachRepository.findById(id);
		if(coach == null || coach.getManagedTeam() != null)
			return null;
		coach.setManagedTeam(team);
		coachRepository.save(coach);
		return coachRepository.findById(id).getManagedTeam();
	}
	
	@Transactional
	@DeleteMapping("/{id}/teams")
	@ApiOperation(value = "Deletes the Coach from a team", response = Iterable.class, tags = "removeManagedTeam")
	public boolean removeManagedTeam(@PathVariable long id) {
		Coach coach = coachRepository.findById(id);
		if(coach == null || coach.getManagedTeam() == null)
			return false;
		coach.removeManagedTeam();
		coachRepository.save(coach);
		return true;
	}

	/** Rating Methods*/

	@PutMapping("/{id}/rate") //path is rate?rating=4 but 4 is the rating you want to give
	@ApiOperation(value = "Increases the Coaches rating", response = Iterable.class, tags = "rateCoach")
	public void rateCoach(@PathVariable long id, @RequestParam(required=true, name="rating") int rating){
		Coach c = coachRepository.findById(id);
		if (c == null){
			System.out.println("No such coach");
		}
		c.rate(rating);
		coachRepository.save(c);
	}

	@GetMapping("{id}/getRating")
	@ApiOperation(value = "Gets the Coaches rating", response = Iterable.class, tags = "getCoachRating")
	public double getCoachRating(@PathVariable long id){
		Coach coach = coachRepository.findById(id);
		if (coach == null){
			System.out.println("No such coach");
		}
		return coach.getRating();
	}

}
