package com.project.ship;

import java.awt.Point;

import com.project.Crew;

public class Room {
	private Point Location;
	private int damagableRadius;
	private int damageMod;
	private int efficiency;
	private Crew roomLeader;
	
	public Point getLocation() {
		return Location;
	}
	public int getDamagableRadius() {
		return damagableRadius;
	}
	public void setDamagableRadius(int damagableRadius) {
		this.damagableRadius = damagableRadius;
	}
	public int getDamageMod() {
		return damageMod;
	}
	public void setDamageMod(int damageMod) {
		this.damageMod = damageMod;
	}
	public void setStatModifiers(String dictKey, float modifier) {
		// TODO Auto-generated method stub
		
	}
	public int getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(int efficiency) {
		this.efficiency = efficiency;
	}
	public Crew getRoomLeader() {
		return roomLeader;
	}
	public void setRoomLeader(Crew roomLeader) {
		this.roomLeader = roomLeader;
	}
	public void UseRoom() {
		
	}
	

}
