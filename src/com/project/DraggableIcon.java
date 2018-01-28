 package com.project;

import java.awt.Point;
import java.util.List;
import com.project.battle.BattleUI;

public class DraggableIcon {
	private ImageHandler img;
	private Crew crew;
	private int xCoordinate;
	private int yCoordinate;
	private int startX;
	private int startY;
	private ActionBox startingBox;
	private int width;
	private int height;
	private ActionBox actionBox = null;
	private Point mouse;
	private boolean snapped = false;;
	private static final int snapToRange = 30;
	private static final int boxLineWidth = 0;
	private static Handler handler;
	
	public DraggableIcon(ImageHandler img,Handler handler,Crew crew, int xCoordinate, int yCoordinate) {
		this.handler = handler;
		this.img         = img;
		this.crew		 = crew;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.startX      = xCoordinate;
		this.startY      = yCoordinate;
		this.width       = img.getWidth();
		this.height      = img.getHeight();
		handler.icons.add(this);// fuk u adam
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
		ImageHandler.delete(handler,img2.img);
		handler.icons.remove(img2);
		img2 = null;		
	}

	public void drop(List<ActionBox> actionBoxes) {
 		snapped = false;
 
		for(int i = 0;i<actionBoxes.size();i++) {
			ActionBox box = actionBoxes.get(i);
			
			boolean walls      = Math.abs(xCoordinate - box.getX()) < snapToRange;
			boolean ceiling    = Math.abs(yCoordinate - box.getY()) < snapToRange;
			boolean xp         = box.getLevelRequirement() <= getLevel(box.getStatType()) ;
			boolean boxEmpty   = ((box.isOpen()||box.getActor()==crew));
			
			if(walls && ceiling && boxEmpty && xp) {
				snapped = true;
				
				// remove the crew from the current actionbox
				if(actionBox != null && actionBox !=  box) {
					actionBox.removeCrew();
				}
				
				// for room swapping
				if(box.getMoveCrew() && (crew.getRoomIn() != box.getRoom() || crew.isMoving()) ) {
					// if you put them back in the room they started in
					if(box.getRoom()==crew.getRoomMovingFrom()) {
						crew.setMoving(false);
					}
					else {
					// if you're moving them to a new room
						crew.setMoving(true);
						crew.setRoomMovingTo(box.getRoom());
					}					
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
			reset();
		}
	}

	private int getLevel(StatID statType) {
		return crew.getStat(statType);
	}

	public Crew getCrew() {
		return crew;
	}

	public void moveTo(int x, int y) {
		this.xCoordinate     = x;
		this.yCoordinate     = y;
		this.img.xCoordinate = x;
		this.img.yCoordinate = y;
		
	}

	public void setActionBox(ActionBox actionBox) {
		this.actionBox = actionBox;
	}

	public void reset() {
		if(actionBox != null) {actionBox.removeCrew();}
		actionBox = null;

		mouse     = null;
		if(startingBox==null){
			moveTo(startX,startY);
		}
		else if(startingBox.isOpen()) {
				moveTo(startingBox.getX(),startingBox.getY());
				startingBox.setCrew(this);
				actionBox = startingBox;
				

			}
			else {
				boolean placed = false;
				for(ActionBox box:BattleUI.actionBoxes) {
					if(box.getRoom() == startingBox.getRoom() && box.isOpen()) {
						box.setCrew(this);
						moveTo(box.getX(), box.getY());
						placed = true;
						break;
					}
				}
				if(!placed) {
					System.out.println("this is spooky");
				}

		}
		crew.setMoving(false);
		
	}
	public void setStart(int x, int y) {
		this.startX = x;
		this.startY = y;
	}
	public void setStartBox(ActionBox box) {
		this.startingBox = box;
	}
}
