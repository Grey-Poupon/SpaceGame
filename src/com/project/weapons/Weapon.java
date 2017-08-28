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
	protected int projectileGap;
	protected boolean targetSelf;
	protected List<WeaponEffect> effects;	
	protected Animation firingAnimation; 
	
	public Weapon(int cooldownDuration, String name,Animation anim,boolean targetSelf,WeaponEffect[] we,int projectileGap){
		this.firingAnimation = anim;
		this.cooldownDuration=cooldownDuration;
		this.name = name;
		this.targetSelf = targetSelf;
		this.projectileGap = projectileGap;
		if(we!=null) {
			this.effects = Arrays.asList(we);
		}
		else {
			this.effects = new ArrayList<>();
		}
	}
	
	
	
	public List<WeaponEffect> getEffects() {
		return effects;
	}
	public void setEffects(List<WeaponEffect> effects) {
		this.effects = effects;
	}
	public Animation getFiringAnimation() {
		return firingAnimation;
	}
	public void setFiringAnimations(Animation firingAnimation) {
		this.firingAnimation = firingAnimation;
	}
	public boolean isTargetSelf() {
		return targetSelf;
	}
	public void setTargetSelf(boolean targetSelf) {
		this.targetSelf = targetSelf;
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
	public Animation getAnimation() {
		return firingAnimation.copy();
	}

	public int getProjectileGap() {
		return projectileGap;
	}	
}