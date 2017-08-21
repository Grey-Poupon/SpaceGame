package com.project;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Animation implements Handleable {
	private static final int DEFAULT_SPEED = 5;
	private int tileWidth;
	private int tileHeight;
	private int noVertTiles;
	private int noHorizTiles;
	private int ticksPerFrame;
	private int framesLeft;
	private int tickCounter = 0;
	private int xTile = 0 ;
	private int yTile = 0 ;
	private float xCoordinate;
	private float yCoordinate;
	private float xVel=0;
	private float yVel=0;
	private float xPixelsToMove =-1;
	private float yPixelsToMove =-1;
	private float xStart,xEnd;
	private float yStart,yEnd;
	private String path;
	private boolean moving = false;
	private Rectangle2D mask;
	private AdjustmentID align;
	int xStartGap;
	int yStartGap;
	int xGap;
	int yGap;
	private float scale = 1;
	private BufferedImage spritesheet;
	private BufferedImage sprite;
	private ImageObserver observer;// any observer that wants to be notified when the this terrain is rendered
	private List<Animation> followingAnims = new ArrayList<Animation>();
	
	//16 stationary
	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles,int xStartGap, int yStartGp,int xGap,int yGap, int frameRate, float xCoordinate, float yCoordinate,float scale,int NoOfloops,boolean firstAnimation,AdjustmentID align,List<Animation> followingAnims) {

		this.path = path;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.noVertTiles = noVertTiles;
		this.noHorizTiles = noHorizTiles;
		this.ticksPerFrame = 60/frameRate;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.align = align;
		this.xStartGap = xStartGap;
		this.yStartGap = yStartGp;
		this.xGap = xGap;
		this.yGap = yGap;
		this.framesLeft = NoOfloops<0 ? -1 : NoOfloops*noHorizTiles*noVertTiles;
		this.scale = scale;
		this.followingAnims = followingAnims;
		setSpritesheet(path);
		setSprite();
		if(firstAnimation) {
			start();
		}
	}
	// 22 moving
	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles,int xStartGap, int yStartGp,int xGap,int yGap, int frameRate,float scale,float xStart,float xEnd, float yStart, float yEnd,float xVel,Rectangle2D mask,boolean firstAnimation,AdjustmentID align,List<Animation> followingAnims) {

		this.path = path;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.noVertTiles = noVertTiles;
		this.noHorizTiles = noHorizTiles;
		this.ticksPerFrame =60/frameRate;
		this.xCoordinate = xStart;
		this.yCoordinate = yStart;
		this.align = align;
		this.xStartGap = xStartGap;
		this.yStartGap = yStartGp;
		this.xGap = xGap;
		this.yGap = yGap;
		this.framesLeft = -1;
		this.scale = scale;
		this.xPixelsToMove = Math.abs(xEnd - xStart);
		this.yPixelsToMove = Math.abs(yEnd - yStart);;
		this.xVel = xVel;
		this.mask = mask;
		this.followingAnims = followingAnims;
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.xStart = xStart;
		this.xEnd = xEnd;
		
		if(xPixelsToMove!=0) {
			this.yVel = (yPixelsToMove*xVel)/(xPixelsToMove);}
		else {
			this.yVel = xVel; 
			this.xVel = 0;
			}
		
		if(xVel!=0) {this.moving = true;}
		setSpritesheet(path);
		setSprite();
		if(firstAnimation) {
			start();
		}
	}
	

	public void start() {
		Handler.addAnimation(this);
	}
	public void addAnims(List<Animation> anims) {
		this.followingAnims = anims;
	}
	public void setSpritesheet(String path) {
		this.spritesheet = ResourceLoader.images.get(path);
	}
	public void setSprite() {
		if(spritesheet!=null) {
			sprite = spritesheet.getSubimage(xStartGap+xTile*(tileWidth+xGap), yStartGap+yTile*(tileHeight+yGap), tileWidth, tileHeight);
		}
	}
	public void nextSprite() {
		xTile++;
		if(xTile>noHorizTiles-1) {
			xTile = 0;
			yTile++;
		}
		if(yTile>noVertTiles-1) {
			yTile = 0;
		}
		setSprite();
	}
	public void render(Graphics g2) {
		Graphics g = g2.create();
		if(mask!=null) {g.setClip(mask);}
		int xAdjustment =(int) (AdjustmentID.getXAdjustment(align)*tileWidth*scale);
		int yAdjustment =(int) (AdjustmentID.getYAdjustment(align)*tileHeight*scale);

		g.drawImage(sprite, (int)xCoordinate+xAdjustment, (int)yCoordinate+yAdjustment,Math.round(sprite.getWidth()*scale),Math.round(sprite.getHeight()*scale),observer);
	}
	public static void delete(Animation anim) {
		Handler.anims.remove(anim);
		anim = null;
		
	}
	public void tick() {
		tickCounter++;
		if(moving) {
			xCoordinate   += xVel;
			xPixelsToMove -= Math.abs(xVel);
			yCoordinate   += yVel;
			yPixelsToMove -= Math.abs(yVel);
		}
		if(tickCounter==ticksPerFrame) {
			nextSprite();
			tickCounter=0;
			if(moving) {
				if(xPixelsToMove < 1 && yPixelsToMove < 1 ) {
					if(followingAnims.size()>0) {
						followingAnims.get(0).setX(xCoordinate);
						followingAnims.get(0).sety(yCoordinate);
						followingAnims.get(0).start();
					}
					Animation.delete(this);
				}
			}
			else
			{
				if(framesLeft>0) { //  if its not an infiniteloop
					framesLeft--;
					if (framesLeft < 1) {
						if (followingAnims.size()>1) {
							Animation next = followingAnims.get(0);
							List<Animation> anns = followingAnims.subList(1, followingAnims.size());
							next.addAnims(anns);
						}
						if(followingAnims.size()>0) {
							followingAnims.get(0).start();
						}
						Animation.delete(this);
					}
				}
			}
		}
		
	}
	private void sety(float yCoordinate2) {
		this.yCoordinate =yCoordinate2;
		
	}
	private void setX(float xCoordinate2) {
		this.xCoordinate = xCoordinate2;
	}
	public float getYVel() {
		
		return yVel;
	}
	public float getXVel() {
		return xVel;
	}
	public float getYPixelsToMove() {
		// TODO Auto-generated method stub
		return yPixelsToMove;
	}
	public float getXPixelsToMove() {
		// TODO Auto-generated method stub
		return xPixelsToMove;
	}
	public Animation copy() {
		List<Animation> anims = new ArrayList<Animation>();
		for(Animation anim: followingAnims) {
			anims.add(anim.copy());
		}
		if(moving) {return new Animation(path, tileWidth, tileHeight, noVertTiles, noHorizTiles, xStartGap, yStartGap, xGap, yGap, 60/ticksPerFrame, scale ,xStart, xEnd, yStart,yEnd,xVel, mask, false,align, anims);}
		else 	   {return new Animation(path, tileWidth, tileHeight, noVertTiles, noHorizTiles, xStartGap, yStartGap, xGap, yGap, 60/ticksPerFrame, xCoordinate, yCoordinate, scale, framesLeft/(noHorizTiles*noVertTiles),       false,align, anims);}      
		
	}
	public void setYEnd(int y) {
		this.yPixelsToMove = Math.abs(y-yStart);
		
		if(y != yEnd || yVel == 0) {
			if(xVel == 0) {
				yVel = yVel == 0 ? DEFAULT_SPEED : yVel;
			}
			else {
				yVel = ((y-yStart)*Math.abs(xVel))/(xPixelsToMove);
			}
		}
		
		this.yEnd = y;

	}
	public void setYStart(int y) {
		this.yPixelsToMove = Math.abs(yEnd-y);
		
		if(y != yStart || yVel == 0) {
			if(xVel == 0) {
				yVel = yVel == 0 ? DEFAULT_SPEED : yVel;
			}
			else {
				yVel = (y-yStart*xVel)/(xPixelsToMove);
			}
		}
		this.yStart = y;
		this.yCoordinate = y;

		
		
	}
	public void setXEnd(int x) {
		this.xPixelsToMove = Math.abs(x-xStart);

		if(x != xEnd || xVel == 0) {
			if(yVel == 0) {
				xVel = xVel == 0 ? DEFAULT_SPEED : xVel;
			}
			else {
				xVel = (x-xStart*Math.abs(yVel))/(yPixelsToMove);
			}
		}
		
		this.xEnd = x;

	}
	public void setXStart(int x) {
		this.xPixelsToMove = Math.abs(xEnd-x);

		if(x != xStart || xVel == 0) {
			if(yVel == 0) {
				xVel = xVel == 0 ? DEFAULT_SPEED : xVel;
			}
			else {
				xVel = (x-xStart*Math.abs(yVel))/(yPixelsToMove);
			}
		}
		this.xStart = x;
		this.xCoordinate = x;

		
		
	}
	public int getTileWidth() {
		return sprite.getTileWidth();
	}
	public int getTileHeight() {
		return sprite.getTileHeight();
	}
}
