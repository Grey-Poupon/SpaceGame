package com.project;

import java.util.ArrayList;
import java.util.List;

import com.project.ship.Room;

public class CrewAction implements Comparable {
	private String name;
	private int levelRequirement;
	private int xpReward;
	private StatID statType;
	private CrewActionID actionType;
	private Crew actor;
	private int powerCost;
	private static Graph PowerGraph;
	private List<CrewAction> actionsNeeded = new ArrayList<CrewAction>();
	private List<CrewAction> actionsNeededAfterUse = new ArrayList<CrewAction>();


	public CrewAction(String name,CrewActionID actionType,StatID statType,List<CrewAction> actionsNeededToUse, int levelRequirement, int xpReward,int powerCost) {
		this.name             = name;
		this.actionType       = actionType;
		this.levelRequirement = levelRequirement;
		this.xpReward         = xpReward;
		this.statType         = statType;
		this.powerCost        = powerCost;	
		this.actionsNeeded.addAll(actionsNeededToUse);
		this.actionsNeededAfterUse = actionsNeededToUse;
	}

	public Crew getActor() {
		return actor;
	}

	public void setActor(Crew actor) {
		actor.getShip().tempUpdatePowerConsumption(powerCost);
		this.actor = actor;
	}

	public void removeActor() {
		actor.getShip().tempUpdatePowerConsumption(-powerCost);
		this.actor = null;
		
	}
	
	public String getName() {
		return name;
	}

	public int getLevelRequirement() {
		return levelRequirement;
	}

	public int getXpReward() {
		return xpReward;
	}

	public StatID getStatType() {
		return statType;
	}

	public CrewActionID getActionType() {
		return actionType;
	}
	
	public int getPowerCost() {
		return powerCost;
	}

	public void setPowerCost(int powerCost) {
		this.powerCost = powerCost;
	}

	public List<CrewAction> getActionsNeeded() {
		return actionsNeeded;
	}

	public boolean addActionsNeeded(List<CrewAction> c) {
		return actionsNeeded.addAll(c);
	}

	public List<CrewAction> getActionsNeededAfterUse() {
		return actionsNeededAfterUse;
	}
	
	public void removeActionNeeded(CrewAction action) {
		for(int i = 0;i<actionsNeeded.size();i++) {
			if(actionsNeeded.get(i).getActionType() == action.getActionType()) {
				actionsNeeded.remove(i);
			}
		}
	}

	@Override
	public int compareTo(Object arg0) {
		if(!(arg0 instanceof CrewAction)) {
			return 1;
		}
		CrewAction arg1 = (CrewAction) arg0;
		if(arg1.actionsNeeded.size() > actionsNeeded.size()) {
			return -1;
		}
		if(arg1.actionsNeeded.size() == actionsNeeded.size()) {
			return 0;
		}
		return 1;
	}

	public void resetActions() {
		actionsNeeded.clear();
		actionsNeeded.addAll(actionsNeededAfterUse);
	}

	public CrewAction copy() {
		CrewAction action = new CrewAction(name, actionType, statType, actionsNeededAfterUse, levelRequirement, levelRequirement, levelRequirement);
		// remove actions that have been completed
		if(actionsNeeded.size() == actionsNeededAfterUse.size()) {
			for(CrewAction a:actionsNeededAfterUse) {
				if(!actionsNeeded.contains(a)) {
					action.removeActionNeeded(a);
				}
			}
		}
		return action;
	}

	public static Graph getPowerGraph() {
		return PowerGraph;
	}

	public static void setPowerGraph(Graph powerGraph) {
		PowerGraph = powerGraph;
	}

}
