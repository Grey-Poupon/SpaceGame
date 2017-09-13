package com.project.weapons;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.project.Actionable;
import com.project.Animation;
import com.project.CrewAction;
import com.project.Slottable;
import com.project.battle.BattleScreen;
import com.project.button.Button;
import com.project.ship.Slot;




public abstract class Weapon implements Slottable, Actionable{ // Holds the shared functionality between all weapons
	protected int cooldownDuration; // weapons will have a cooldown period?

	protected int cooldownTurnsLeft; 
	protected String name;
	protected int projectileGap;
	protected boolean targetSelf;
	protected List<WeaponEffect> effects;	
	protected Animation firingAnimation;
	protected Animation weaponBody;
	protected Slot slot;
	
	
	
	
	public Animation getSlotItemBody() {
		return weaponBody;
	}



	protected List<CrewAction> actions = new ArrayList<CrewAction>();

	public Weapon(int cooldownDuration, String name,Animation anim,boolean targetSelf,List<WeaponEffect> we,int projectileGap,Animation weaponBody,List<CrewAction> actions,Slot slot){

	
		this.slot = slot;
		this.firingAnimation = anim;
		this.actions = actions;
		this.cooldownDuration=cooldownDuration;
		this.name = name;
		this.targetSelf = targetSelf;
		this.projectileGap = projectileGap;
		this.weaponBody = weaponBody.copy();
		if(we!=null) {
			this.effects =we;
		}
		else {
			this.effects = new ArrayList<WeaponEffect>();
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
	
	public Weapon copy() {
		return null;
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




	public Slot getSlot() {
		return slot;
	}




	public void setSlot(Slot slot) {
		this.slot = slot;
	}




	public void giveXP() {
		// TODO Auto-generated method stub
		
	}




	public List<Button> getInfoButtons(int tooltipbuttonwidth, int tooltipbuttonheight, BattleScreen bs) {
		
		return null;
	}	
}