package com.project.ship.rooms;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.project.CrewAction;
import com.project.ResourceLoader;
import com.project.RoomSize;
import com.project.ship.Room;
import com.project.ship.Sensor;

public class SensorRoom extends Room {

	private Sensor sensor;
	
	/*This is essential just the rooms health*/
	private List<CrewAction> dummyActions = new ArrayList<CrewAction>();
	
	
	public SensorRoom(Sensor sensor, String name, int actionHealth, int noOfActions, int damageableRadius, RoomSize size) {
		super(name,actionHealth,noOfActions,size);
		this.setDamageableRadius(damageableRadius);
		setRoomName("Sensors");
		
		/*Set up health/dummy actions*/
		for(int i = 0;i<noOfActions;i++){
			dummyActions.add(new CrewAction());
		}
		
	}
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/sensorRoomIcon.png");
	}
	
	public Sensor getSensor() {
		return sensor;
	}
	
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
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
