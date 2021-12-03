package com.werqout.werqout.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Simple class to be used in AthleteDM, represents a single message
 * 
 * @author evanu
 */

@Entity
public class AthleteMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String data;
	
	// Athlete whom this message is from
	@ManyToOne
	@JoinColumn(name = "athlete_id")
	private Athlete from;
	
	// DM which this message is in
	@ManyToOne
	@JoinColumn(name = "dm_id")
	@JsonIgnore
	private AthleteDM dm;
	
	public AthleteMessage(Athlete from, String data, AthleteDM dm) {
		this.from = from;
		this.data = data;
		this.dm = dm;
	}
	
	public AthleteMessage() {}
	
	public long getID() {
		return id;
	}
	
	public void setID(long id) {
		this.id = id;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public Athlete getFrom() {
		return from;
	}
	
	public void setFrom(Athlete from) {
		this.from = from;
	}
	
	@JsonIgnore
	public AthleteDM getDM() {
		return dm;
	}
	
	public void setDM(AthleteDM dm) {
		this.dm = dm;
	}
	
}
