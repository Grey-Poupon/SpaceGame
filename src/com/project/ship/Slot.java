package com.project.ship;

import com.project.Slottable;

public class Slot {
	private int x;
	private int y;	
	private int width;
	private int height;
	private Slottable slotItem;
	
	
	public Slot(int x, int y,int width,int height) {
		this.x = x;
		this.y = y;
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public Slot(int x, int y,char type) {
		this.x = x;
		this.y = y;
		if(type == 's') {
			this.setWidth(20);
			this.setHeight(20);			
		}
		if(type == 'm') {
			this.setWidth(30);
			this.setHeight(30);			
		}
		if(type == 'l') {
			this.setWidth(40);
			this.setHeight(40);			
		}
		
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public Slottable getSlotItem() {
		return slotItem;
	}

	public void setSlotItem(Slottable slotItem) {
		this.slotItem = slotItem;
	}

}
