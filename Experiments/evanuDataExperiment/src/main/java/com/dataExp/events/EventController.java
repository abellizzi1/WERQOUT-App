package com.dataExp.events;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;

/**
 * This class controls http requests at the /events directory. Capable of CRUDL using GET, POST, PUT, and DELETE requests
 * 
 * @author evanu
 *
 */

@RestController
public class EventController {
	
	// Java.utils HashMap of events, stores the data which has been collected in a convenient way
	HashMap<String, Event> eventList = new HashMap<String, Event>();
	
	
	
	/**
	 * getEveryEvent
	 * Handles get requests at "/events", just returns the whole list of events
	 * 
	 * @return List of all events
	 */
	@GetMapping("/events")
	public @ResponseBody HashMap<String, Event> getEveryEvent(){
		return eventList;
	}
	
	
	/**
	 * createEvent
	 * Handles post requests at "/events", takes an event object in the request body and adds it to the list
	 * 
	 * @param Event to be added to the List
	 * @return String confirming event addition
	 */
	@PostMapping("/events")
	public @ResponseBody String createEvent(@RequestBody Event event) {
		System.out.println(event);
		eventList.put(event.getName(), event);
		return "Event" + event.getName() + "added!";
	}
	
	
	/**
	 * getEvent
	 * Handles get requests at "/events/{name}", searches the list and returns an event whose name matches the PathVariable
	 * 
	 * @param Name to search for in the list (@PathVariable)
	 * @return Event with matching name
	 */
	@GetMapping("/events/{name}")
	public @ResponseBody Event getEvent(@PathVariable String name) {
		Event event = eventList.get(name);
		return event;
	}
	
	
	/**
	 * updateEvent
	 * Handles put requests at "/events/{name}", searches for an event whose name matches the PathVariable, replaces it with an event defined in the body
	 * 
	 * @param Name of event to update (@PathVariable)
	 * @param Event to update name's entry with (@RequestBody)
	 * @return Name of the event, by lookup in the list
	 */
	@PutMapping("/events/{name}")
	public @ResponseBody Event updateEvent(@PathVariable String name, @RequestBody Event e) {
		eventList.replace(name, e);
		return eventList.get(name);
	}
	
	
	/**
	 * deleteEvent
	 * Handles delete requests at "/events/{name}", searches for an event whose name matches the PathVariable and deletes it.
	 * 
	 * @param Name of event to delete (@PathVariable)
	 * @return Event List after deletion
	 */
	@DeleteMapping("/events/{name}")
	public @ResponseBody HashMap<String, Event> deleteEvent(@PathVariable String name) {
		eventList.remove(name);
		return eventList;
	}
	

}
