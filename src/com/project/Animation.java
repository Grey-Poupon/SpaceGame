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
	private int yTile = 0 ;
	private int xCoordinate;
	private int yCoordinate;
	private BufferedImage spritesheet;
	private BufferedImage sprite;
	private ImageObserver observer;// any observer that wants to be notified when the this terrain is rendered
	private List<Animation> nextAnimations = new ArrayList<Animation>();
	

	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int frameRate, int xCoordinate, int yCoordinate,int framesLeft,boolean firstAnimation) {
		this.path = path;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.noVertTiles = noVertTiles;
		this.noHorizTiles = noHorizTiles;
		this.ticksPerFrame = 60/frameRate;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.framesLeft = framesLeft;
		setSpritesheet(path);
		setSprite();
		if(firstAnimation) {
			start();
		}
	}
	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int frameRate, int xCoordinate, int yCoordinate,int framesLeft,boolean firstAnimation,Animation[] anims) {
		this.path = path;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.noVertTiles = noVertTiles;
		this.noHorizTiles = noHorizTiles;
		this.ticksPerFrame = 60/frameRate;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.framesLeft = framesLeft;
		this.nextAnimations = Arrays.asList(anims);
		setSpritesheet(path);
		setSprite();
		if(firstAnimation) {
			start();
		}
	}
	
	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int frameRate, int xCoordinate, int yCoordinate,int framesLeft,boolean firstAnimation,List<Animation> anims) {
		this.path = path;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.noVertTiles = noVertTiles;
		this.noHorizTiles = noHorizTiles;
		this.ticksPerFrame = 60/frameRate;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.framesLeft = framesLeft;
		this.nextAnimations =  anims;
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
		try {
			this.spritesheet = ImageIO.read(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	public void setSprite() {
		if(spritesheet!=null) {
			sprite = spritesheet.getSubimage(xTile*tileWidth, yTile*tileHeight, tileWidth, tileHeight);
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
		g.drawImage(sprite, xCoordinate, yCoordinate,Math.round(sprite.getWidth()),Math.round(sprite.getHeight()),observer);
	}
	public static void delete(Animation anim) {
		Handler.anims.remove(anim);
		anim = null;
		
	}
	public void tick() {
		tickCounter++;
		if(tickCounter==ticksPerFrame) {
			nextSprite();
			tickCounter=0;
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
