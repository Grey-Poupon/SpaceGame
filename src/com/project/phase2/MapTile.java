package com.project.phase2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

public class MapTile {
	public Polygon hex; 
	public ArrayList<MapObject> objects = new ArrayList<MapObject>();
	Random rand = new Random();
	
	public MapTile(Polygon hex) {
		super();
		this.hex = hex;
		if(rand.nextInt(10)==5) {
			objects.add(new MapObject());
		}
	}
	
	public void render(Graphics g) {
		if(!isEmpty()) {
			g.setColor(Color.GREEN);
			g.fillPolygon(hex);
		}
		if(containsPlayer()) {
			g.setColor(Color.BLUE);
			g.fillPolygon(hex);
		}
		g.setColor(Color.RED);
		g.drawPolygon(hex);
	}
	
	public boolean isEmpty() {
		return(objects.size()==0);
	}
	public boolean containsPlayer() {
		for(int i = 0;i<objects.size();i++) {
			if(objects.get(i) instanceof MapShip) {
				if(((MapShip)objects.get(i)).isPlayerShip()) {
					return true;
				}
			}
		}
		return false;
	}

	public void addObject(MapObject object) {
		objects.add(object);
		object.setTileContained(this);
	}
	
	public void removeObject(MapObject object){
		objects.remove(object);
	}
	
	
	

}
