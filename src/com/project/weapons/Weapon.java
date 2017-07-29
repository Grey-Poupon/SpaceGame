package com.project.weapons;

public abstract class Weapon { // Holds the shared functionality between all weapons
	private int cooldownDuration; // weapons will have a cooldown period?
	protected int cooldownTurnsLeft; 
	protected String name;
	protected boolean isBuffer;
	protected boolean isDestructive;
	protected boolean isDebuffer;
	//protected Animation 
	public boolean isBuffer() {// to check if the weapon buffs
		return isBuffer;
	}
	public boolean isDestructive() { // to check if the weapon is destructive
		return isDestructive;
	}
	public boolean isDebuffer(){ // to check if the weapon debuffs
		return isDebuffer;
	}
	public Weapon(int cooldownDuration, String name){
		this.cooldownDuration=cooldownDuration;
		this.name = name;
		}
	protected int getCooldownDuration() {
		return cooldownDuration;
	}
	
	public Object[] fire(){ // this should be overridden
		return new Object[]{0,0};
	}
	public Buffer getBuff(){ // ^^
		return null;
	}
	public String getWeaponInfo(){ //^^
		return name;
	}
	public String getName() {
		return name;
	}
	
}