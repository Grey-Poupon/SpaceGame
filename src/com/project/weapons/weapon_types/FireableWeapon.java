package com.project.weapons.weapon_types;

import java.util.List;

import com.project.Animation;
import com.project.DamageType;
import com.project.weapons.Destructive;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class FireableWeapon extends Weapon {

	private float reloadTime;

	public FireableWeapon(int cooldownDuration, int rateOfFire,int damagePerShot,double accuracy, String name, DamageType dt,int weaponSwayMod,float reloadTime,Animation[] anims,boolean targetSelf,WeaponEffect[] we) {
		super(cooldownDuration, name,anims,targetSelf,we);
		effects.add(new Destructive(rateOfFire,damagePerShot,accuracy,dt,weaponSwayMod));



		this.isDestructive = true;
		this.reloadTime = reloadTime;
	}

	

	
	@Override
	public Object[] fire(){
		
		resetCooldown();
		return effects.get(effects.size()-1).fire();	
	}
	
	private void resetCooldown(){ // made a function for it in case it got more complicated with buffs/debuffs
		this.cooldownTurnsLeft = getCooldownDuration();
	}
	@Override
	public String getWeaponInfo(){
		String info = this.name+" ( Dmg:"+effects.get(effects.size()-1).getDamagePerShot()+" Acc:"+effects.get(effects.size()-1).getAccuracy()+" RoF:"+effects.get(effects.size()-1).getRateOfFire()+")";
		return info;
	}
	public float getReloadTime() {

		return reloadTime;
}
	public double getAccuracy() {
		return effects.get(effects.size()-1).getAccuracy();
	}

}
