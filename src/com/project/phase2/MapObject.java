package com.project.phase2;

import java.awt.Polygon;
import java.awt.Rectangle;

import com.project.ImageHandler;

public class MapObject {
	
	public ImageHandler objImg; 
	public MapTile tileContained;

	
	public MapObject(MapTile c) {
		tileContained = c;
		Rectangle r =tileContained.hex.getBounds();
		objImg = new ImageHandler((int)(r.x),(int)(r.y -r.getHeight()),"res/planet.png",true,null);
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
