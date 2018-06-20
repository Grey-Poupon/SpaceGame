package com.project.phase2;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.project.ImageHandler;
import com.project.ResourceLoader;

public class MapObject {
	
	public ImageHandler objImg; 
	public MapTile tileContained;
	public boolean hasQuest = false;
	public Quest quest;
	
	public MapObject(MapTile c) {
		tileContained = c;
		Rectangle r =tileContained.hex.getBounds();
		objImg = new ImageHandler(0,0,"res/planet.png",true,null);
		c.setObjectPos(this);
	}
	
	public void moveTile(MapTile tile) {
		tileContained.removeObject(this);
		tile.addObject(this);
	}
	
	public MapTile getTileContained() {
		return tileContained;
	}
	public void setTileContained(MapTile tile) {
		tileContained = tile;
	}
	
	public void takeTurn() {
		
	}

	public static MapObject generateRandomObject(MapTile mapTile) {
		MapObject m = new MapObject(mapTile);
		return m;
	}
	
	public void interact(MapShip ship) {
		
	}

	public void render(Graphics g) {
		if(hasQuest) {
			renderQuest(g);
		}
		objImg.render(g);
	}
	
	public Quest getQuest() {
		return quest;
	}
	
	public void renderQuest(Graphics g) {
		if(quest.isAccepted()) {
			g.drawImage(ResourceLoader.getImage("res/questIconAccepted.png"), (int)(objImg.getxCoordinate()+objImg.getOnScreenWidth()/2-ResourceLoader.getImage("res/questIconAccepted.png").getWidth()/2),(int)( objImg.getyCoordinate()-ResourceLoader.getImage("res/questIconAccepted.png").getHeight()), ResourceLoader.getImage("res/questIconAccepted.png").getWidth(),ResourceLoader.getImage("res/questIconAccepted.png").getHeight(), null);
		}
		else {
			g.drawImage(ResourceLoader.getImage("res/questIcon.png"), objImg.getxCoordinate()+objImg.getWidth()/2-ResourceLoader.getImage("res/questIcon.png").getWidth()/2, objImg.getyCoordinate()-ResourceLoader.getImage("res/questIcon.png").getHeight(), ResourceLoader.getImage("res/questIcon.png").getWidth(),ResourceLoader.getImage("res/questIcon.png").getHeight(), null);
		}
		
	}

}
