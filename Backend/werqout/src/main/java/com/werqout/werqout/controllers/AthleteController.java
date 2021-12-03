package com.werqout.werqout.controllers;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

import com.werqout.werqout.repository.AthleteDMRepository;
import com.werqout.werqout.repository.AthleteMessageRepository;
import com.werqout.werqout.repository.AthleteRepository;
import com.werqout.werqout.repository.TeamRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.werqout.werqout.models.Athlete;
import com.werqout.werqout.models.AthleteDM;
import com.werqout.werqout.models.AthleteMessage;
import com.werqout.werqout.models.Team;


@Api(value = "AthleteController", tags = {"Athlete"}, description = "REST APIs related to Athlete Entity")
@RestController
@RequestMapping("/athletes")
public class AthleteController {
    
    @Autowired
    AthleteRepository athleteRepository;
    
    @Autowired
    AthleteDMRepository athleteDMRepository;
    
    @Autowired
    AthleteMessageRepository athleteMessageRepository;
    
    //Ta suggested to have this
    @RequestMapping(value="/all", method=RequestMethod.GET)
    @ApiOperation(value = "Get list of Athletes in the database", response = Iterable.class, tags = "getAllAthletes")
    public List<Athlete> getAllAthletes(){
        return athleteRepository.findAll();
    }
    //Ta suggested to have this
    @RequestMapping(value="post/{id}/{usr}/{email}/{pwd}/{team}", method=RequestMethod.GET)
    @ApiOperation(value = "Get an Athlete in the System by a certain path", response = Iterable.class, tags = "getAthleteByPath")
    public Athlete postAthleteByPath(@PathVariable long id,@PathVariable String usr,@PathVariable String email,@PathVariable String pwd){
        Athlete athlete = new Athlete(id,usr,email,pwd);
        athleteRepository.save(athlete);
        return athlete;
    }    


    @GetMapping("/{id}")
    @ApiOperation(value = "Get an Athlete by their ID", response = Iterable.class, tags = "getAthlete")
    public Athlete getAthlete(@PathVariable long id) {
    	return athleteRepository.findById(id);
    }

    @PostMapping("")
    @ApiOperation(value = "Creates an Athlete in the database", response = Iterable.class, tags = "createAthlete")
    public Athlete createAthlete(@RequestBody Athlete newAthlete){
        athleteRepository.save(newAthlete);
        return newAthlete;
    }
    
    @PutMapping("/{id}")
    @ApiOperation(value = "Updates an Athlete by their ID", response = Iterable.class, tags = "updateAthlete")
    public Athlete updateAthlete(@PathVariable long id, @RequestBody Athlete athlete) {
    	if(athleteRepository.findById(id) == null)
    		return null;
    	athlete.setId(id);
    	athleteRepository.save(athlete);
    	return athleteRepository.findById(id);
    }
    
    @Transactional
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes an Athlete from the database by their ID", response = Iterable.class, tags = "deleteAthlete")
    public String deleteAthlete(@PathVariable long id) {
    	if(athleteRepository.findById(id) == null)
    		return "No Such Athlete";                       //putting .get() here to resolve cannot convert from optional<>...
    	String athleteName = athleteRepository.findById(id).getUserName();
    	athleteRepository.deleteById(id);
    	return "Athlete: " + athleteName + " Deleted";
    }


  
    @GetMapping("/{id}/teams")
    @ApiOperation(value = "Get the team the Athlete belongs to", response = Iterable.class, tags = "getGroups")
    public List<Team> getGroups(@PathVariable long id) {
    	return athleteRepository.findById(id).getTeams();
    }
    
    @PostMapping("/{id}/teams")
    @ApiOperation(value = "Adds an Athlete to a team", response = Iterable.class, tags = "addGroup")
    public List<Team> addGroup(@PathVariable long id, @RequestBody Team team) {

    	Athlete athlete = athleteRepository.findById(id);
    	athlete.addTeam(team);
    	athleteRepository.save(athlete);
    	return athleteRepository.findById(id).getTeams();
    }
    

