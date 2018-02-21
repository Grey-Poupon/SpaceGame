package com.project;

import java.util.List;

import com.project.weapons.Target;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class ProjectileInfo {
	private int waitGap;
	private int radiusOfHit;
	private Target target;
	private List<WeaponEffect> effects;
	private int distance;
	
	
	public ProjectileInfo(Weapon weapon,int distance) {
		this.waitGap = weapon.getProjectileGap();
		this.effects = weapon.getEffects();
		this.target = weapon.getTarget();
		this.radiusOfHit = weapon.getRadiusOfHit();
		this.distance = distance;
	}
	public int getWaitGap() {
		return waitGap;
	}
}
