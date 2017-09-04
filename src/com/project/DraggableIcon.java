package com.project;

import java.awt.Graphics;
import java.awt.Point;

public class DraggableIcon {
	private ImageHandler img;
	private boolean beingDragged = false;
	private int xCoordinate;
	private int yCoordinate;
	private int startX;
	private int startY;
	private int width;
	private int height;
	private Point mouse;
	
	
	
	public DraggableIcon(ImageHandler img, int xCoordinate, int yCoordinate) {
		this.img         = img;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.startX      = xCoordinate;
		this.startY      = yCoordinate;
		this.width       = img.getWidth();
		this.height      = img.getHeight();
		Handler.icons.add(this);// fuk u adam
	}


	public boolean isInside(int x, int y) {
		if(x>= xCoordinate && x<= xCoordinate+width && y>= yCoordinate && y<= yCoordinate+height) {
			return true;
		}
		return false;
	}
	
	public void drag(int x, int y) {
		beingDragged = true;
		if(mouse == null) {
			mouse = new Point(x,y);
		}
		else {
			this.xCoordinate += x-mouse.getX();
			this.yCoordinate += y-mouse.getY();
			
			this.img.xCoordinate += x-mouse.getX();
			this.img.yCoordinate += y-mouse.getY();
			
			mouse.x = x;
			mouse.y = y;
		}
	}


	public static void delete(DraggableIcon img2) {
		Handler.icons.remove(img2);
		ImageHandler.delete(img2.img);
		img2 = null;
		
	}


	public boolean isBeingDragged() {
		return beingDragged;
	}
	public void drop() {
		beingDragged = false;
		this.mouse.x         = startX;
		this.mouse.y         = startY;
		this.xCoordinate     = startX;
		this.yCoordinate     = startY;
		this.img.xCoordinate = startX;
		this.img.yCoordinate = startY;
	}


	
}
