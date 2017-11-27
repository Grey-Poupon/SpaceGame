package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.project.Crew;
import com.project.CrewAction;
import com.project.ResourceLoader;
import com.project.RoomSize;
import com.project.Recreation.RecreationalItem;
import com.project.ship.Room;

public class StaffRoom extends Room {
	List<RecreationalItem> items = new ArrayList<>();
	
	/*This is essential just the rooms health*/
	private List<CrewAction> dummyActions = new ArrayList<CrewAction>();
	
	
	public StaffRoom(List<RecreationalItem> items,String name, int actionHealth, int noOfActions, int damageableRadius, RoomSize size) {
		super(name,actionHealth,noOfActions,size);
		this.setDamageableRadius(damageableRadius);

		
		/*Set up health/dummy actions*/
		for(int i = 0;i<noOfActions;i++){
			dummyActions.add(new CrewAction());
		}
	}

	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/staffRoomIcon.png");
	}
	
	public List<RecreationalItem> getItems() {
		return items;
	}
	public void setItems(List<RecreationalItem> items) {
		this.items = items;
	}
	
	@Override
	protected CrewAction getLeastDependantAction() {
		for(CrewAction action : dummyActions){
			if(!action.isBroken()){
				return action;
			}
		}
		return null;
	}
	@Override
	protected boolean ActionsLeft() {
		for(CrewAction action : dummyActions){
			if(!action.isBroken()){
				return true;
			}
		}
		return false;
	}

}
