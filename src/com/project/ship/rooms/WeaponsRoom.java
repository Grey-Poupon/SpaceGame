package com.project.ship.rooms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.ship.Room;
import com.project.weapons.Weapon;

public class WeaponsRoom extends Room {

	private List<Weapon> weapons = new ArrayList<>();
	private List<Weapon> frontWeapons = new ArrayList<>();
	private List<Weapon> backWeapons = new ArrayList<>();
	private boolean isChased;
	public List<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}

	public WeaponsRoom(Point location) {
		super(location);
	}
	public WeaponsRoom(Weapon[] we,Point location) {
		super(location);
		weapons = (ArrayList<Weapon>) Arrays.asList(we);
	}
	public WeaponsRoom(List<Weapon> frontWeapons2,List<Weapon> backWeapons2,boolean isChased,Point location) {
		super(location);
		this.isChased = isChased;
		this.frontWeapons=backWeapons2;
		this.backWeapons = frontWeapons2;
		weapons.addAll(backWeapons2);
		weapons.addAll(frontWeapons2);
	}
	
	
	public void UseRoom() {
		
	}
	public ArrayList<String> getOptions() {
		for(int i = 0;i<weapons.size();i++) {
			
		}
		return null;
		
		
	}

	public List<Weapon> getFrontWeapons() {
		return frontWeapons;
	}

	public void setFrontWeapons(List<Weapon> frontWeapons) {
		this.frontWeapons = frontWeapons;
	}

	public List<Weapon> getBackWeapons() {
		return backWeapons;
	}

	public void setBackWeapons(List<Weapon> backWeapons) {
		this.backWeapons = backWeapons;
	}

	public boolean isChased() {
		return isChased;
	}

	public void setChased(boolean isChased) {
		this.isChased = isChased;
	}
	
	
	
	
	

}
