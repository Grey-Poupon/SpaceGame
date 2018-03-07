package com.project.weapons;


import java.util.ArrayList;
import java.util.List;

import com.project.DamageType;
import com.project.StatID;
import com.project.ship.Ship;

public class Buffer implements WeaponEffect{
	private List<Double> modifiers;	// new modifiers for damage taken
	private List<DamageType> damageTypes; // types of damage that change
	private float modifier;
	private String toBuff;
	private StatID dictKey;
	public List<Double> getModifiers() {
		return new ArrayList<Double>(modifiers); 
	}


	public List<DamageType> getDamageType() {
		return new ArrayList<DamageType>(damageTypes);
	}


	public Buffer(String toBuff,StatID key, float modifier){
		this.toBuff =toBuff; 
		this.modifier = modifier;
		this.dictKey= key;
	}
	
	public void applyBuff(Ship ship,int x,int y) {
		if(toBuff == "crew") {
			//Needs a function to find crew in specific rooms
			ship.getAllCrew().get(0).setStatModifiers(dictKey, modifier);
		}
		else if(toBuff == "room") {
			ship.getClosestRoom(x,y).setStatModifiers(dictKey,modifier);
		}
		
	}


	@Override
	public Object[] getInfo() {
		// TODO Auto-generated method stub
		return null;
	}


}
