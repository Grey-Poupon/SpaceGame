package com.project.phase2;

import com.project.ship.ResourcesID;
import com.project.ship.Ship;

public class MapShip extends MapObject{

	public boolean isPlayerShip = false;
	private Ship ship;
	private int money = 200;
	
	public MapShip(MapTile c,Ship ship) {
		super(c);
		this.ship = ship;
		objImg.setImg("res/ships/insectoid.png");
		
	}
	
	public void takeTurn() {
		if(!isPlayerShip) {
			tileContained.map.moveToRandomAdjacentTile(this);
		}
	}
	
	public boolean isPlayerShip() {
	 return isPlayerShip;
	}
	public void setIsPlayerShip(boolean b) {
		isPlayerShip = b;
	}
	public void moveTile(MapTile mt) {
		
			super.moveTile(mt);
		
	}
	
	public int getMoney() {
		return ship.getResource(ResourcesID.Money);
	}
	
	public void interact(MapShip ship) {
		
			tileContained.removeObject(this);
			Phase2.battle(ship,this);
		
	}
	
	public Ship getShip() {
		return ship;
	}



	public void incrementMoney(int i) {
		ship.incResource(ResourcesID.Money, i);
	}
	
}
