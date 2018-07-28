package com.project.Recreation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.project.ActionCooldown;
import com.project.Actionable;
import com.project.Crew;
import com.project.CrewAction;
import com.project.CrewActionID;
import com.project.ImageHandler;
import com.project.StatID;
import com.project.battle.BattleScreen;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Ship;

public class RecreationalItem implements Actionable{

	private List<CrewAction> actions; 
	private String name;
	private int relaxation;
	public RecreationalItem(String name,int relax) {
		this.name = name;
		this.actions = new ArrayList<>();
		this.actions.add(new CrewAction("Relax", CrewActionID.Relax, StatID.stress, Collections.emptyList(), 0, 0, 0,new ActionCooldown(0)));
		this.relaxation =relax;
	}

	@Override
	public List<CrewAction> getActions() {
		// TODO Auto-generated method stub
		return actions;
	}

	@Override
	public void doAction(Crew crew,CrewAction action, Ship ship, BattleScreen bs) {
		if(action.isOffCooldown()){
			crew.setStat(StatID.stress, (byte) 0);
		}
		action.updateCooldown();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}


	public ImageHandler getCardBackground() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ImageHandler getCardImage() {
		return null;
	}

	@Override
	public String getFlavorText() {
		// TODO Auto-generated method stub
		return null;
	}



	

}
