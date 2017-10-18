package com.project.weapons;

import java.util.Random;

import com.project.DamageType;


public class Destructive implements WeaponEffect {
	private int rateOfFire;
	private int damagePerShot;
	private double accuracy;
	private boolean isPhysical;
	private int radiusOfHit;
	public int getRateOfFire() {
		return rateOfFire;
	}

	public int getDamagePerShot() {
		return damagePerShot;
	}


	public void setRateOfFire(int rateOfFire) {
		this.rateOfFire = rateOfFire;
	}

	public void setDamagePerShot(int damagePerShot) {
		this.damagePerShot = damagePerShot;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public double getAccuracy() {
		return accuracy;
	}

	
	public Destructive(int rateOfFire, int damagePerShot, float accuracy, boolean isPhysical, int radiusOfHit){
		this.rateOfFire=rateOfFire;
		this.damagePerShot =damagePerShot;
		this.accuracy=accuracy;
		this.isPhysical = isPhysical;
		this.radiusOfHit = radiusOfHit;
		
	}
	public Object[] fire(){
		float[] accuracy = new float[rateOfFire];
		Random rand = new Random();
		for(int i=0; i<getRateOfFire();i++){
			accuracy[i] = 1 ;
			if(rand.nextFloat()>getAccuracy()){// if rand number is less than acc it's a hit
				accuracy[i]=0;
			}
		}
		Object[] damageDealt = {rateOfFire,accuracy,getDamagePerShot(),isPhysical,radiusOfHit}; // to return, holds the shots hit and dmg per shot
		return damageDealt;
	}
}
