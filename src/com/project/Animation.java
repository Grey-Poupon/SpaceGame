package com.project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Animation {
	private String path;
	private int tileWidth;
	private int tileHeight;
	private int noVertTiles;
	private int noHorizTiles;
	private int ticksPerFrame;
	private int tickCounter = 0;
	private int xTile = 0 ;
	private int yTile = 0 ;
	private int xCoordinate;
	private int yCoordinate;
	private BufferedImage spritesheet;
	private BufferedImage sprite;
	private ImageObserver observer;// any observer that wants to be notified when the this terrain is rendered
	

	public Animation(String path, int tileWidth, int tileHeight, int noVertTiles, int noHorizTiles, int frameRate, int xCoordinate, int yCoordinate) {
		this.path = path;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.noVertTiles = noVertTiles;
		this.noHorizTiles = noHorizTiles;
		this.ticksPerFrame = 60/frameRate;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		setSpritesheet(path);
		setSprite();
		Handler.addAnimation(this);
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
	public void tick() {
		tickCounter++;
		if(tickCounter==ticksPerFrame) {
			nextSprite();
			tickCounter=0;
		}
	}
}
