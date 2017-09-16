package com.project.ship;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

import com.project.Crew;
import com.project.StatID;

public abstract class Room {
	private String roomName;
	private Point location;
	private int damagableRadius;
	private int damageMod;
	private int efficiency;
	private Crew roomLeader;
	private int size = 9;
	ArrayList<Crew> crewInRoom = new ArrayList<Crew>();
	
	public Room(String name) {
		this.roomName = name;
	}
	public Room() {
		
	}
	
	public BufferedImage getIcon() {
		return null;
	}
	
	public void addCrew(Crew crew) {
		crew.setRoomIn(this);
		crewInRoom.add(crew);
	}
	public void removeCrew(Crew crew) {
		crewInRoom.remove(crew);
	}
	public void moveCrew(Crew crew, Room moveTo) {
		this.removeCrew(crew);
		moveTo.addCrew(crew);
	}
	
	public ArrayList<Crew> getCrewInRoom() {
		return crewInRoom;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public Point getLocation() {
		return location;
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
		roomLeader.setRoomLeader(true, this);
		this.roomLeader = roomLeader;
		this.crewInRoom.add(roomLeader);
	}
	public void UseRoom() {
		
	}
	public ArrayList<String> getOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setStatModifiers(StatID dictKey, float modifier) {
		// TODO Auto-generated method stub
		
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	public String getRoomName() {
		return roomName;
	}
	

}
