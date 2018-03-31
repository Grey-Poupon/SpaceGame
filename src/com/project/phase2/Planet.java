package com.project.phase2;

public class Planet extends MapObject{

	private ShopMenu shop;
	
	
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
