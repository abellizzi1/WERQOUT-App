package com.werqout.werqout.controllers;

import com.werqout.werqout.models.GymOwner;
import com.werqout.werqout.models.Team;

import java.util.List;

import javax.transaction.Transactional;

import com.werqout.werqout.models.Event;
import com.werqout.werqout.repository.GymOwnerRepository;
import com.werqout.werqout.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.werqout.werqout.repository.EventRepository;

/**
 * This class is to be used in conjunction with the gymo
 */
@Api(value = "EventController", tags = {"Event"}, description = "REST APIs related to Event Entity")
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    GymOwnerRepository gymOwnerRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EventRepository eventRepository;

     //Event routes
    /**
     * Gets all the events at the gym
     * @return events at the gym
     */
    @GetMapping("/all")
    @ApiOperation(value = "Get list of Events in the database", response = Iterable.class, tags = "getEvents")
    public List<Event> getEvents(){
        return eventRepository.findAll();
    }
    /**
     * Creates an event
     * @param event event that is being created
     * @return the new event
     */
    @PostMapping("/create")
    @ApiOperation(value = "Create an event in the database", response = Iterable.class, tags = "createEvent")
    public Event createEvent(@RequestBody Event event){
        Event newEvent = eventRepository.save(event);
        return eventRepository.findById(newEvent.getId());
    }
    /**
     * updates an event 
     * @param id event id
     * @param event event that is being updated
     * @return the new event
     */
    @PutMapping("/{id}")
    @ApiOperation(value = "Updates an event in the database", response = Iterable.class, tags = "updateEvent")
	Event updateEvent(@PathVariable long id, @RequestBody Event event) {
		Event toUpdate = eventRepository.findById(id);
		if(toUpdate == null)
			return null;
		event.setId(id);
		eventRepository.save(event);
		return eventRepository.findById(id);
	}
    /**
     * Deletes an event 
     * @param id id of the event being deleted
     * @return the response, either no such event or that the event has been deleted.
     */
    @Transactional
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes an event in the database", response = Iterable.class, tags = "deleteEvent")
    String deleteEvent(@PathVariable long id){
        Event event = eventRepository.findById(id);
        eventRepository.deleteById(id);
        String res = (event == null) ? "No such event" : "Event " + event.getId() + " has been deleted";
        return res;
    }

    // event methods that have to do with the gymowners
    /**
     * Gets the events at the gym
     * @param gymID id of the gym owner
     * @return list of events that that gym has
     */
    @GetMapping("/gym/{gymID}/events")
    @ApiOperation(value = "Gets the events at a gym by the gym's id", response = Iterable.class, tags = "getEventsAtGym")
    Event getEventsAtGym(@PathVariable long gymID){
        //todo- filter by date
       return gymOwnerRepository.findById(gymID).getEvent();
    }
    /**
     * Creates an event at the gym
     * @param gymID id of the gym that event is being held at
     * @param e event that is being created
     * @return  the new event
     */
    @PostMapping("/gym/{gymID}/event")
    @ApiOperation(value = "Creates an event at a gym", response = Iterable.class, tags = "createEvent")
    public Event createEvent(@PathVariable long gymID, @RequestBody Event e){
        GymOwner go = gymOwnerRepository.findById(gymID);
        if(go != null){
            go.setEvent(e);
            eventRepository.save(e);
            return eventRepository.findById(e.getId());
        }
        else{
            //No gym owner.
            System.out.println("No such gymOwner");
            return null;
        }
    }
    /**
     * Deletes the event from the gym
     * @param gymID gymId which holds the event
     * @param eventID eventID which is going to be deleted
     * @return Response of either no such gymOwner or event, or that the event has been deleted.
     */
    @Transactional
    @DeleteMapping("/gym/{gymID}/{eventID}")
    @ApiOperation(value = "Deletes an event in the database that a gym owner created", response = Iterable.class, tags = "deleteEvent")
    String deleteEvent(@PathVariable long gymID, @PathVariable long eventID){
        GymOwner go = gymOwnerRepository.findById(gymID);
        Event e = eventRepository.findById(eventID);
        if (go == null || e == null){
            return "no such gymOwner or event";
        }
        else{
        go.setEvent(null);
        gymOwnerRepository.save(go);
        return "Event " + e.getId() + " has been deleted";
        }
        
        
    }

    //events that have to do with the teams
    /**
     * Gets the events made by the team.
     * @param teamID
     * @return team's event.
     */
    @GetMapping("/team/{teamID}/events")
    @ApiOperation(value = "Gets the events created by the team by the team's id", response = Iterable.class, tags = "getTeamEvents")
    public Event getTeamEvents(@PathVariable long teamID){
        //todo- filter by date
       return teamRepository.findById(teamID).getEvent();
    }
    /**
     * Allows a team to create an event
     * @param teamID ID of the team that wants to creat the event
     * @param e event that is being created
     * @return new event
     */
    @PostMapping("/team/{teamID}/createEvent")
    @ApiOperation(value = "Team creates an event", response = Iterable.class, tags = "teamCreateEvent")
    public Event teamCreateEvent(@PathVariable long teamID,@RequestBody Event e){
        Team t = teamRepository.findById(teamID);

        if (t != null){
            t.setEvent(e);
            eventRepository.save(e);
            return eventRepository.findById(e.getId());
        }
        else{
            //No Team.
            System.out.println("No such Team");
            return null;
        }

    }
    
    /**
     * Deletes the event from the team
     * @param teamID ID of the team which holds the event
     * @param eventID eventID which is going to be deleted
     * @return Response of either no such team or event, or that the event has been deleted.
     */
    @Transactional
    @DeleteMapping("/team/{teamID}/{eventID}")
    @ApiOperation(value = "Deletes an event in the database that a team created", response = Iterable.class, tags = "deleteTeamEvent")
    String deleteTeamEvent(@PathVariable long teamID, @PathVariable long eventID){
        Team t = teamRepository.findById(teamID);
        Event e = eventRepository.findById(eventID);
        if (t == null || e == null){
            return "no such team or event";
        }
        else{
        t.setEvent(null);
        teamRepository.save(t);
        return "Event " + e.getId() + " has been deleted";
        }
        
        
    }



    
}
