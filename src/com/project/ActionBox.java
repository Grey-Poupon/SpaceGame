package com.project;

import java.awt.image.BufferedImage;

public class ActionBox {
	private ImageHandler img;
	private int x;
	private int y;
	private CrewAction action;
	private boolean openCrewSlot = true;
	private Text	actionText;
	private DraggableIcon crew = null;
	public ActionBox(BufferedImage img, int x, int y, CrewAction action) {
		this.img = new ImageHandler(x, y, img, true, EntityID.UI);
		this.img.start();
		this.x 			= x;
		this.y 		    = y;

		this.action     = action;
		actionText      = new Text(getName(), true, x+getWidth(), y);
	}
	
	public void setCrew(DraggableIcon crew) {
		if (openCrewSlot) {
			this.crew = crew;
			openCrewSlot = false;
			setActor(crew.getCrew());
		}
	}
	public void removeCrew() {
		this.crew = null;
		openCrewSlot = true;
		removeActor();
	}

	public static void delete(ActionBox box) {
		if(box.img       !=null) {ImageHandler .delete(box.img );}
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

	public void removeActor() {
		action.removeActor();
	}

	public StatID getStatType() {
		return action.getStatType();
	}

	
}