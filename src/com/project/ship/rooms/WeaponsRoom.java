package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.ResourceLoader;
import com.project.ship.Room;
import com.project.weapons.Weapon;

public class WeaponsRoom extends Room {

	private List<Weapon> weapons = new ArrayList<>();
	private List<Weapon> frontWeapons = new ArrayList<>();
	private List<Weapon> backWeapons = new ArrayList<>();
	public List<Weapon> getWeapons() {
		return weapons;
	}

	public void setWeapons(ArrayList<Weapon> weapons) {
		this.weapons = weapons;
	}

	public WeaponsRoom(Weapon[] we,String name) {
		super(name);
		weapons = (ArrayList<Weapon>) Arrays.asList(we);
		
	}
	public WeaponsRoom(List<Weapon> frontWeapons,List<Weapon> backWeapons,String name) {
		super(name);
		this.frontWeapons=frontWeapons;
		this.backWeapons =backWeapons ;
		weapons.addAll(backWeapons);
		weapons.addAll(frontWeapons);


		
	}
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/weaponsRoomIcon.png");
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


	
	
	

}
