package com.project;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Animation implements Handleable {
	private int tileWidth;
	private int tileHeight;
	private int noVertTiles;
	private int noHorizTiles;
	private int ticksPerFrame;
	private int framesLeft;
	private int tickCounter = 0;
	private int xTile = 0 ;
	private int xEnd;
	private int yEnd;
	private int yTile = 0 ;
	private int xCoordinate;
	private int yCoordinate;
	private int xVel=0;
	private int yVel=0;
	private int xPixelsToMove =-1;
	private int yPixelsToMove =-1;
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
	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles,int xStartGap, int yStartGp,int xGap,int yGap, int frameRate, int xCoordinate, int yCoordinate,float scale,int NoOfloops,boolean firstAnimation,AdjustmentID align,List<Animation> followingAnims) {

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
	//20 moving
	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles,int xStartGap, int yStartGp,int xGap,int yGap, int frameRate, int xCoordinate, int yCoordinate,float scale,int xPixelsToMove,int yPixelsToMove,int xVel,int yVel,Rectangle2D mask,boolean firstAnimation,AdjustmentID align,List<Animation> followingAnims) {

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
		this.framesLeft = -1;
		this.scale = scale;
		this.xPixelsToMove = xPixelsToMove;
		this.yPixelsToMove = yPixelsToMove;
		this.yVel = yVel;
		this.xVel = xVel;
		this.mask = mask;
		this.moving = true;
		this.followingAnims = followingAnims;
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

		g.drawImage(sprite, xCoordinate+xAdjustment, yCoordinate+yAdjustment,Math.round(sprite.getWidth()*scale),Math.round(sprite.getHeight()*scale),observer);
	}
	public static void delete(Animation anim) {
		Handler.anims.remove(anim);
		anim = null;
		
	}
	public void tick() {
		tickCounter++;
		if(moving) {
			xCoordinate   += xVel;
			xPixelsToMove -= xVel;
			yCoordinate   += yVel;
			yPixelsToMove -= yVel;
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
	private void sety(int yCoordinate2) {
		this.yCoordinate =yCoordinate2;
		
	}
	private void setX(int xCoordinate2) {
		this.xCoordinate = xCoordinate2;
	}
	public int getYVel() {
		
		return yVel;
	}
	public int getXVel() {
		return xVel;
	}
	public int getYPixelsToMove() {
		// TODO Auto-generated method stub
		return yPixelsToMove;
	}
	public int getXPixelsToMove() {
		// TODO Auto-generated method stub
		return xPixelsToMove;
	}
	public Animation copy() {
		if(moving) {return new Animation(path, tileWidth, tileHeight, noVertTiles, noHorizTiles, xStartGap, yStartGap, xGap, yGap, 60/ticksPerFrame, xCoordinate, yCoordinate, scale, xPixelsToMove,yPixelsToMove,xVel,yVel, mask, false,align, followingAnims);}
		else 	   {return new Animation(path, tileWidth, tileHeight, noVertTiles, noHorizTiles, xStartGap, yStartGap, xGap, yGap, 60/ticksPerFrame, xCoordinate, yCoordinate, scale, framesLeft/(noHorizTiles*noVertTiles),       false,align, followingAnims);}      
		
	}
}
