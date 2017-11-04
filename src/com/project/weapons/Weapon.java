package com.project.weapons;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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
	protected int projectileGap;
	protected boolean targetSelf;
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
	
	
	public Animation getSlotItemBody() {
		return weaponBody;
	}

	public Weapon(int radiusOfHit,int cooldownDuration, int rateOfFire,int damagePerShot,float accuracy, String name,boolean isPhysical,Animation anim,boolean targetSelf,List<WeaponEffect> we,int projectileGap,Animation weaponBody,List<CrewAction>actions,Slot slot, ImageHandler backgroundImg, ImageHandler weaponImg){
		
		this.roundsTilHit = 0;
		this.radiusOfHit = radiusOfHit;
		this.isPhysical = isPhysical;
		this.backgroundImg = backgroundImg;
		this.weaponImg = weaponImg;
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
		this.effects.add(new Destructive(rateOfFire,  damagePerShot,  accuracy,  isPhysical, radiusOfHit));
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
	public boolean isPhysical() {
		return isPhysical;
	}


	public void setPhysical(boolean isPhysical) {
		this.isPhysical = isPhysical;
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
	
	public Buffer getBuff(){ // ^^
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

	public Object[] fire(){
		resetCooldown();
		weaponBody.start(true);
		Object[] returnableEffects = new Object[effects.size()];
		
		for(int i = 0;i < returnableEffects.length;i++) {
			returnableEffects[i] = effects.get(i);
		}
		return returnableEffects;	
	}
	
	private void resetCooldown(){ // made a function for it in case it got more complicated with buffs/debuffs
		this.cooldownTurnsLeft = getCooldownDuration();
	}
	
	public String getWeaponInfo(){
		String info = this.name+" ( Dmg:"+effects.get(effects.size()-1).getDamagePerShot()+" Acc:"+effects.get(effects.size()-1).getAccuracy()+" RoF:"+effects.get(effects.size()-1).getRateOfFire()+")";
		return info;
	}

	public double getAccuracy() {
		return effects.get(effects.size()-1).getAccuracy();
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
		
		if(effects.size()==1) {
			return new Weapon(radiusOfHit,cooldownDuration,  effects.get(effects.size()-1).getRateOfFire(), effects.get(effects.size()-1).getDamagePerShot(), (float) effects.get(effects.size()-1).getAccuracy(),  name,  isPhysical,  firingAnimation, targetSelf, null, projectileGap, weaponBody,newActions,slot,weaponImg,backgroundImg);
		}
		List<WeaponEffect> temp = effects;
		temp.remove(effects.size()-1);
		return new Weapon(radiusOfHit,cooldownDuration,  effects.get(effects.size()-1).getRateOfFire(), effects.get(effects.size()-1).getDamagePerShot(), (float) effects.get(effects.size()-1).getAccuracy(),  name,  isPhysical, firingAnimation, targetSelf, temp, projectileGap, weaponBody,newActions,slot,weaponImg,backgroundImg);
	}

	public void doAction(Crew crew,CrewAction action, BattleScreen bs) {
		if(action.getActionType() == CrewActionID.Fire) {
			if(bs.playerIsChaser()) {
				bs.addChaserWeaponChoice(this);
				//bs.chaserShip.updatePowerConsumption(action);
			}
			else {
				bs.addChasedWeaponChoice(this);
				//bs.chasedShip.updatePowerConsumption(action);
			}
		}
	}
	
	public List<Button> getInfoButtons(int width,int height,BattleScreen bs){
		List<Button> buttons = new ArrayList<>();
		buttons.add(new Button(0,0,width, height, ButtonID.Info, 0,false,"Name: "+this.name,bs,true));
		buttons.add(new Button(0,0,width, height, ButtonID.Info, 1,false,"Dmg:"+effects.get(effects.size()-1).getDamagePerShot(),bs,true));
		buttons.add(new Button(0,0,width, height, ButtonID.Info, 1,false,"Acc:"+effects.get(effects.size()-1).getAccuracy(),bs,true));
		buttons.add(new Button(0,0,width, height, ButtonID.Info, 1,false,"RoF:"+effects.get(effects.size()-1).getRateOfFire(),bs,true));
		return buttons;
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

}