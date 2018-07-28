package com.project;

import java.util.List;

import com.project.battle.BattleScreen;
import com.project.button.Button;
import com.project.ship.Ship;

public interface Actionable {

	public List<CrewAction> getActions();
	public String getFlavorText();
	public void doAction(Crew crew,CrewAction action,Ship ship,BattleScreen bs);
	public String getName();
	public ImageHandler getCardBackground();
	public ImageHandler getCardImage();
}
