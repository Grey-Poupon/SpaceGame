package com.project.phase2;

public class MapShip extends MapObject{

	public boolean isPlayerShip = false;
	
	
	
	public MapShip(boolean b) {
		isPlayerShip = true;
	}
	
	public MapShip(MapTile mt) {
		tileContained = mt;
	}
	
	public boolean isPlayerShip() {
	 return isPlayerShip;
	}
	public void setIsPlayerShip(boolean b) {
		isPlayerShip = b;
	}
	
	
}
