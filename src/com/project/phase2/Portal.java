package com.project.phase2;

public class Portal extends MapObject{
	
	public Map toMap;
	public Map fromMap;
	
	public Portal(MapTile c, Map fromMap) {
		super(c);
		this.objImg.setImg("res/portalGate.png");
		this.fromMap= fromMap;
	}
	
	public void changeMap(MapShip ship) {
		toMap = Map.generateRandomMap();
		Phase2.getP().setCurrentMap(toMap);
	}
	
	public void interact(MapShip ship) {
		changeMap(ship);
	}
	
	
	
}
