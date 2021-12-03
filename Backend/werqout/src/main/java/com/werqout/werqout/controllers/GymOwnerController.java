package com.werqout.werqout.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.werqout.werqout.repository.GymOwnerRepository;
import com.werqout.werqout.repository.TeamRepository;

import java.util.List;

import javax.transaction.Transactional;

import com.werqout.werqout.models.GymOwner;
import com.werqout.werqout.models.Team;

@RestController
@RequestMapping("/gymOwner")
@Api(value = "GymOwnerController", tags = {"gymOwner"}, description = "REST APIs related to Gym Owner Entity")
public class GymOwnerController {

    @Autowired
    GymOwnerRepository gymOwnerRepository;

    @Autowired
    TeamRepository teamRepository;
    
    @GetMapping("")
    public List<GymOwner> getAllGyms() {
    	return gymOwnerRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Gets a Gym Owner from the database by their ID", response = Iterable.class, tags = "getGymOwner")
    public GymOwner getGymOwner(@PathVariable long id){
        return gymOwnerRepository.findById(id);
    }


    @GetMapping("/{id}/gymName")
    @ApiOperation(value = "Gets the name of the gym the gymOwner owns", response = Iterable.class, tags = "getGymName")
    String getGymName(@PathVariable long id){
        if (gymOwnerRepository.findById(id) == null){
            return "No such gymOwner";
        }
        return gymOwnerRepository.findById(id).getGymName();
    }

    @PostMapping("/")
    @ApiOperation(value = "Creates a Gym Onwer in the database", response = Iterable.class, tags = "getGymOwner")
    GymOwner createGymOwner(@RequestBody GymOwner gymOwner){
        gymOwnerRepository.save(gymOwner);
        return gymOwnerRepository.findById(gymOwner.getId());
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update's a Gym Onwer in the database", response = Iterable.class, tags = "updateGymOwner")
    GymOwner updateGymOwner(@PathVariable long id, @RequestBody GymOwner gymOwner){
        GymOwner toUpdate = gymOwnerRepository.findById(id);
        if(toUpdate == null){
            return null;
        }
        gymOwner.setId(id);
        gymOwnerRepository.save(gymOwner);
        return gymOwnerRepository.findById(id);
    }

    @GetMapping("/{id}/teams")
    @ApiOperation(value = "Gets the teams that train at the gym- gym is refrenced from the gymID", response = Iterable.class, tags = "getTeams")
    public List<Team> getTeams(@PathVariable long id) {
    	return gymOwnerRepository.findById(id).getTeams();
    }

    @PostMapping("/{id}/teams/{teamId}")
    @ApiOperation(value = "Adds a team (refrenced by the team ID) to the list of teams that train at the gym (referenced by id)", response = Iterable.class, tags = "addTeam")
    public List<Team> addTeam(@PathVariable long id, @PathVariable long teamId) {
    	GymOwner go = gymOwnerRepository.findById(id);
        Team team = teamRepository.findById(teamId);
    	go.addTeam(team);
    	gymOwnerRepository.save(go);
    	return gymOwnerRepository.findById(id).getTeams();
    }
    
    @Transactional
    @DeleteMapping("/{id}/teams/{teamId}")
    @ApiOperation(value = "Removes a team (refrenced by the team ID) from the list of teams that train at the gym (referenced by gymID)", response = Iterable.class, tags = "removeTeam")
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
    @ApiOperation(value = "Deletes a Gym Owner (refrenced by id) from the database", response = Iterable.class, tags = "deleteGymOwner")
    String deleteGymOwner(@PathVariable long id){
        if (gymOwnerRepository.findById(id) == null){
            return "No such gymOwner";
        }
        String user = gymOwnerRepository.findById(id).getUserName();
        gymOwnerRepository.deleteById(id);
        return "Gym Owner: " + user + " deleted successfully";
    }

    /** Rating Methods*/

	@PutMapping("/{id}/rate") //path is rate?rating=4 but 4 is the rating you want to give
	@ApiOperation(value = "Updates the Gym Owners rating", response = Iterable.class, tags = "rateGymOwner")
	public void rateGymOwner(@PathVariable long id, @RequestParam(required=true, name="rating") int rating){
		GymOwner go = gymOwnerRepository.findById(id);
		if (go == null){
			System.out.println("No such coach");
		}
		go.rate(rating);
		gymOwnerRepository.save(go);
	}

	@GetMapping("{id}/getRating")
	@ApiOperation(value = "Gets the Gym Owner's rating", response = Iterable.class, tags = "getGymOwnerRating")
	public double getGymOwnerRating(@PathVariable long id){
		GymOwner gymOwner = gymOwnerRepository.findById(id);
		if (gymOwner == null){
			System.out.println("No such Gym Owner");
		}
		return gymOwner.getRating();
	}
    

}
