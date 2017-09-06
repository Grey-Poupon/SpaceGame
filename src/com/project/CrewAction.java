package com.project;

public class CrewAction {
	private String name;
	private int levelRequirement;
	private int xpReward;
	private StatID statType;
	private CrewActionID actionType;
	private Crew actor;
	private int powerCost;
	

	public CrewAction(String name,CrewActionID actionType,StatID statType, int levelRequirement, int xpReward,int powerCost) {
		this.name = name;
		this.actionType = actionType;
		this.levelRequirement = levelRequirement;
		this.xpReward = xpReward;
		this.statType = statType;
		this.powerCost = powerCost;
		
	}

	public Crew getActor() {
		return actor;
	}

	public void setActor(Crew actor) {
		this.actor = actor;
	}

	public void removeActor() {
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
	
}
