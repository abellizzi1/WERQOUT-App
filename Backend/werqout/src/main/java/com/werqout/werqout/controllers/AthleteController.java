package com.werqout.werqout.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.werqout.werqout.repository.AthleteRepository;
import com.werqout.werqout.models.Athlete;
import com.werqout.werqout.models.Group;

import java.util.List;
import java.util.ArrayList;

@RestController
public class AthleteController {
    
    @Autowired
    AthleteRepository athleteRepository;

    @GetMapping("/athletes")
    public List<Athlete> getAllAthletes() {
        //need to have a method that returns username and email and make sure it matches with the database
        return athleteRepository.findAll();
    }
    
    @GetMapping("/athletes/{id}")
    public Athlete getAthlete(@PathVariable int id) {
    	return athleteRepository.findById(id);
    }

    @PostMapping("/athletes")
    public Athlete createAthlete(@RequestBody Athlete newAthlete){
        athleteRepository.save(newAthlete);
        return newAthlete;
    }
    
    @PutMapping("/athletes/{id}")
    public Athlete updateAthlete(@PathVariable int id, @RequestBody Athlete athlete) {
    	if(athleteRepository.findById(id) == null)
    		return null;
    	athleteRepository.save(athlete);
    	return athleteRepository.findById(id);
    }
    
    @DeleteMapping("/athletes/{id}")
    public String deleteAthlete(@PathVariable int id) {
    	if(athleteRepository.findById(id) == null)
    		return "No Such Athlete";
    	athleteRepository.deleteById(id);
    	return "Athlete: " + athleteRepository.findById(id).getUserName() + " Deleted";
    }
    
    @GetMapping("/athletes/{id}/groups")
    public List<Group> getGroups(@PathVariable int id) {
    	return athleteRepository.findById(id).getGroups();
    }
    
    @PostMapping("/athletes/{id}/groups")
    public String addGroup(@PathVariable int id, @RequestBody Group group) {
    	Athlete athlete = athleteRepository.findById(id);
    	athlete.addGroup(group);
    	return "success";
    }
    
    
    public void removeGroup(@PathVariable int id, @RequestBody Group group) {
    	athleteRepository.findById(id).removeGroup(group);
    }
    
}
