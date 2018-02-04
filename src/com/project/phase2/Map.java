package com.project.phase2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

import com.project.Handleable;
import com.project.ImageHandler;
import com.project.Main;

public class Map implements Handleable {
	
	public Map() {
		
	}
	public ImageHandler bg = new ImageHandler(0,0,"res/star_field.png",true, null);
	public int height = 12;
	public int width  = 10;
	public static int hexSide = 65;
	public Point hexSize;
	public Point mapSize;
	public Area mapComposite;
	public Point mapOffSet = new Point((int) ((Main.WIDTH -Math.sqrt(3)*hexSide*(width-0.5))/2),(int)((Main.HEIGHT -hexSide*(height-2.75))/2));
	public ArrayList<ArrayList<MapTile>> hexes = new ArrayList<ArrayList<MapTile>>();
	public ArrayList<Ellipse2D> rings = new ArrayList<Ellipse2D>();
	public MapTile highLightedTile=null;
	public ArrayList<Integer> shortestPathX = null;
	public ArrayList<Integer> shortestPathY = null;
	public void generateHexMap() {
		//getDimensions for polygon used
		Polygon h = hexagonIso(0,0);
		hexSize = new Point(h.getBounds().width,h.getBounds().height);
		mapComposite = new Area();
		
		
		//generates a HEX based map of dim: height, width
		for(int y=0;y<height;y++) {
			ArrayList<MapTile> m1 = new ArrayList<MapTile>();
			
			for(int x = 0; x<width;x++) {
			
			
				MapTile m;
				if(y%2==0) {
					
					m= new MapTile( hexagonIso(x*hexSize.x,(float) ((0.75)*y*hexSize.y)),x,y);
				}
				else {
					m = new MapTile(hexagonIso((float) ((x+0.5)*hexSize.x),(float) ((0.75)*(y)*hexSize.y)),x,y);
				}
				m1.add(m);
				mapComposite.add(new Area(m.hex));
			}
			hexes.add(m1);
		}
		//place the player ship.
		
		//place an emeny
		hexes.get(8).get(8).addObject(new MapShip(hexes.get(5).get(5)));
		hexes.get(4).get(5).addObject(new Planet(hexes.get(4).get(5)));
		mapSize = new Point(mapComposite.getBounds().width,mapComposite.getBounds().height);
		//addOrbits();
	}
	
	public void randomlyPlaceShip(MapShip ship) {
		ship.moveTile(this.hexes.get(0).get(0));
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
	
	public void addOrbits() {
		Rectangle r= mapComposite.getBounds();
		Polygon h1 = hexes.get(4).get(4).hex;
		Point ringSize = new Point(6*hexSize.x,4*hexSize.y);
		Ellipse2D ring = new Ellipse2D.Float((float)h1.getBounds().getCenterX()-(float)ringSize.x/2,(float)h1.getBounds().getCenterY()-(float)ringSize.y/2,(float)ringSize.x,(float)ringSize.y);
//		Ellipse2D ring1 = new Ellipse2D.Float((float)r.getX()+3f,(float)r.getY()+3f,(float)r.width/4-6f,(float)r.height/4-6f);
		rings.add(ring);
		float tol=2;
//		rings.add(ring1);
		for(int i = 0; i<rings.size();i++) {
			Area o = new Area(rings.get(i));
			o.exclusiveOr(new Area(new Ellipse2D.Float((float)rings.get(i).getX()+tol,(float)rings.get(i).getY()+tol,-2*tol+(float)rings.get(i).getWidth(),-2*tol+(float)rings.get(i).getHeight())));
			for(int j = 0; j<hexes.size();j++) {
				for(int k =0;k<hexes.get(j).size();k++) {
					Area h = new Area(hexes.get(j).get(k).hex);
					h.intersect(o);
					if(!((h).isEmpty()||h.getBounds().height*h.getBounds().width<h1.getBounds().height*h1.getBounds().width/12)) {
						hexes.get(j).get(k).addObject();
//						hexes.get(j).get(k).col = Color.white;
					}
				}
				
			}
		}
		
	}
	
	public Polygon hexagonIso(float x, float y) {
		Polygon h = new Polygon();

		for (int i = 0; i < 6; i++){
			h.addPoint((int)(mapOffSet.x+x + hexSide * Math.sin(i * 2 * Math.PI / 6)),
					  (int)(mapOffSet.y+y + 0.5*hexSide * Math.cos(i * 2 * Math.PI / 6)));
		}
		return h;
	}
	
	
	
	
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g.create();
		bg.render(g);
		
		//loops through a list and renders the objects in the list
		for(int i =0 ;i <hexes.size();i++) {
			for(int j= 0 ; j<hexes.get(i).size();j++) {
//				hexes.get(i).get(j).render(g);
				hexes.get(i).get(j).renderTile(g);
			}
		}
		for(int i =0 ;i <hexes.size();i++) {
			for(int j= 0 ; j<hexes.get(i).size();j++) {
				hexes.get(i).get(j).renderObjects(g);
			}
		}
		for(int i =0;i<rings.size();i++) {
			g2d.setColor(Color.CYAN);
			g2d.draw(rings.get(i));
		}
		g.setColor(Color.white);
		renderShortestPath(g);
	
	}
	
