package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.project.CrewAction;
import com.project.ResourceLoader;
import com.project.RoomSize;
import com.project.ship.Generator;
import com.project.ship.Room;

public class GeneratorRoom extends Room{

	
	
	private Generator generator;
	
	
	public GeneratorRoom(Generator generator,String name, int actionHealth, int noOfActions, int damageableRadius, RoomSize size) {
		super(name,actionHealth,noOfActions,size);
		this.setDamageableRadius(damageableRadius);

		this.generator = generator;
		setSensorSphereRadius(100);
	}
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/generatorRoomIcon.png");
	}
	public Generator getGenerator() {
		return generator;
	}
	public void setGenerator(Generator generator) {
		this.generator = generator;
	}
	
	@Override
	protected CrewAction getLeastDependantAction() {
		
		/*Set smallest as null*/
		int smallest = Integer.MAX_VALUE; 
		CrewAction leastDependant = null;
		
		/*Loop through and search for the smallest*/
		for(CrewAction action : generator.getActions()){
			
			/*Update smallest value*/
			if(!action.isBroken() && action.getActionsNeededAfterUse().size() < smallest){
				smallest = action.getActionsNeededAfterUse().size();
				leastDependant = action;
				
				/*Break on 0 as you can't get smaller than it*/
				if(action.getActionsNeededAfterUse().size() == 0){
					break;
				}
			}
			
		}
		
		return leastDependant;
	}
	@Override
	protected boolean ActionsLeft() {
		for(CrewAction action : generator.getActions()){
			if(!action.isBroken()){
				return true;
			}
		}
		return false;
	}

}
