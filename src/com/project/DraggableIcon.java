 package com.project;

import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

public class DraggableIcon {
	private ImageHandler img;
	private Crew crew;
	private int xCoordinate;
	private int yCoordinate;
	private int startX;
	private int startY;
	private int width;
	private int height;
	
	private ActionBox actionBox = null;
	private Point mouse;
	private boolean snapped = false;;
	private static final int snapToRange = 25;
	private static final int boxLineWidth = 2;

	public DraggableIcon(ImageHandler img,Crew crew, int xCoordinate, int yCoordinate) {
		this.img         = img;
		this.crew		 = crew;
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
		ImageHandler.delete(img2.img);
		Handler.icons.remove(img2);
		img2 = null;
		
	}

	public void drop(int x, int y, List<ActionBox> actionBoxes) {
		snapped = false;
		for(int i = 0;i<actionBoxes.size();i++) {
			ActionBox box = actionBoxes.get(i);
			boolean leftWall   = x > box.getX() - snapToRange;
			boolean rightWall  = x < box.getX() + box.getWidth() + snapToRange;
			boolean topWall    = y > box.getY() - snapToRange;
			boolean bottomWall = y < box.getY() + box.getHeight() + snapToRange;
			boolean xp         = box.getLevelRequirement() <= getLevel(box.getStatType()) ;
			if(leftWall && rightWall && topWall && bottomWall && box.isOpen() && xp ) {
				snapped = true;
				
				// remove the crew from the current actionbox
				if(actionBox != null && actionBox !=  box) {
					actionBox.removeCrew();
				}
				// set new actionbox
				box.setCrew(this);
				actionBox = box;
				
				
				mouse                = null;
				this.xCoordinate     = box.getX() + boxLineWidth;
				this.yCoordinate     = box.getY() + boxLineWidth;
				this.img.xCoordinate = box.getX() + boxLineWidth;
				this.img.yCoordinate = box.getY() + boxLineWidth;
				break;
			}
		}
		if(!snapped) {
			if(actionBox != null) {actionBox.removeCrew();}
			actionBox = null;
			mouse                = null;
			this.xCoordinate     = startX;
			this.yCoordinate     = startY;
			this.img.xCoordinate = startX;
			this.img.yCoordinate = startY;
		}
	}

	private int getLevel(StatID statType) {
		return crew.getStat(statType);
	}

	public Crew getCrew() {
		return crew;
	}


	


	
}
