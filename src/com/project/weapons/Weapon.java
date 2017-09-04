package com.project.weapons;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.project.Actionable;
import com.project.Animation;
import com.project.CrewAction;
import com.project.Slottable;

public abstract class Weapon implements Slottable, Actionable{ // Holds the shared functionality between all weapons
	private int cooldownDuration; // weapons will have a cooldown period?
	protected int cooldownTurnsLeft; 
	protected String name;
	protected int projectileGap;
	protected boolean targetSelf;
	protected List<WeaponEffect> effects;	
	protected Animation firingAnimation;
	protected Animation weaponBody;
	protected List<CrewAction> actions = new ArrayList<CrewAction>();

	public Weapon(int cooldownDuration, String name,Animation anim,boolean targetSelf,WeaponEffect[] we,int projectileGap,Animation weaponBody,List<CrewAction> actions){
		this.firingAnimation = anim;
		this.actions = actions;
		this.cooldownDuration=cooldownDuration;
		this.name = name;
		this.targetSelf = targetSelf;
		this.projectileGap = projectileGap;
		this.weaponBody = weaponBody;
		if(we!=null) {
			this.effects = Arrays.asList(we);
		}
		else {
			this.effects = new ArrayList<>();
		}
	}
	
	
	
	
	public Animation getWeaponBody() {
		return weaponBody;
	}
	public void setWeaponBody(Animation weaponBody) {
		this.weaponBody = weaponBody;
	}
	public List<WeaponEffect> getEffects() {
		return effects;
	}
	public void setEffects(List<WeaponEffect> effects) {
		this.effects = effects;
	}
	public Animation getFiringAnimation() {
		return firingAnimation.copy();
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

	public void render(Graphics g) {
		weaponBody.render(g);
	}


	public int getProjectileGap() {
		return projectileGap;
	}




	public List<CrewAction> getActions() {
		return actions;
	}	
}