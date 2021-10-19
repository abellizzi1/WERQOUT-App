package com.werqout.werqout.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.werqout.werqout.repository.AthleteRepository;
import com.werqout.werqout.models.Athlete;
import com.werqout.werqout.models.Team;

import java.util.List;


@RestController
@RequestMapping("/athletes")
public class AthleteController {
    
    @Autowired
    AthleteRepository athleteRepository;
    //Ta suggested to have this
    @RequestMapping(value="/all", method=RequestMethod.GET)
    public List<Athlete> getAllAthletes(){
        return athleteRepository.findAll();
    }
    //Ta suggested to have this
    @RequestMapping(value="post/{id}/{usr}/{email}/{pwd}/{team}", method=RequestMethod.GET)
    public Athlete postAthleteByPath(@PathVariable Long id,@PathVariable String usr,@PathVariable String email,@PathVariable String pwd,@PathVariable String team){
        Athlete athlete = new Athlete(id,usr,email,pwd,team);
        athleteRepository.save(athlete);
        return athlete;
    }    


    @GetMapping("/{id}")
    public Athlete getAthlete(@PathVariable long id) {
    	return athleteRepository.findById(id).get();
    }

    @PostMapping("/")
    public Athlete createAthlete(@RequestBody Athlete newAthlete){
        athleteRepository.save(newAthlete);
        return newAthlete;
    }
    
    @PutMapping("/{id}")
    public Athlete updateAthlete(@PathVariable long id, @RequestBody Athlete athlete) {
    	if(athleteRepository.findById(id) == null)
    		return null;
    	athleteRepository.save(athlete);
    	return athleteRepository.findById(id).get();
    }
    
    @Transactional
    @DeleteMapping("/{id}")
    public String deleteAthlete(@PathVariable long id) {
    	if(athleteRepository.findById(id) == null)
    		return "No Such Athlete";                       //putting .get() here to resolve cannot convert from optional<>...
    	String athleteName = athleteRepository.findById(id).get().getUserName();
    	athleteRepository.deleteById(id);
    	return "Athlete: " + athleteName + " Deleted";
    }


    
    @GetMapping("/athletes/{id}/team")
    public List<Team> getTeams(@PathVariable int id) {
    	return athleteRepository.findById(id).getTeams();
    }
    
    @PostMapping("/athletes/{id}/teams")
    public List<Team> addTeam(@PathVariable int id, @RequestBody Team team) {
    	Athlete athlete = athleteRepository.findById(id);
    	athlete.addTeam(team);
    	athleteRepository.save(athlete);
    	return athleteRepository.findById(id).getTeams();
    }
    
    
    public void removeTeam(@PathVariable int id, @RequestBody Team team) {
    	athleteRepository.findById(id).removeTeam(team);
    }
    
}
