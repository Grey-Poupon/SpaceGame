package com.project.weapons;


import java.util.List;

import com.project.DamageType;

public class Buffing {
	private double modifier;	// new modifier for damage taken
	private List<DamageType> damageTypes; // type of damage
	
	
	public double getModifier() {
		return modifier;
	}


	public List<DamageType> getDamageType() {
		return damageTypes;
	}


	public Buffing(double modifier, List<DamageType> damagetypes){
		this.modifier = modifier;
		this.damageTypes = damagetypes;

	}

}
