package com.project;

import java.util.List;

import com.project.battle.BattleScreen;
import com.project.ship.Ship;

public interface Actionable {

	public List<CrewAction> getActions();
	public void doAction(CrewAction action,BattleScreen bs);
	public String getName();
}
