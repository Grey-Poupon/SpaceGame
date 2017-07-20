package com.project.weapons.weapon_types;

import com.project.DamageType;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;

public class Laser extends Weapon {
	Destructive destruct;
	public Laser(int cooldownDuration, int rateOfFire,int damagePerShot,double accuracy, String name) {
		super(cooldownDuration, name);
		this.destruct= new Destructive(rateOfFire,damagePerShot,accuracy,DamageType.Laser);
		this.isDestructive = true;
	}

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