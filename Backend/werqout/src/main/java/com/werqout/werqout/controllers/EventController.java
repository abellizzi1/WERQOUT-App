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


import com.werqout.werqout.repository.EventRepository;

/**
 * This class is to be used in conjunction with the gymo
 */
@RestController
@RequestMapping("/event")
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
    public List<Event> getEvents(){
        return eventRepository.findAll();
    }

    @PostMapping("/create")
    public Event createEvent(@RequestBody Event event){
        eventRepository.save(event);
        return eventRepository.findById(event.getId());
    }

    @PutMapping("/{id}")
	Event updateEvent(@PathVariable long id, @RequestBody Event event) {
		Event toUpdate = eventRepository.findById(id);
		if(toUpdate == null)
			return null;
		eventRepository.save(event);
		return eventRepository.findById(id);
	}

    @Transactional
    @DeleteMapping("/{id}")
    String deleteEvent(@PathVariable long id){
        Event event = eventRepository.findById(id);
        eventRepository.deleteById(id);
        String res = (event == null) ? "No such event" : "Event " + event.getId() + " has been deleted";
        return res;
    }

    // event methods that have to do with the gymowners
    /**
     * Gets the events at the gym
     * @param gymId id of the gym owner
     * @return list of events that that gym has
     */
    @GetMapping("/{gymID}/events")
    List<Event> getEventsAtGym(@PathVariable long gymID){
        //todo- filter by date
       return gymOwnerRepository.findById(gymID).getEvents();
    }

    @PostMapping("/{gymID}/event")
    public Event createEvent(@PathVariable long gymID, @RequestBody Event e){
        GymOwner go = gymOwnerRepository.findById(gymID);
        if(go != null){
            go.addEvent(e);
            eventRepository.save(e);
            return eventRepository.findById(e.getId());
        }
        else{
            //No gym owner.
            System.out.println("No such gymOwner");
            return null;
        }
    }

    @DeleteMapping("/{gymID}/event")
    String deleteEvent(@PathVariable long gymID, @RequestBody Event e){
        GymOwner go = gymOwnerRepository.findById(gymID);
        if (go != null && go.hasEvent(e)){
            go.removeEvent(e);
            gymOwnerRepository.save(go);
            return "Event " + e.getId() + " has been deleted";
        }
        else{
            return "GymOwner does not exist or doesn't have the event you specified.";
        }
        
    }

    //events that have to do with the teams

    
}
