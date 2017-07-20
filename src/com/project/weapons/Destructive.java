package com.project.weapons;

import java.util.Random;

import com.project.DamageType;


public class Destructive {
	private int rateOfFire;
	private int damagePerShot;
	private DamageType damageType;
	private double accuracy;

	public int getRateOfFire() {
		return rateOfFire;
	}

	public int getDamagePerShot() {
		return damagePerShot;
	}

	public double getAccuracy() {
		return accuracy;
	}

	
	public Destructive(int rateOfFire, int damagePerShot, double accuracy, DamageType damageType){
		this.rateOfFire=rateOfFire;
		this.damagePerShot =damagePerShot;
		this.accuracy=accuracy;
		this.damageType = damageType;
		
	}
	public Object[] fire(){
		int shotsHit=0; // counter for shots that hit
		Random rand = new Random();
		for(int i=0; i<getRateOfFire();i++){
			if(rand.nextDouble()<=getAccuracy()){// if rand number is less than acc it's a hit
				shotsHit++;
			}
		}
		Object[] damageDealt = {shotsHit,getDamagePerShot(),damageType}; // to return, holds the shots hit and dmg per shot
		return damageDealt;
	}
}
