package com.project.phase2;

import com.project.ship.Ship;

public class MapShip extends MapObject{

	public boolean isPlayerShip = false;
	private Ship ship;
	private int money = 200;
	
	public MapShip(MapTile c,boolean b,Ship ship) {
		super(c);
		this.ship = ship;
		isPlayerShip = b;
		if(b) {
			objImg.setImg("res/matron3/mergedimage.png");
		}
		else {
			objImg.setImg("res/ships/insectoid.png");
		}
		
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
		if(this.isPlayerShip()&&mt!=this.tileContained){
			mt.map.movePlayerShip(this, mt);
		}
		super.moveTile(mt);
	}
	
	public int getMoney() {
		return money;
	}
	
	public void interact(MapShip ship) {
		if(!this.isPlayerShip) {
			tileContained.removeObject(this);
			Phase2.battle(ship,this);
		}
	}
	
	public Ship getShip() {
		return ship;
	}



	public void incrementMoney(int i) {
		money+=i;
		
	}
	
}
