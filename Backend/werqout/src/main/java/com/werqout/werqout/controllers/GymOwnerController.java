package com.werqout.werqout.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.werqout.werqout.repository.GymOwnerRepository;
import com.werqout.werqout.repository.TeamRepository;

import java.util.List;

import javax.transaction.Transactional;

import com.werqout.werqout.models.GymOwner;
import com.werqout.werqout.models.Team;

@RestController
@RequestMapping("/gymOwner")
public class GymOwnerController {

    @Autowired
    GymOwnerRepository gymOwnerRepository;

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/{id}")
    public GymOwner getGymOwner(@PathVariable long id){
        return gymOwnerRepository.findById(id);
    }


    @GetMapping("/{id}/gymName")
    String getGymName(@PathVariable long id){
        if (gymOwnerRepository.findById(id) == null){
            return "No such gymOwner";
        }
        return gymOwnerRepository.findById(id).getGymName();
    }

    @PostMapping("/")
    GymOwner createGymOwner(@RequestBody GymOwner gymOwner){
        gymOwnerRepository.save(gymOwner);
        return gymOwnerRepository.findById(gymOwner.getId());
    }

    @PutMapping("/{id}")
    GymOwner updateGymOwner(@PathVariable long id, @RequestBody GymOwner gymOwner){
        GymOwner toUpdate = gymOwnerRepository.findById(id);
        if(toUpdate == null){
            return null;
        }
        gymOwnerRepository.save(gymOwner);
        return gymOwnerRepository.findById(id);
    }

    @GetMapping("/{id}/teams")
    public List<Team> getTeams(@PathVariable long id) {
    	return gymOwnerRepository.findById(id).getTeams();
    }

    @PostMapping("/{id}/teams/{teamId}")
    public List<Team> addTeam(@PathVariable long id, @PathVariable long teamId) {
    	GymOwner go = gymOwnerRepository.findById(id);
        Team team = teamRepository.findById(teamId);
    	go.addTeam(team);
    	gymOwnerRepository.save(go);
    	return gymOwnerRepository.findById(id).getTeams();
    }
    
    @Transactional
    @DeleteMapping("/{id}/teams/{teamId}")
    public String removeTeam(@PathVariable long id, @PathVariable long teamId){
        GymOwner go = gymOwnerRepository.findById(id);
        Team team = teamRepository.findById(teamId);
        if (go == null || team == null){
            return "No such gymOwner or team";
        }
        //boolean method to see if the team is in the gym
        else if (!(go.isInTeam(team))){
            System.out.println(team.getId());
            return "No such team with id " + team.getId() +  " at this gym";
        }
        else{
            go.removeTeam(team);
            gymOwnerRepository.save(go);
            getTeams(id);
            return team.getName() + " has been deleted";
        }
    }   

    @DeleteMapping("/{id}")
    String deleteGymOwner(@PathVariable long id){
        if (gymOwnerRepository.findById(id) == null){
            return "No such gymOwner";
        }
        String user = gymOwnerRepository.findById(id).getUserName();
        gymOwnerRepository.deleteById(id);
        return "Gym Owner: " + user + " deleted successfully";
    }


}
