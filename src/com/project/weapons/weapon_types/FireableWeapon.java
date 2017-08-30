package com.project.weapons.weapon_types;

import java.awt.Graphics;

import com.project.AdjustmentID;
import com.project.Animation;
import com.project.DamageType;
import com.project.ship.Slot;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class FireableWeapon extends Weapon {


	public FireableWeapon(int cooldownDuration, int rateOfFire,int damagePerShot,float accuracy, String name, DamageType dt,int weaponSwayMod,Animation anim,boolean targetSelf,WeaponEffect[] we,int projectileGap,Animation weaponBody) {
		super(cooldownDuration, name,anim,targetSelf,we,projectileGap,weaponBody);
		effects.add(new Destructive(rateOfFire,damagePerShot,accuracy,dt,weaponSwayMod));
	}

	

	
	@Override
	public Object[] fire(){
		resetCooldown();
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
		Animation wb = weaponBody.copy();
		wb.setxCoordinate(slot.getX());
		wb.setyCoordinate(slot.getY()+slot.getHeight()/2-wb.getTileHeight());
		if(!slot.isFront()) {
			wb.setxCoordinate(slot.getX()+slot.getWidth()/2);
			wb.setxScale(-1);
		}
		//wb.setAlign(AdjustmentID.MidLeft);

		wb.render(g);
		
	}







}
