package com.werqout.werqout.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.werqout.werqout.repository.AthleteRepository;
import com.werqout.werqout.models.Athlete;

import java.util.List;

@RestController
public class AthleteController {
    
    @Autowired
    AthleteRepository athleteRepository;

    @GetMapping("/")
    public List<Athlete> getAthlete() {
        //need to have a method that returns username and email and make sure it matches with the database
        return athleteRepository.findAll();
    }

    @PostMapping("/createAthlete")
    public Athlete createAthlete(@RequestBody Athlete newAthlete){
        athleteRepository.save(newAthlete);
        return newAthlete;
    }
    
    
    
}
