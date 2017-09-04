package com.project;

public class CrewAction {
	private String name;
	private float xpRequirement;
	private Crew actor;
	
	public CrewAction(String name, float xpRequirement) {
		this.name = name;
		this.xpRequirement = xpRequirement;
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

	public float getXpRequirement() {
		return xpRequirement;
	}
	
}
