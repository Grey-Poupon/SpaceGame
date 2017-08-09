package com.project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class Animation {
	private String path;
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
	int xStartGap;
	int yStartGap;
	int xGap;
	int yGap;
	private float scale = 1;
	private BufferedImage spritesheet;
	private BufferedImage sprite;
	private ImageObserver observer;// any observer that wants to be notified when the this terrain is rendered
	private List<Animation> nextAnimations = new ArrayList<Animation>();
	

	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles,int xStartGap, int yStartGp,int xGap,int yGap, int frameRate, int xCoordinate, int yCoordinate,float scale,int NoOfloops,boolean firstAnimation) {
		this.path = path;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.noVertTiles = noVertTiles;
		this.noHorizTiles = noHorizTiles;
		this.ticksPerFrame = 60/frameRate;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.xStartGap = xStartGap;
		this.yStartGap = yStartGp;
		this.xGap = xGap;
		this.yGap = yGap;
		this.framesLeft = NoOfloops<0 ? -1 : NoOfloops*noHorizTiles*noVertTiles;
		this.scale = scale;
		setSpritesheet(path);
		setSprite();
		if(firstAnimation) {
			start();
		}
	}
	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int frameRate, int xCoordinate, int yCoordinate,float scale,int NoOfloops,boolean firstAnimation,Animation[] anims) {
		this.path = path;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.noVertTiles = noVertTiles;
		this.noHorizTiles = noHorizTiles;
		this.ticksPerFrame = 60/frameRate;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.framesLeft = NoOfloops*noHorizTiles*noVertTiles;
		this.nextAnimations = Arrays.asList(anims);
		this.scale = scale;
		setSpritesheet(path);
		setSprite();
		if(firstAnimation) {
			start();
		}
	}
	
	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int frameRate, int xCoordinate, int yCoordinate,float scale,int NoOfloops,boolean firstAnimation,List<Animation> anims) {
		this.path = path;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.noVertTiles = noVertTiles;
		this.noHorizTiles = noHorizTiles;
		this.ticksPerFrame = 60/frameRate;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.framesLeft = NoOfloops*noHorizTiles*noVertTiles;
		this.nextAnimations =  anims;
		this.scale = scale;
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
		this.nextAnimations = anims;
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
	public void render(Graphics g) {
		g.drawImage(sprite, xCoordinate, yCoordinate,Math.round(sprite.getWidth()*scale),Math.round(sprite.getHeight()*scale),observer);
	}
	public static void delete(Animation anim) {
		Handler.anims.remove(anim);
		anim = null;
		
	}
	public void tick() {
		tickCounter++;
		xCoordinate+=xVel;
		yCoordinate+=yVel;
		if(tickCounter==ticksPerFrame) {
			nextSprite();
			tickCounter=0;
			
			if(framesLeft>0) { //  if its not an infiniteloop
				framesLeft--;
				if (framesLeft < 1) {
					if (nextAnimations.size()>1) {
						Animation next = nextAnimations.get(0);
						List<Animation> anns = nextAnimations.subList(1, nextAnimations.size());
						next.addAnims(anns);
					}
					if(nextAnimations.size()>0) {
						nextAnimations.get(0).start();
					}
					Animation.delete(this);
				}
			}	
		}
	}
}
