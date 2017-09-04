package com.project.ship;


import java.awt.Graphics;

import com.project.Handleable;
import com.project.Slottable;

public class Slot implements Handleable{
	private int x;
	private int y;
	private int relX;
	private int relY;
	private int width;
	private int height;
	private Slottable slotItem;
	private int layerIndex;
	private boolean isFront;
	private float z;
	
	public Slot(int x, int y,int relX,int relY,int size,int index,boolean isFront,float z) {
		this.x = x;
		this.y = y;
		this.relX = relX;
		this.relY = relY;
		this.setLayerIndex(index);
		this.height = size;
		this.width = size;
		this.isFront = isFront;
		this.z = z;
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
		this.slotItem.setSlot(this);
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

	public boolean isFront() {
		return isFront;
	}

	public void setFront(boolean isFront) {
		this.isFront = isFront;
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		slotItem.getSlotItemBody().tick();
		
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return z;
	}

}
