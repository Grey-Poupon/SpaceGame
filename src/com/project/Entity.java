package com.project;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entity {
	protected EntityID id;

	protected int xCoordinate;//  top left coordinates of where the image is to be placed
	protected int yCoordinate;
	protected int xVel;//  speed of mob
	protected int yVel;
	
	
	
	public void tick(){	
		xCoordinate+=xVel;
		yCoordinate+=yVel;
	};

	public void render(Graphics g)
	{
		if(visible){
				g.drawImage(img, xCoordinate, yCoordinate,Math.round(img.getWidth()*scale),Math.round(img.getHeight()*scale), observer);

			
		}
	};
	
	public void changeImage(String newPath){
		this.setImg(newPath);
	}

	protected float scale = 1;
	private EntityID ID;
	
	
	private ImageObserver observer;// any observer that wants to be notified when the this terrain is rendered
	private BufferedImage img; 
	private boolean visible = true;
	
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public int getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public ImageObserver getObserver() {					// getters and setters for all variables
		return observer;
	}
	public void setObserver(ImageObserver observer) {
		this.observer = observer;
	}
	public BufferedImage getImg() {
		return img;
	}
	public void setImg(String path) {
		try {
			this.img = ImageIO.read(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		};
	}

	public Entity(int x, int y , String path, boolean visible, EntityID ID){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.ID = ID;
		setImg(path);
		Handler.addEntity(this);
		}
	
	public Entity(int x, int y , boolean visible, EntityID ID){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.ID = ID;
		Handler.addEntity(this);
		}
	public Entity(int x, int y , String path, boolean visible, float scale, EntityID ID){		// constructor for scaled mobs
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.scale = scale;
		this.ID = ID;
		setImg(path);
		Handler.addEntity(this);
		}
	
	public Object getID() {
		// TODO Auto-generated method stub
		return this.id;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public EntityID getEntityID() {
		return ID;
	}
	public void setEntityID(EntityID mobID) {
		this.ID = mobID;
	}
	public void setScale(float scale){
		this.scale = scale;
	}
	public float getScale(){
		return this.scale;
	}

}
