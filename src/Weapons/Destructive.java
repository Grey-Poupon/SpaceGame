package Weapons;

import java.util.Random;

public class Destructive {
	private int rateOfFire;
	private int damagePerShot;
	public int getRateOfFire() {
		return rateOfFire;
	}

	public int getDamagePerShot() {
		return damagePerShot;
	}

	public double getAccuracy() {
		return accuracy;
	}

	private double accuracy;
	
	public Destructive(int rateOfFire, int damagePerShot, double accuracy){
		this.rateOfFire=rateOfFire;
		this.damagePerShot =damagePerShot;
		this.accuracy=accuracy;
		
	}
	public int[] fire(){
		int[] damageDealt = {0,getDamagePerShot()}; // to return, holds the shots hit and dmg per shot
		// instead of a counter variable i just used the first item in the array
		Random rand = new Random();
		for(int i=0; i<getRateOfFire();i++){
			if(rand.nextDouble()<=getAccuracy()){
				damageDealt[0]++;
			}
		}
		return damageDealt;
	}
}
