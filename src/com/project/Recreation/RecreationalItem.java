package com.project.Recreation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.project.Actionable;
import com.project.Crew;
import com.project.CrewAction;
import com.project.CrewActionID;
import com.project.ImageHandler;
import com.project.StatID;
import com.project.battle.BattleScreen;
import com.project.button.Button;
import com.project.button.ButtonID;

public class RecreationalItem implements Actionable{

	private List<CrewAction> actions; 
	private String name;
	private int relaxation;
	public RecreationalItem(String name,int relax) {
		this.name = name;
		this.actions = new ArrayList<>();
		this.actions.add(new CrewAction("Relax", CrewActionID.Relax, StatID.stress, Collections.emptyList(), 0, 0, 0));
		this.relaxation =relax;
	}

	@Override
	public List<CrewAction> getActions() {
		// TODO Auto-generated method stub
		return actions;
	}

	@Override
	public void doAction(Crew crew,CrewAction action, BattleScreen bs) {
		crew.setStat(StatID.stress, crew.getStat(StatID.stress));
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public List<Button> getInfoButtons(int width, int height, BattleScreen bs) {
		List<Button> buttons = new ArrayList<>();
		buttons.add(new Button(0,0,width, height, ButtonID.Info, 0,false,"Name: "+this.name,bs,true));
		buttons.add(new Button(0,0,width, height, ButtonID.Info, 1,false,"Relax:"+relaxation,bs,true));
		return buttons;
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
