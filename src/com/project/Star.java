package com.project;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Star extends ImageHandler{
	private int boundaryLeft;
	private int boundaryRight;
	private int boundaryTop;
	private int boundaryBottom;
	private Color color;
	private Rectangle2D clippingArea;
	private static Random rand = new Random();
	private int vel;
	private int rad =0;
	
	public Star(int x, int y, String path, boolean visible,int left,int right,int top, int bottom,int  vel) {
		super(x, y, path, visible,EntityID.star);
		this.boundaryLeft = left;
		this.boundaryBottom = bottom;
		this.vel = vel;
		this.boundaryRight = right;
		this.boundaryTop = top;
		this.color = Color.white;
		this.clippingArea = new Rectangle2D.Float(boundaryLeft,boundaryTop,boundaryRight-boundaryLeft,boundaryBottom-boundaryTop);
		this.setXVel(-(int)(2*Math.abs((rand.nextGaussian()+vel))));
	}
	
	public void tick(){
		super.tick();
		if(this.xCoordinate+this.getImg().getWidth()<boundaryLeft-20) {
			this.setxCoordinate(boundaryRight);
			this.setyCoordinate(boundaryTop+rand.nextInt(boundaryBottom-this.getImg().getHeight()-boundaryTop));
			this.setXVel(-(int)(2*Math.abs((rand.nextGaussian()+vel))));
			if(rand.nextInt(100)==50) {
				color=new Color(rand.nextFloat(),0,rand.nextFloat()/2f);
				rad = rand.nextInt(15)+5;
				this.xVel*=0.2;
			}
			else {
				color=Color.white;
			}
		}
	}
	
	public void render(Graphics g1)
	{
		Graphics g = g1.create();
		if(isVisible()){
			g.setClip(clippingArea);
			g.setColor(color);	
				if(color== Color.white) {
					g.fillRect(this.xCoordinate, this.yCoordinate, 5, 5);
				}
				else {
					
					g.fillOval(this.xCoordinate, this.yCoordinate, rad, rad);
				}
				
					
		}
	};

}
