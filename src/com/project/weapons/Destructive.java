package com.project.weapons;

import java.util.Random;

import com.project.DamageType;


public class Destructive implements WeaponEffect {
	private int damagePerShot;
	private boolean isPhysical;
	private int radiusOfHit;

	public int getDamagePerShot() {
		return damagePerShot;
	}

	public void setDamagePerShot(int damagePerShot) {
		this.damagePerShot = damagePerShot;
	}
	
	public Destructive(int damagePerShot, boolean isPhysical, int radiusOfHit){
		this.damagePerShot =damagePerShot;
		this.isPhysical = isPhysical;
		this.radiusOfHit = radiusOfHit;
	}
	
	@Override
	public Object[] getInfo(){		
		Object[] damageDealt = {getDamagePerShot(),isPhysical,radiusOfHit}; // to return, holds the shots hit and dmg per shot
		return damageDealt;
	}
}
