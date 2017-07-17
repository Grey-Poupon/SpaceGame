package com.project.weapons.weapon_types;

import com.project.DamageType;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;

public class Flamethrower extends Weapon {

	public Flamethrower(int cooldownDuration, int rateOfFire,int damagePerShot,int accuracy) {
		super(cooldownDuration);
		this.destruct= new Destructive(rateOfFire,damagePerShot,accuracy,DamageType.Flame);
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
}
