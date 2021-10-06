package com.werqout.werqout.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.werqout.werqout.repository.GymOwnerRepository;

import java.util.List;

import com.werqout.werqout.models.GymOwner;

@RestController
@RequestMapping("/gymOwner")
public class GymOwnerController {

    @Autowired
    GymOwnerRepository gymOwnerRepository;

    @GetMapping("/{id}")
    public GymOwner getGymOwner(@PathVariable long id){
        return gymOwnerRepository.findById(id);
    }

    // @GetMapping("/{id}/groups")
    //TODO - create a request that gets a list of the groups.


    @GetMapping("/{id}/gymName")
    String getGymName(@PathVariable long id){
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

    @DeleteMapping("/id")
    String deleteGymOwner(@PathVariable long id){
        gymOwnerRepository.deleteById(id);
        return "Gym Owner: " + gymOwnerRepository.findById(id).getUserName() + " deleted successfully";
    }


}
