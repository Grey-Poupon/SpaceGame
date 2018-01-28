package com.project.phase2;

import java.awt.Rectangle;

import com.project.ImageHandler;

public class MapObject {
	
	public ImageHandler objImg; 
	public MapTile tileContained;
	
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

	public static MapObject generateRandomObject(MapTile mapTile) {
		MapObject m = new MapObject(mapTile);
		return m;
	}
	
	public void interact(MapShip ship) {
		
	}

}
