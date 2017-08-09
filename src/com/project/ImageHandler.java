package com.project;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageHandler implements Handleable {
	protected EntityID id;

	protected int xCoordinate;//  top left coordinates of where the image is to be placed
	protected int yCoordinate;
	protected float zCoordinate;
	protected int xVel;//  speed of mob
	protected int yVel;
	protected float xScale = 1;
	protected float yScale = 1;
	private EntityID ID;
	private ImageObserver observer;// any observer that wants to be notified when the this terrain is rendered
	private BufferedImage img; 
	private boolean visible = true;
	private Shape clip;
	
	
	public void tick(){	
		xCoordinate+=xVel;
		yCoordinate+=yVel;
	};

	public void render(Graphics g)
	{
		if(visible){
			if(clip != null) {g.setClip(clip);}
				g.drawImage(img, xCoordinate, yCoordinate,Math.round(img.getWidth()*xScale),Math.round(img.getHeight()*yScale), observer);	
		}
	};
	
	public void changeImage(String newPath, boolean visible){
		this.setImg(newPath);
		this.setVisible(visible);
		
	}
	

	public ImageHandler(int x, int y , String path, boolean visible, EntityID ID){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.ID = ID;
		setImg(path);
		Handler.addLowPriorityEntity(this);
		}
	
	public ImageHandler(int x, int y , boolean visible, EntityID ID){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.ID = ID;
		Handler.addLowPriorityEntity(this);
		}
	public ImageHandler(int x, int y , String path, boolean visible, float xscale, float yscale, EntityID ID){		// constructor for scaled mobs
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.xScale = xscale;
		this.yScale = yscale;
		this.ID = ID;
		setImg(path);
		Handler.addLowPriorityEntity(this);
		}
	public ImageHandler(int x, int y , String path, boolean visible, float scale, EntityID ID){		// constructor for scaled mobs
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.xScale = scale;
		this.yScale = scale;
		this.ID = ID;
		setImg(path);
		Handler.addLowPriorityEntity(this);
		}
	public ImageHandler(int x, int y , String path, boolean visible, EntityID ID, String priority){		// constructor for mob
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.visible = visible;
		this.ID = ID;
		setImg(path);
		if(priority.equals("low")){
			Handler.addLowPriorityEntity(this);
			}
		else{
			Handler.addHighPriorityEntity(this);	
			}
		}
	
	
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	public int getyCoordinate() {
		return yCoordinate;
	}
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	public void setZ(float z) {
		this.zCoordinate = z;
	}
	public float getZ() {
		return zCoordinate;
	}
	public void setXVel(int xVel) {
		this.xVel =xVel;
	}
	public int getXvel() {
		return xVel;
	}
	public void setYVel(int yVel) {
		this.yVel =yVel;
	}
	public int getYvel() {
		return xVel;
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
	public void setImg(BufferedImage img) {
		this.img = img;
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
	public void setXScale(float scale){
		this.xScale = scale;
	}
	public float getXScale(){
		return this.xScale;
	}
	public void setYScale(float scale){
		this.yScale = scale;
	}
	public float getYScale(){
		return this.yScale;
	}
	public void changeMask(int x , int y, int width, int height) {
		this.clip = new Rectangle2D.Float(x, y, width, height);
	}
	public void removeMask() {
		this.clip = null;		
	}

	public int getxCoordinate() {
		return xCoordinate;
	}
	public boolean isClicked(int x, int y) {
		if(x>xCoordinate && x<xCoordinate+(img.getWidth()*xScale) && y>yCoordinate && y<yCoordinate+(img.getHeight()*yScale)) {
			double X = ((x-xCoordinate)/xScale);
			double Y = ((y-yCoordinate)/yScale);
			int newX = (int)X;
			int newY = (int)Y;
			if( (img.getRGB(newX,newY)>>24) != 0x00 ) {
			    return true;
			}
		}
		return false;
	}

}