    @Transactional
    @DeleteMapping("/{id}/teams/{teamID}")
    @ApiOperation(value = "Deletes an Athlete from a team", response = Iterable.class, tags = "removeTeam")
    public String removeTeam(@PathVariable int id, @PathVariable long teamID) {
    	Athlete athlete = athleteRepository.findById(id);
        Team team = teamRepository.findById(teamID);
        if (team != null && athlete != null){
            athlete.removeTeam(team);
    	    athleteRepository.save(athlete);
            return "Athlete " + athlete.getUserName() + " from " + team.getName() +" has been deleted.";
        }
        else{
            return "Team or Athlete doesn't exist";
        }
    	
    }
    
    // Methods that control DMs ===============================================================================
    
    /**
     * getAllDMs
     *   Get all dm objects this athlete is currently mapped to
     * 
     * @param id
     *   id of athlete whose dms you want to get
     * @return
     *   list of AthleteDM objects, represents all users the athlete is messaging
     */
    
    @GetMapping("/{id}/dms")
    @ApiOperation(value = "Get all DM relationships belonging to this user", response = Iterable.class, tags = "getOpenDMs")
    public List<AthleteDM> getAllDMs(@PathVariable long id) {
    	return athleteRepository.findById(id).getOpenDMs();
    }
    
    @GetMapping("/dms/{id}")
    public List<AthleteMessage> getMessages(@PathVariable long id){
    	return athleteDMRepository.findById(id).getMessages();
    }
    
    /**
     * createDM
     *   Create a new DM object and map it to two athletes
     * 
     * @param id0
     *   id of first athlete to be mapped to new DM
     * @param id1
     *   id of second athlete to be mapped to new DM
     */
    
    @PostMapping("/{id0}/dms/{id1}")
    @ApiOperation(value = "Add new DM relationship between first id and second id", response = Iterable.class, tags = "createDM")
    public void createDM(@PathVariable long id0, @PathVariable long id1) {
    	Athlete athlete0 = athleteRepository.findById(id0);
    	Athlete athlete1 = athleteRepository.findById(id1);
    	
    	List<Athlete> a0dms = athlete0.getAthletesDMing();
    	
    	for(Athlete i : a0dms) {
    		if(i.equals(athlete1)) {
    			throw new IllegalArgumentException("Athletes already DMing");
    		}
    	}
    	
    	AthleteDM dm = new AthleteDM(athlete0, athlete1);
    	
    	athleteDMRepository.save(dm);
    	athlete0.addDM(dm);
    	athlete1.addDM(dm);
    	
    	athleteRepository.save(athlete0);
    	athleteRepository.save(athlete1);
    	
    	//AthleteDM newDM = new AthleteDM(athlete0, athlete1);
    	
    	//athlete0.addDM(newDM);
    	//athlete1.addDM(newDM);
    	
    	//athleteDMRepository.save(newDM);
    	//athleteRepository.save(athlete0);
    	//athleteRepository.save(athlete1);
    	
    }
    
    /**
     * sendDM
     *   Sends a new dm from the specified athlete to the specified dm
     * 
     * @param AthleteId
     *   Athlete who this dm is coming from
     * @param DMId
     *   DM which the message is being sent to
     * @param text
     *   Data to be sent
     */
    
    @PutMapping("/{AthleteId}/dms/{DMId}")
    @ApiOperation(value = "Send DM in existing relationship", response = Iterable.class, tags = "sendDM")
    public void sendDM(@PathVariable long AthleteId, @PathVariable long DMId, @RequestBody String text) {
    	Athlete from = athleteRepository.findById(AthleteId);
    	AthleteDM dm = athleteDMRepository.findById(DMId);
    	AthleteMessage message = new AthleteMessage(from, text, dm);
    	athleteMessageRepository.save(message);
    	dm.sendMessage(message);
    	athleteDMRepository.save(dm);
    }
    
}
