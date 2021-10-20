package com.werqout.werqout.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.werqout.werqout.repository.AthleteRepository;
import com.werqout.werqout.models.Athlete;
import com.werqout.werqout.models.Team;



@RestController
@RequestMapping("/athletes")
public class AthleteController {
    
    @Autowired
    AthleteRepository athleteRepository;
    
    @GetMapping("/{id}")
    public Athlete getAthlete(@PathVariable long id) {
    	return athleteRepository.findById(id);
    }

    @PostMapping("")
    public Athlete createAthlete(@RequestBody Athlete newAthlete){
        athleteRepository.save(newAthlete);
        return newAthlete;
    }
    
    @PutMapping("/{id}")
    public Athlete updateAthlete(@PathVariable long id, @RequestBody Athlete athlete) {
    	if(athleteRepository.findById(id) == null)
    		return null;
    	athleteRepository.save(athlete);
    	return athleteRepository.findById(id);
    }
    
    @Transactional
    @DeleteMapping("/{id}")
    public String deleteAthlete(@PathVariable long id) {
    	if(athleteRepository.findById(id) == null)
    		return "No Such Athlete";                       //putting .get() here to resolve cannot convert from optional<>...
    	String athleteName = athleteRepository.findById(id).getUserName();
    	athleteRepository.deleteById(id);
    	return "Athlete: " + athleteName + " Deleted";
    }


    
    @GetMapping("/{id}/teams")
    public List<Team> getGroups(@PathVariable long id) {
    	return athleteRepository.findById(id).getGroups();
    }
    
    @PostMapping("/{id}/teams")
    public List<Team> addGroup(@PathVariable long id, @RequestBody Team group) {
    	Athlete athlete = athleteRepository.findById(id);
    	athlete.addGroup(group);
    	athleteRepository.save(athlete);
    	return athleteRepository.findById(id).getGroups();
    }
    
    @Transactional
    @DeleteMapping("/{id}/teams")
    public void removeGroup(@PathVariable long id, @RequestBody Team group) {
    	athleteRepository.findById(id).removeGroup(group);
    }
    
}
