package com.project.ship;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import com.project.Crew;

public class Room {
	private Point Location;
	private int damagableRadius;
	private int damageMod;
	private int efficiency;
	private Crew roomLeader;
	ArrayList<Crew> crewInRoom = new ArrayList<Crew>();
	
	public Room(Point location) {
		this.Location =location;
	}
	
	public void addCrew(Crew crew) {
		crewInRoom.add(crew);
	}
	public void removeCrew(Crew crew) {
		crewInRoom.remove(crew);
	}
	
	public ArrayList<Crew> getCrewInRoom() {
		return crewInRoom;
	}
	public void setCrewInRoom(ArrayList<Crew> crewInRoom) {
		this.crewInRoom = crewInRoom;
	}
	public void setLocation(Point location) {
		Location = location;
	}
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
	public ArrayList<String> getOptions() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
