package com.project.weapons.weapon_types;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.project.Animation;
import com.project.Crew;
import com.project.CrewAction;
import com.project.CrewActionID;
import com.project.DamageType;
import com.project.battle.BattleScreen;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Slot;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class FireableWeapon extends Weapon {



	public FireableWeapon(int cooldownDuration, int rateOfFire,int damagePerShot,float accuracy, String name, DamageType dt,int weaponSwayMod,Animation anim,boolean targetSelf,List<WeaponEffect> we,int projectileGap,Animation weaponBody,List<CrewAction>actions,Slot slot) {
		super(cooldownDuration, name,anim,targetSelf,we,projectileGap,weaponBody,actions,slot);
		
		effects.add(new Destructive(rateOfFire,damagePerShot,accuracy,dt,weaponSwayMod));
	}

	
	@Override
	public Object[] fire(){
		resetCooldown();
		weaponBody.start(false);
		Object[] returnableEffects = new Object[effects.size()];
		
		for(int i = 0;i < returnableEffects.length;i++) {
			returnableEffects[i] = effects.get(i);
		}
		
		return returnableEffects;	
	}
	
	private void resetCooldown(){ // made a function for it in case it got more complicated with buffs/debuffs
		this.cooldownTurnsLeft = getCooldownDuration();
	}
	@Override
	public String getWeaponInfo(){
		String info = this.name+" ( Dmg:"+effects.get(effects.size()-1).getDamagePerShot()+" Acc:"+effects.get(effects.size()-1).getAccuracy()+" RoF:"+effects.get(effects.size()-1).getRateOfFire()+")";
		return info;
	}

	public double getAccuracy() {
		return effects.get(effects.size()-1).getAccuracy();
	}




	@Override
	public void render(Graphics g) {
		super.render(g);
	}




	@Override
	public void render(Graphics g, Slot slot) {
		weaponBody.setxCoordinate(slot.getX());
		weaponBody.setyCoordinate(slot.getY()+slot.getHeight()/2-weaponBody.getTileHeight());
		if(!slot.isFront()) {
			weaponBody.setxCoordinate(slot.getX()+slot.getWidth()/2);
			weaponBody.setxFlip(-1);
		}
		//wb.setAlign(AdjustmentID.MidLeft);

		weaponBody.render(g);
		
	}
	
	public Weapon copy() {
		//WHEN NEW TYPES OF WEAPON BESIDES FIREABLE NEED TO ADD CHECK
		List<CrewAction> newActions = new ArrayList<CrewAction>();
		for(int i = 0 ; i < actions.size();i++) {
			newActions.add(actions.get(i).copy());
		}
		
		
		
		if(effects.size()==1) {
			return new FireableWeapon(cooldownDuration,  effects.get(effects.size()-1).getRateOfFire(), effects.get(effects.size()-1).getDamagePerShot(), (float) effects.get(effects.size()-1).getAccuracy(),  name,  ((Destructive)effects.get(effects.size()-1)).getDamageType(), ((Destructive)effects.get(effects.size()-1)).getWeaponSwayMod(), firingAnimation, targetSelf, null, projectileGap, weaponBody,newActions,slot);
		}
		List<WeaponEffect> temp = effects;
		temp.remove(effects.size()-1);
		return new FireableWeapon(cooldownDuration,  effects.get(effects.size()-1).getRateOfFire(), effects.get(effects.size()-1).getDamagePerShot(), (float) effects.get(effects.size()-1).getAccuracy(),  name,  ((Destructive)effects.get(effects.size()-1)).getDamageType(), ((Destructive)effects.get(effects.size()-1)).getWeaponSwayMod(), firingAnimation, targetSelf, temp, projectileGap, weaponBody,newActions,slot);
	}





	@Override
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









}
