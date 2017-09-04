package com.project;

import java.awt.image.BufferedImage;

public class ActionBox {
	private ImageHandler img;
	private int x;
	private int y;
	private String actionName;
	private boolean openCrewSlot = true;
	
	private DraggableIcon crew = null;
	
	public ActionBox(BufferedImage img, int x, int y, Actionable actionable) {
		this.img = new ImageHandler(x, y, img, true, EntityID.UI);
		this.img.start();
		this.x = x;
		this.y = y;
		this.actionName = actionName;
	}
	
	public void setCrew(DraggableIcon crew) {
		if (openCrewSlot) {
			this.crew = crew;
			openCrewSlot = false;
		}
	}
	public void removeCrew() {
		this.crew = null;
		openCrewSlot = true;
	}

	public static void delete(ActionBox box) {
		ImageHandler.delete(box.img);
		DraggableIcon.delete(box.crew);
		box = null;
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public DraggableIcon getCrew() {
		return crew;
	}

	public int getHeight() {
		return img.getHeight();
	}

	public int getWidth() {
		return img.getWidth();
	}

	public boolean isOpen() {
		return openCrewSlot;
	}

	
}
