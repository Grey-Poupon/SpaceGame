package com.project.phase2;

import java.util.Random;

public class Planet extends MapObject{

	public Planet(MapTile tile) {
		super(tile);
	}
	
	public void interact(MapShip ship) {
		System.out.println("Hey there X plorer");
		Phase2.setShop(new ShopMenu());
		
	}

}
