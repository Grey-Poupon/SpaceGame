package com.project.phase2;

import com.project.ship.Ship;

public class MapShip extends MapObject{

	public boolean isPlayerShip = false;
	private Ship ship;
	
	
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
	
	
	
	public boolean isPlayerShip() {
	 return isPlayerShip;
	}
	public void setIsPlayerShip(boolean b) {
		isPlayerShip = b;
	}
	public void moveTile(MapTile mt) {
		if(this.isPlayerShip()){

			for(int i =0;i<mt.objects.size();i++) {
				if(!mt.objects.get(i).tileContained.containsPlayer())mt.objects.get(i).interact(this);
			}
		}
		super.moveTile(mt);
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
	
}
