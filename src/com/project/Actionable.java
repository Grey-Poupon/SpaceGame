package com.project;

import java.util.List;

import com.project.battle.BattleScreen;
import com.project.button.Button;

public interface Actionable {

	public List<CrewAction> getActions();
	public String getFlavorText();
	public void doAction(Crew crew,CrewAction action,BattleScreen bs);
	public String getName();
	public List<Button> getInfoButtons(int width,int height,BattleScreen bs);
}
