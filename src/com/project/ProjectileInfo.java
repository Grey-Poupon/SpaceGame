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
	private int distanceToEnd;
	private int startSpeed;
	private int velocity;
	
	public ProjectileInfo(Weapon weapon,int distance,int shipVel) {
		this.waitGap = weapon.getProjectileGap();
		this.effects = weapon.getEffects();
		this.target = weapon.getTarget();
		this.radiusOfHit = weapon.getRadiusOfHit();
		this.startSpeed = weapon.getSpeed();
		this.distanceToEnd = distance;
		this.velocity = startSpeed + shipVel;
	}
	public int getWaitGap() {
		return waitGap;
	}
	public int getStartSpeed(){
		return startSpeed;
	}
	public int getVelocity(){
		return velocity;
	}
	public int getDistanceToEnd() {
		return distanceToEnd;
	}
	public void updateDistance(){
		distanceToEnd-=velocity;
	}

}
