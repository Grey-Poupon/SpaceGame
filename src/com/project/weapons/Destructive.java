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

	public double getAccuracy() {
		return accuracy;
	}

	
	public Destructive(int rateOfFire, int damagePerShot, double accuracy, DamageType damageType,int weaponSwayMod){
		this.rateOfFire=rateOfFire;
		this.damagePerShot =damagePerShot;
		this.accuracy=accuracy;
		this.damageType = damageType;
		this.weaponSwayMod = weaponSwayMod;
		
	}
	public Object[] fire(){
		double[] accuracy = new double[rateOfFire];
		Random rand = new Random();
		for(int i=0; i<getRateOfFire();i++){
			accuracy[i] = 1 ;
			if(rand.nextDouble()>getAccuracy()){// if rand number is less than acc it's a hit
				accuracy[i]=0;
			}
		}
		Object[] damageDealt = {rateOfFire,accuracy,weaponSwayMod,getDamagePerShot(),damageType}; // to return, holds the shots hit and dmg per shot
		return damageDealt;
	}
}
