package com.project.phase2;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import com.project.Handleable;

public class Map implements Handleable {
	
	public Map() {
		
	}
	
	public Point mapOffSet = new Point(50,50); 
	public int height = 10;
	public int width  = 10;
	public int hexSide = 40;
	public Point hexSize = new Point((int) (hexSide*Math.sqrt(3)),2*hexSide);
	public ArrayList<ArrayList<MapTile>> hexes = new ArrayList<ArrayList<MapTile>>();
	
	public void generateHexMap(MapShip ship) {
		//generates a HEX based map of dim: height, width
		for(int x = 0; x<width;x++) {
			ArrayList<MapTile> m1 = new ArrayList<MapTile>();
			for(int y=0;y<height;y++) {
				MapTile m;
				if(y%2==0) {
					m= new MapTile( hexagon(x*hexSize.x,(float) ((0.75)*y*hexSize.y)));
				}
				else {
					m = new MapTile(hexagon((float) ((x+0.5)*hexSize.x),(float) ((0.75)*(y)*hexSize.y)));
				}
				m1.add(m);
			}
			hexes.add(m1);
		}
		//place the player ship.
		hexes.get(2).get(2).addObject(ship);
	}
	//generates a hexagon at position x, y
	public Polygon hexagon(float x,float y) {
		Polygon h = new Polygon();

		for (int i = 0; i < 6; i++){
			h.addPoint((int)(mapOffSet.x+x + hexSide * Math.sin(i * 2 * Math.PI / 6)),
					  (int)(mapOffSet.y+y + hexSide * Math.cos(i * 2 * Math.PI / 6)));
		}
		return h;
	}
	
	public void render(Graphics g) {
		//loops through a list and renders the objects in the list
		for(int i =0 ;i <hexes.size();i++) {
			for(int j= 0 ; j<hexes.get(i).size();j++) {
				hexes.get(i).get(j).render(g);
			}
		}
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
}


