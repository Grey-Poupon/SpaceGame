package com.project.weapons;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.project.Actionable;
import com.project.Animation;
import com.project.Crew;
import com.project.CrewAction;
import com.project.CrewActionID;
import com.project.ImageHandler;
import com.project.ProjectileAnimation;
import com.project.Slottable;
import com.project.battle.BattleScreen;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Slot;

public class Weapon implements Slottable, Actionable{ // Holds the shared functionality between all weapons
	protected int cooldownDuration; // weapons will have a cooldown period?
	protected int cooldownTurnsLeft; 
	protected String name;
	protected int projectileGap; //  this is in pixels for ProjAnim but for Ship-Weapon It will be used as if it were a Distance
	protected Target target;
	protected List<WeaponEffect> effects;	
	protected Animation firingAnimation;
	protected Animation weaponBody;
	protected Slot slot;
	protected ImageHandler backgroundImg;
	protected ImageHandler weaponImg;
	protected boolean isPhysical;
	protected int radiusOfHit;
	protected int roundsTilHit;
	protected List<CrewAction> actions = new ArrayList<CrewAction>();
	protected ProjectileAnimation projAnim = null;
	protected float accuracy;
	protected int rateOfFire;
	
	
	public Animation getSlotItemBody() {
		return weaponBody;
	}

	public Weapon(List<WeaponEffect> effects, int rateOfFire,float accuracy, String name,Animation firingAnimation,int projectileGap,Animation weaponBody,List<CrewAction>actions, ImageHandler backgroundImg, ImageHandler weaponImg, Target target){
		
		this.roundsTilHit = 0;
		this.backgroundImg = backgroundImg;
		this.weaponImg = weaponImg;
		this.firingAnimation = firingAnimation;
		this.actions = actions;
		this.name = name;
		this.target = target;
		this.projectileGap = projectileGap;
		this.weaponBody = weaponBody.copy();
		this.weaponBody.setMonitored(true);
		this.accuracy = accuracy;
		this.rateOfFire = rateOfFire;
		this.effects = effects;
	}
	
	public Animation getWeaponBody() {
		return weaponBody;
	}
	public void setWeaponBody(Animation weaponBody) {
		this.weaponBody = weaponBody;
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
	public boolean isPhysical() {
		return isPhysical;
	}

	public void setPhysical(boolean isPhysical) {
		this.isPhysical = isPhysical;
	}

	public boolean isTargetSelf() {
		return Target.Self == target;
	}
	public void setTargetSelf(Target target) {
		this.target = target;
	}

	protected int getCooldownDuration() {
		return cooldownDuration;
	}
	
	public Buffer getBuff(){ 
		return null;
	}
	
	public String getName() {
		return name;
	}
	public String getSprite() {
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

	public List<WeaponEffect> getEffects(){
		return effects;
	}
	
	private void resetCooldown(){ // made a function for it in case it got more complicated with buffs/debuffs
		this.cooldownTurnsLeft = getCooldownDuration();
	}
	
	

	public double getAccuracy() {
		return accuracy;
	}

	public void render(Graphics g, Slot slot) {
		if(!weaponBody.isRunning() && projAnim!=null) {
				projAnim.start();
				projAnim =null;
		}
		
		weaponBody.setxCoordinate(slot.getX());
		weaponBody.setyCoordinate(slot.getY()+slot.getHeight()/2-weaponBody.getTileHeight());
		if(!slot.isFront()) {
			weaponBody.setxCoordinate((float) (slot.getX()+slot.getWidth()));
			weaponBody.setxFlip(-1);
		}
		weaponBody.render(g);		
	}

	public Weapon copy() {
		//WHEN NEW TYPES OF WEAPON BESIDES FIREABLE NEED TO ADD CHECK
		List<CrewAction> newActions = new ArrayList<CrewAction>();
		for(int i = 0 ; i < actions.size();i++) {
			newActions.add(actions.get(i).copy());
		}
		
		Weapon w = new Weapon(effects, rateOfFire, accuracy, name, firingAnimation, projectileGap, weaponBody, newActions, backgroundImg, weaponImg, target);
		w.setSlot(slot);
		return w;
		}

	@Override
	public void doAction(Crew crew,CrewAction action, BattleScreen bs) {
		if(action.getActionType() == CrewActionID.Fire && action.isOffCooldown()) {
			if(bs.playerIsChaser()) {
				bs.addChaserWeaponChoice(this);
				//bs.chaserShip.updatePowerConsumption(action);
			}
			else if(action.isOffCooldown()){
				bs.addChasedWeaponChoice(this);
				//bs.chasedShip.updatePowerConsumption(action);
			}
		}
	}
	


	public String getFlavorText() {
		return null;
	}
	
	public ImageHandler getCardBackground() {
		return backgroundImg.copy();
	}

	public ImageHandler getCardImage() {
		// TODO Auto-generated method stub
		return weaponImg.copy();
	}
	
	public void giveXP() {
		
	}
	
	public void setProjAnim(ProjectileAnimation pro) {
		projAnim = pro;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	public Target getTarget() {
		return target;
	}

	public int getRadiusOfHit() {
		return radiusOfHit;
	}

	public int[] fire() {
		int[] accuracies = new int[rateOfFire];
		Random rand = new Random();
		for(int i = 0;i<rateOfFire;i++){
			if(rand.nextFloat() < accuracy){
				accuracies[i] = 1;
			}
			else{accuracies[i] = 0;}
		}
		return accuracies;
	}

}