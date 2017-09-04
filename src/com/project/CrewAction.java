package com.project;

public class CrewAction {
	private String name;
	private int levelRequirement;
	private int xpReward;
	private StatID statType;
	private Crew actor;
	
	public CrewAction(String name,StatID statType, int xpRequirement, int levelReward) {
		this.name = name;
		this.levelRequirement = levelRequirement;
		this.xpReward = xpReward;
		this.statType = statType;
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
	
}
