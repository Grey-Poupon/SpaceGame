package com.project.phase2;

import com.project.ImageHandler;

public class MapObject {
	
	public ImageHandler objImg; 
	public MapTile tileContained;

	
	public MapObject() {
		
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

}
