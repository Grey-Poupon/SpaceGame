package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.project.Crew;
import com.project.CrewAction;
import com.project.ResourceLoader;
import com.project.ship.Room;

public class Cockpit extends Room{
	private List<CrewAction> manoeuvres = new ArrayList<CrewAction>();
	public Cockpit(List<CrewAction> manoeuvres,String name, int actionHealth, int noOfActions, int damageableRadius) {
		super(name,actionHealth,noOfActions);
		this.manoeuvres = manoeuvres;
		this.setDamageableRadius(damageableRadius);
	}
	
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/cockpitIcon.png");
	}

	public List<CrewAction> getManoeuvres() {
		return manoeuvres;
	}

	@Override
	public CrewAction getLeastDependantAction() {
		
		/*Set smallest as null*/
		int smallest = Integer.MAX_VALUE; 
		CrewAction leastDependant = null;
		
		/*Loop through and search for the smallest*/
		for(CrewAction action : manoeuvres){
			
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
		
		for(CrewAction action : manoeuvres){
			if(!action.isBroken()){
				return true;
			}
		}
		return false;
	}

}
