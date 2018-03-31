package com.project;

import java.awt.image.BufferedImage;

import com.project.battle.BattleScreen;
import com.project.ship.Room;

public class ActionBox {
	private ImageHandler img;
	private int x;
	private int y;
	private CrewAction action;
	private boolean openCrewSlot = true;
	private Text	actionText;
	private DraggableIcon crew = null;
	private Room room;
	private boolean moveCrew = false;
	private Handler handler;
	
	
	public ActionBox(Handler handler,BufferedImage img, int x, int y, CrewAction action,Room room,BattleScreen bs) {
		this.handler = handler;
		this.img = new ImageHandler(x, y, img, true, EntityID.UI);

		this.img.start(handler,false);
		this.room =room;
		this.x 			= x;
		this.y 		    = y;
		this.action     = action;
		actionText      = new Text(handler,getName(), true, x+getWidth(), y);
		this.action.setActionBox(this);

	}
	
	public ActionBox(Handler handler,BufferedImage img, int x, int y, CrewAction action,Room room,BattleScreen bs,boolean moveCrew) {
		this.handler = handler;
		this.img = new ImageHandler(x, y, img, true, EntityID.UI);
		this.img.start(handler,false);
		this.room       = room;
		this.moveCrew   = moveCrew;
		this.x 			= x;
		this.y 		    = y;
		this.action     = action;
		actionText      = new Text(handler,getName(), true, x+getWidth(), y);
		this.action.setActionBox(this);

	}
	
	public void setCrew(DraggableIcon crew) {
		if (openCrewSlot) {
			this.crew = crew;
			openCrewSlot = false;
			if(getActor()!=crew.getCrew()) {
				setActor(crew.getCrew());
			}
		}
	}
	
	public void removeCrew() {
		this.crew = null;
		openCrewSlot = true;
		removeActor();
	}
	
	public void removeActor() {
		action.removeActor();
	}
	/*Remove crew from box, and place them back*/
	public void resetBox(){
		if(crew != null){
			crew.reset();
		}
	}
	public static void delete(ActionBox box) {
		//box.removeCrew();
		if(box.img       !=null) {ImageHandler .delete(box.handler,box.img );}
		if(box.crew      !=null) {DraggableIcon.delete(box.crew);}
		if(box.actionText!=null) {Text         .delete(box.actionText);}
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

	public String getName() {
		return action.getName();
	}

	public void setActor(Crew actor) {
		action.setActor(actor);
	}

	public int getLevelRequirement() {
		return action.getLevelRequirement();
	}



	public StatID getStatType() {
		return action.getStatType();
	}

	public Crew getActor() {
		return action.getActor();
	}

	public CrewAction getAction() {
		return action;
	}

	public void setAction(CrewAction action) {
		this.action = action;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public void setMoveCrew(boolean b) {
		moveCrew = b;
	}

	public boolean getMoveCrew() {
		return moveCrew;
	}
	public void setImg(BufferedImage actionImg) {
		this.img.setImg(actionImg);
	}
	
}
