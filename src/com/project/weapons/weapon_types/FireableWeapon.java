package com.project.weapons.weapon_types;

import com.project.DamageType;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;

public class FireableWeapon extends Weapon {

	public FireableWeapon(int cooldownDuration, int rateOfFire,int damagePerShot,double accuracy, String name, DamageType dt) {
		super(cooldownDuration, name);
		this.destruct= new Destructive(rateOfFire,damagePerShot,accuracy,dt);
		this.isDestructive = true;
	}

	Destructive destruct;
	
	@Override
	public Object[] fire(){
		
		resetCooldown();
		return destruct.fire();	
	}
	
	private void resetCooldown(){ // made a function for it in case it got more complicated with buffs/debuffs
		this.cooldownTurnsLeft = getCooldownDuration();
	}
	@Override
	public String getWeaponInfo(){
		String info = this.name+" ( Dmg:"+destruct.getDamagePerShot()+" Acc:"+destruct.getAccuracy()+" RoF:"+destruct.getRateOfFire()+")";
		return info;
	}
	

}
