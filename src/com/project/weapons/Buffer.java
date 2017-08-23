package com.project.weapons;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.project.DamageType;
import com.project.ship.Ship;

public class Buffer implements WeaponEffect{
	private List<Double> modifiers;	// new modifiers for damage taken
	private List<DamageType> damageTypes; // types of damage that change
	private float modifier;
	private String toBuff;
	private String dictKey;
	public List<Double> getModifiers() {
		return new ArrayList<Double>(modifiers); 
	}


	public List<DamageType> getDamageType() {
		return new ArrayList<DamageType>(damageTypes);
	}


	public Buffer(String toBuff,String key, float modifier){
		this.toBuff =toBuff; 
		this.modifier = modifier;
		this.dictKey= key;
	}
	
	public void applyBuff(Ship ship,int x,int y) {
		if(toBuff == "crew") {
			//Needs a function to find crew in specific rooms
			ship.getCrew().get(0).setStatModifiers(dictKey, modifier);
		}
		else if(toBuff == "room") {
			ship.getClosestRoom(x,y).setStatModifiers(dictKey,modifier);
		}
		
	}


	@Override
	public Object[] fire() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public double getAccuracy() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getDamagePerShot() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getRateOfFire() {
		// TODO Auto-generated method stub
		return 0;
	}

}
