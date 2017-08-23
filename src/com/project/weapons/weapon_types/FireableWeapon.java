package com.project.weapons.weapon_types;

import java.util.List;

import com.project.Animation;
import com.project.DamageType;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;

public class FireableWeapon extends Weapon {

	private float reloadTime;
	public FireableWeapon(int cooldownDuration, int rateOfFire,int damagePerShot,double accuracy, String name, DamageType dt,int weaponSwayMod,float reloadTime,Animation[] anims) {
		super(cooldownDuration, name,anims);
		this.destruct= new Destructive(rateOfFire,damagePerShot,accuracy,dt,weaponSwayMod);
		this.isDestructive = true;
		this.reloadTime = reloadTime;
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
	public float getReloadTime() {

		return reloadTime;
}
	public double getAccuracy() {
		return destruct.getAccuracy();
	}

}
