package com.project.phase2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;

public class MapTile {
	public Polygon hex; 
	public ArrayList<MapObject> objects = new ArrayList<MapObject>();
	Random rand = new Random();
	public boolean isHovered =false;
	public Color col = Color.RED;
	public Map map;
	public Point mapPos;
	public MapTile(Polygon hex,int x,int y,Map map) {
		super();
		this.map= map;
		this.hex = hex;
		this.mapPos = new Point(x,y);
//		if(rand.nextInt(10)==5) {
//			objects.add(new MapObject(this));
//		}
	}
	
	public void addObject() {
		objects.add(MapObject.generateRandomObject(this));
	}
	
	
	public void renderTile(Graphics g) {
		g.setColor(col);
		g.drawString(mapPos.x+":"+mapPos.y, hex.getBounds().x+hex.getBounds().width/2, hex.getBounds().y+hex.getBounds().height/2);
		
		if(containsPlayer()) {
			g.setColor(Color.BLUE);
			g.fillPolygon(hex);
			
		}
		if(!isEmpty()) {
			g.fillPolygon(hex);
			
			if(!containsPlayer())g.setColor(Color.GREEN);
			
		}
		if(isHovered) {
			g.setColor(new Color(0,0,0.5f,0.5f));
			g.fillPolygon(hex);
		}
		g.drawPolygon(hex);
		
		
		
	}

	public void renderObjects(Graphics g) {
		for(int i =0;i<objects.size();i++) {
			if(objects.get(i) instanceof MapShip &&!containsPlayer()) g.setColor(Color.red);
			objects.get(i).render(g);
		}
	}

	public void setObjectPos(MapObject ob ) {
		ob.objImg.setxCoordinate((int) (hex.getBounds().x+hex.getBounds().getWidth()/2-ob.objImg.getWidth()/2));
		ob.objImg.setyCoordinate((int) (hex.getBounds().y +hex.getBounds().getHeight()/2-ob.objImg.getHeight()/2));
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
		setObjectPos(object);
	}

	public void removeObject(MapObject object){
		objects.remove(object);
	}

	public boolean isHovered() {
		return isHovered;
	}

	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}
	
}
