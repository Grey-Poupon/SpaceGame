package com.project.phase2;

import java.util.ArrayList;

import com.project.Crew;

public class Planet extends MapObject{

	private ShopMenu shop;
	private ArrayList<Crew> crew = new ArrayList<Crew>();
	
	public Planet(MapTile tile) {
		super(tile);
		objImg.changeImage("res/shop.png", true);
		shop = new ShopMenu();
	}
	
	public void interact(MapShip ship) {
		System.out.println("Hey there X plorer");
		shop.setShip(ship);
		Phase2.setShop(shop);
		
	}

}
