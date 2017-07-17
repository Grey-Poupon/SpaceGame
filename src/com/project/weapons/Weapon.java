package com.project.weapons;

public abstract class Weapon { // Holds the shared functionality between all weapons
	private int cooldownDuration; // weapons will have a cooldown period?
	protected int cooldownTurnsLeft; 
	
	protected boolean isBuffer;
	protected boolean isDestructive;
	protected boolean isDebuffer;
	
	public boolean isBuffer() {// to check if the weapon buffs
		return isBuffer;
	}
	public boolean isDestructive() { // to check if the weapon is destructive
		return isDestructive;
	}
	public boolean isDebuffer(){ // to check if the weapon debuffs
		return isDebuffer;
	}
	public Weapon(int cooldownDuration){
		this.cooldownDuration=cooldownDuration;
	}
	public Weapon(int cooldownDuration,double damageModifier){
		this.cooldownDuration=cooldownDuration;
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
	
}