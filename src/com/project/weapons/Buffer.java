package com.project.weapons;


import java.util.ArrayList;
import java.util.List;

import com.project.DamageType;

public class Buffer {
	private List<Double> modifiers;	// new modifiers for damage taken
	private List<DamageType> damageTypes; // types of damage that change
	
	
	public List<Double> getModifiers() {
		return new ArrayList<Double>(modifiers); 
	}


	public List<DamageType> getDamageType() {
		return new ArrayList<DamageType>(damageTypes);
	}


	public Buffer(List<Double> modifiers, List<DamageType> damagetypes){
		this.modifiers = modifiers;
		this.damageTypes = damagetypes;

	}

}
