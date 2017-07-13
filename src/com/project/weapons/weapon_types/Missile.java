package com.project.weapons.weapon_types;

import com.project.weapons.Destructive;
import com.project.weapons.Weapon;

public class Missile extends Weapon {

	public Missile(int cooldownDuration, int rateOfFire,int damagePerShot,int accuracy) {
		super(cooldownDuration);
		this.destruct= new Destructive(rateOfFire,damagePerShot,accuracy);
	}


	Destructive destruct;
	
	@Override
	public int[] fire(){
		
		resetCooldown();
		return destruct.fire();	
	}
	
	private void resetCooldown(){ // made a function for it in case it got more complicated with buffs/debuffs
		this.cooldownTurnsLeft = getCooldownDuration();
	}
}
