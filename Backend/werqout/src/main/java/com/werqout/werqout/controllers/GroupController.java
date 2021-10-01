package com.werqout.werqout.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.werqout.werqout.repository.GroupRepository;
import com.werqout.werqout.models.Group;

import java.util.List;

@RestController
public class GroupController {
	
	GroupRepository groupRepository;
	
	@GetMapping("/groups")
	List<Group> getGroups(){
		return groupRepository.findAll();
	}
	
	@GetMapping("/groups/{id}")
	Group findGroup(@PathVariable int id) {
		return groupRepository.findById(id);
	}
	
	@PostMapping("/groups")
	Group createGroup(@RequestBody Group group) {
		groupRepository.save(group);
		return groupRepository.findById(group.getId());
	}
	
	@PutMapping("/groups/{id}")
	Group updateGroup(@PathVariable int id, @RequestBody Group group) {
		Group toUpdate = groupRepository.findById(id);
		if(toUpdate == null)
			return null;
		groupRepository.save(group);
		return groupRepository.findById(id);
	}
	
	@DeleteMapping("/groups/{id}")
	String deleteGroup(@PathVariable int id) {
		groupRepository.deleteById(id);
		return "Group: " + groupRepository.findById(id).getName() + " deleted successfully!";
	}
	
}