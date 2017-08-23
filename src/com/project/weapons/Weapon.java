package com.project.weapons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.Animation;
import com.project.Slottable;

public abstract class Weapon implements Slottable{ // Holds the shared functionality between all weapons
	private int cooldownDuration; // weapons will have a cooldown period?
	protected int cooldownTurnsLeft; 
	protected String name;
	protected boolean isBuffer;
	protected boolean isDestructive;
	protected boolean isDebuffer;
	protected boolean targetSelf;
	protected List<WeaponEffect> effects;	
	protected List<Animation> firingAnimations = new ArrayList<Animation>(); 
	
	public List<WeaponEffect> getEffects() {
		return effects;
	}
	public void setEffects(List<WeaponEffect> effects) {
		this.effects = effects;
	}
	public List<Animation> getFiringAnimations() {
		return firingAnimations;
	}
	public void setFiringAnimations(List<Animation> firingAnimations) {
		this.firingAnimations = firingAnimations;
	}
	public boolean isTargetSelf() {
		return targetSelf;
	}
	public void setTargetSelf(boolean targetSelf) {
		this.targetSelf = targetSelf;
	}
	public boolean isBuffer() {// to check if the weapon buffs
		return isBuffer;
	}
	public boolean isDestructive() { // to check if the weapon is destructive
		return isDestructive;
	}
	public boolean isDebuffer(){ // to check if the weapon debuffs
		return isDebuffer;
	}

	public Weapon(int cooldownDuration, String name,Animation[] anims,boolean targetSelf,WeaponEffect[] we){
		this.firingAnimations = Arrays.asList(anims);
		this.cooldownDuration=cooldownDuration;
		this.name = name;
		this.targetSelf = targetSelf;
		if(we!=null) {
			this.effects = Arrays.asList(we);
		}
		else {
			this.effects = new ArrayList<>();
		}
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
	public String getSprite() {
		// TODO Auto-generated method stub
		return "res/missileSpritesheet.png";
	}
	public Animation getAnimation(int i) {
		return firingAnimations.get(i).copy();
	}	
}