package com.project;

import java.awt.Point;
import java.util.List;
import java.util.Observable;

import com.project.ship.Slot;
import com.project.weapons.Target;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class ProjectileInfo extends Observable{
	private int waitGap;
	private int radiusOfHit;
	private Target target;
	public List<WeaponEffect> effects;
	private int distanceToEnd;
	private int startSpeed;
	private int velocity;
	private Slot slot;
	private Point dest = null;
	private boolean fromChaser;
	private Animation inboundAnimation;
	private Animation outboundAnimation;
	
	public ProjectileInfo(Weapon weapon,int distance,int shipVel,boolean fromChaser) {
		this.waitGap = weapon.getProjectileGap();
		this.effects = weapon.getEffects();
		this.target = weapon.getTarget();
		this.radiusOfHit = weapon.getRadiusOfHit();
		this.startSpeed = weapon.getSpeed();
		this.distanceToEnd = distance;
		this.velocity = startSpeed + shipVel;
		this.slot = weapon.getSlot();
		this.inboundAnimation = weapon.getInboundAnimation(); 
		this.outboundAnimation = weapon.getOutboundAnimation();
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
	public Slot getSlot() {
		return slot;
	}
	public void addDest(Point point) {
		this.dest = point;
	}
	public Point getDest(){
		return dest;
	}
	public Target getTarget() {
		return target;
	}
	public void doEffects(){
		notifyObservers();
	}
	public boolean isFromChaser() {
		return fromChaser;
	}
	public Animation getOutboundAnimation() {
		return outboundAnimation;
	}
	public Animation getInboundAnimation() {
		return inboundAnimation;
	}

}