	public void renderShortestPath(Graphics g) {
		if(shortestPathX == null || shortestPathY == null) {
			return;
		}
		for(int i = 0; i<shortestPathX.size()-1;i++) {
			g.drawLine(shortestPathX.get(i), shortestPathY.get(i), shortestPathX.get(i+1), shortestPathY.get(i+1));
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
	public void highlightTile(Point mousePosition) {
		for(int x = 0; x<width;x++) {
			for(int y= 0; y<height;y++) {
				MapTile t =hexes.get(y).get(x);
				if(t.hex.contains(mousePosition)) {
					t.setHovered(true);
					if(highLightedTile!=t) {
						highLightedTile = t;
						setShortestPath();	
					}
				}
				else {t.setHovered(false);}
			}
		}
		
	}
	
	public void setShortestPath() {
		shortestPathX = null;
		shortestPathY = null;
		MapTile playerCell = null;
		for(int i = 0; i<hexes.size();i++) {
			for(int j=0;j<hexes.get(i).size();j++){
				if(hexes.get(i).get(j).containsPlayer()) {
					playerCell = hexes.get(i).get(j);
				}
			}
		}
		if(playerCell ==null||playerCell == highLightedTile) {
			return;
		}
		Point temp = playerCell.mapPos.getLocation();
		int diffX = playerCell.mapPos.x-highLightedTile.mapPos.x;
		int diffY = playerCell.mapPos.y-highLightedTile.mapPos.y;
		int modX = 1;
		if(diffX!=0) {modX =  Math.abs(playerCell.mapPos.x-highLightedTile.mapPos.x)/(playerCell.mapPos.x-highLightedTile.mapPos.x);}
		int modY = 1;
		if(diffY!=0) {modY =  Math.abs(playerCell.mapPos.y-highLightedTile.mapPos.y)/(playerCell.mapPos.y-highLightedTile.mapPos.y);}
		ArrayList<Integer> xVal = new ArrayList<>();
		ArrayList<Integer> yVal = new ArrayList<>();
		xVal.add((int)(playerCell.hex.getBounds().x+playerCell.hex.getBounds().getWidth()/2));
		yVal.add((int)(playerCell.hex.getBounds().y+playerCell.hex.getBounds().getHeight()/2));
		while(!(temp.x == highLightedTile.mapPos.x&&temp.y==highLightedTile.mapPos.y)) {
			int xMove= 0;
			int yMove = 0;
			boolean xLarger = temp.x-highLightedTile.mapPos.x>temp.y-highLightedTile.mapPos.y;
			MapTile next = null;
			if(temp.getX()!=highLightedTile.mapPos.x) {
				if(modX==-1) {
					xMove = 1;
				}
				else {
					xMove = -1;
				}
			}
			if(temp.getY()!=highLightedTile.mapPos.getY()) {
				if(modY==-1) {
					yMove = 1;
				}
				else {
					yMove = -1;
				}
			}
			if(temp.getY()%2==0) {
				if(yMove+xMove==2) {
					if(xLarger) {
						xMove = 0;
					
					}
					else {xMove= 0;}				
					}
				if(yMove == -1 && xMove == 1) {
					if(xLarger) {
						yMove= 0;
					}
					else {
						xMove = 0;
					}
				}
				
			}
			if(temp.getY()%2==1 && ((yMove == 1 &&xMove ==-1)||(yMove==-1&&xMove==-1))) {
				xMove=0;
			}
			
			
			
			
			temp.translate(xMove, yMove);
			next = hexes.get(temp.y).get(temp.x);
		
			xVal.add((int)(next.hex.getBounds().x+next.hex.getBounds().getWidth()/2));
			
			yVal.add((int)( next.hex.getBounds().y+next.hex.getBounds().getHeight()/2));
		}
		shortestPathX = xVal;
		shortestPathY = yVal;
	}
	
	
	public static Map generateRandomMap() {
		Map m = new Map();
		m.generateHexMap();
		Random rand = new Random();
		int numObjects = 10;
		for(int i=0; i<numObjects;i++) {
			int randX = 0;
			int randY = 0;
			
			boolean isFilled = true;
			while(isFilled) {
				randX = rand.nextInt(m.hexes.size());
				randY = rand.nextInt(m.hexes.get(randX).size());
				if(m.hexes.get(randX).get(randY).isEmpty()) {
					isFilled =false;
				}
			}
			if(i!=0) {
				m.hexes.get(randX).get(randY).addObject();
			}
			else {
				m.hexes.get(randX).get(randY).addObject(new Portal(m.hexes.get(randX).get(randY),m));
			}
			
		}

		
		return m;
		
		
		
		
	}
	
	
	
	
}


