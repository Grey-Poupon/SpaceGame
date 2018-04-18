package com.project.phase2;

import java.util.ArrayList;

import com.project.ship.Ship;

public class MapPlayerShip extends MapShip {

	private ArrayList<Quest> quests = new ArrayList<Quest>();
	
	public MapPlayerShip(MapTile c, Ship ship) {
		super(c,ship);
		// TODO Auto-generated constructor stub
		isPlayerShip = true;
		objImg.setImg("res/matron3/mergedimage.png");	
		}
	
	public void interact(MapShip ship) {
		
	}

	public ArrayList<Quest> getQuests() {
		return quests;
	}

	public void setQuests(ArrayList<Quest> quests) {
		this.quests = quests;
	}
	public void addQuest(Quest q) {
		quests.add(q);
		q.setAccepted(true);
	}
	public void moveTile(MapTile mt) {
	//Allows for the ship to move within two tiles 2 denotes the number of tiles
			if(mt!=this.tileContained&&mt.map.shortestPathX.size()<=2+1){
				mt.map.movePlayerShip(this, mt);
				super.moveTile(mt);
			}
	}
}

