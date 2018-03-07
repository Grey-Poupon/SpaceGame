package com.project.phase2;

public class Star extends MapObject {

	public Star(MapTile c) {
		super(c);
		objImg.changeImage("res/mapStar.png",true);
	}
	
	public void interact(MapShip ship){
		
	}

}
