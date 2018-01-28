package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.project.Actionable;
import com.project.Crew;
import com.project.CrewAction;
import com.project.ImageHandler;
import com.project.ResourceLoader;
import com.project.RoomSize;
import com.project.battle.BattleScreen;
import com.project.button.Button;
import com.project.ship.Room;

public class Cockpit extends Room implements Actionable{
	private ImageHandler background;
	private ImageHandler card;
	private List<CrewAction> manoeuvres = new ArrayList<CrewAction>();
	public Cockpit(List<CrewAction> manoeuvres,String name, int actionHealth, int damageableRadius, RoomSize size) {
		super(name,actionHealth,manoeuvres.size(),size);
		this.manoeuvres = manoeuvres;
		this.setDamageableRadius(damageableRadius);
		this.background = new ImageHandler(0, 0, "res/ui/piloting.png", true, null);
		this.card = new ImageHandler(0,0,"res/ui/piloting.png",true,null);
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

	@Override
	public List<CrewAction> getActions() {
		// TODO Auto-generated method stub
		return manoeuvres;
	}

	@Override
	public String getFlavorText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doAction(Crew crew, CrewAction action, BattleScreen bs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Button> getInfoButtons(int width, int height, BattleScreen bs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageHandler getCardBackground() {
		// TODO Auto-generated method stub
		return background;
	}

	@Override
	public ImageHandler getCardImage() {
		// TODO Auto-generated method stub
		return getCrewInRoom().get(0).getPortrait().copy();
	}

}
