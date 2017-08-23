package com.project.ship;


import com.project.Slottable;

public class Slot {
	private int x;
	private int y;
	private int relX;
	private int relY;
	private int width;
	private int height;
	private Slottable slotItem;
	private int layerIndex;
	
	
	public Slot(int x, int y,int relX,int relY,int width,int height) {
		this.x = x;
		this.y = y;
		this.relX = relX;
		this.relY = relY;
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public Slot(int x, int y,int relX,int relY,char type,int index) {
		this.x = x;
		this.y = y;
		this.relX = relX;
		this.relY = relY;
		this.setLayerIndex(index);
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
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
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

	public int getRelX() {
		return relX;
	}

	public void setRelX(int relX) {
		this.relX = relX;
	}

	public int getRelY() {
		return relY;
	}

	public void setRelY(int relY) {
		this.relY = relY;
	}

	public int getLayerIndex() {
		return layerIndex;
	}

	public void setLayerIndex(int layerIndex) {
		this.layerIndex = layerIndex;
	}

}
