package com.project.weapons;

import java.util.Random;

import com.project.DamageType;


public class Destructive implements WeaponEffect {
	private int rateOfFire;
	private int damagePerShot;
	private DamageType damageType;
	private double accuracy;
	private int weaponSwayMod;
	
	public int getRateOfFire() {
		return rateOfFire;
	}

	public int getDamagePerShot() {
		return damagePerShot;
	}

	public DamageType getDamageType() {
		return damageType;
	}

	public void setDamageType(DamageType damageType) {
		this.damageType = damageType;
	}

	public int getWeaponSwayMod() {
		return weaponSwayMod;
	}

	public void setWeaponSwayMod(int weaponSwayMod) {
		this.weaponSwayMod = weaponSwayMod;
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

	
	public Destructive(int rateOfFire, int damagePerShot, float accuracy, DamageType damageType,int weaponSwayMod){
		this.rateOfFire=rateOfFire;
		this.damagePerShot =damagePerShot;
		this.accuracy=accuracy;
		this.damageType = damageType;
		this.weaponSwayMod = weaponSwayMod;
		
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
		Object[] damageDealt = {rateOfFire,accuracy,weaponSwayMod,getDamagePerShot(),damageType}; // to return, holds the shots hit and dmg per shot
		return damageDealt;
	}
}
