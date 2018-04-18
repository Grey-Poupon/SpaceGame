package com.project.phase2;

import java.util.ArrayList;
import java.util.Random;

import com.project.Crew;

public class Planet extends MapObject{

	private ShopMenu shop;
	private ArrayList<Crew> crew = new ArrayList<Crew>();
	private Random rand; 
	public Planet(MapTile tile) {
		super(tile);
		rand = new Random();
		hasQuest= rand.nextBoolean();
		
		objImg.changeImage("res/shop.png", true);
		shop = new ShopMenu();
		if(hasQuest) {
			quest =new Quest("Find your Ass.",shop.shopKeep);
			shop.setQuest(quest);
		}
	}
	
	public void interact(MapShip ship) {
		System.out.println("Hey there X plorer");
		shop.setShip((MapPlayerShip)ship);
		Phase2.setShop(shop);
		
	}

}
