package com.project;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import com.project.ship.Ship;

public class Star extends ImageHandler{
	private int boundaryLeft;
	private int boundaryRight;
	private int boundaryTop;
	private int boundaryBottom;
	private Color color;
	private Rectangle2D clippingArea;
	private static Random rand = new Random();
	private int rad =5;
	private Ship ship;
	private boolean isRock=false;
	private int speedscale = 20;
	
	public Star(int x, int y, String path, boolean visible,int left,int right,int top, int bottom,Ship ship) {
		super(x, y,visible,EntityID.star);
		this.boundaryLeft = left;
		this.boundaryBottom = bottom;
		this.ship = ship;
		this.xVel = ship.getSpeed()/speedscale;
		this.boundaryRight = right;
		this.boundaryTop = top;
		this.color = Color.white;
		this.ship = ship;
		this.setImg(ResourceLoader.getImage("res/spaceRock.png"));
		this.clippingArea = new Rectangle2D.Float(boundaryLeft,boundaryTop,boundaryRight-boundaryLeft,boundaryBottom-boundaryTop);
		this.setXVel(-(int)(2*Math.abs((rand.nextGaussian()+this.xVel))));
	}
	
	public void tick(){
		super.tick();
		
	}
	
	private void preRender() {
		if(this.xCoordinate+this.rad<boundaryLeft-getWidth()) {
			this.setxCoordinate(boundaryRight);
			this.setyCoordinate(boundaryTop+rand.nextInt(boundaryBottom-rad-boundaryTop));
			this.setXVel(-(int)(2*Math.abs((rand.nextGaussian()+ship.getSpeed()/speedscale))));
			if(rand.nextInt(100)==50) {
				color=new Color(rand.nextFloat(),0,rand.nextFloat()/2f);
				rad = rand.nextInt(15)+5;
				this.xVel*=0.2;
			}
			else if(rand.nextInt(400)==70){
				
				isRock = true;
				setXVel(getXvel()/5);
			}
			else {
				isRock = false;
				color=Color.white;
			}
		}
	}
	
	public void render(Graphics g1)
	{
		preRender();
		Graphics g = g1.create();
		if(isVisible()){
			
			g.setClip(clippingArea);
			if(isRock) {
				super.render(g);
			}else {
				g.setColor(color);	
					if(color== Color.white) {
						g.fillRect(this.xCoordinate, this.yCoordinate, 5, 5);
					}
					else {
						
						g.fillOval(this.xCoordinate, this.yCoordinate, rad, rad);
					}
				
			}	
					
		}
	};

}
