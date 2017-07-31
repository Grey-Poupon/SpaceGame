package com.project.weapons.weapon_types;

import java.util.ArrayList;
import java.util.List;

import com.project.DamageType;
import com.project.weapons.Buffer;
import com.project.weapons.Weapon;

public class Armour extends Weapon {

	private Buffer buffing;
	
	public Armour(int cooldownDuration,List<Double> modifiers, String name,List<DamageType> dt) {
		super(cooldownDuration, name);
		buffing = new Buffer(modifiers,dt);
		this.isBuffer = true;
	}

	@Override
	public Buffer getBuff() {
		return buffing;
	}

}
