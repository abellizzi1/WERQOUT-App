package com.werqout.werqout.models;

/**
 * Simple class to be used in AthleteDM, represents a single message
 * 
 * @author evanu
 */

public class AthleteMessage {
	
	private String data;
	
	private Athlete from;
	
	public AthleteMessage(Athlete from, String data) {
		this.from = from;
		this.data = data;
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
	
}
