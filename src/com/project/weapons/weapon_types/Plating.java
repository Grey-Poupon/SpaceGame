package com.project.weapons.weapon_types;

import java.util.ArrayList;
import java.util.List;

import com.project.DamageType;
import com.project.weapons.Buffer;
import com.project.weapons.Weapon;

public class Plating extends Weapon {

	private Buffer buffing;
	
	public Plating(int cooldownDuration,List<Double> modifiers, String name) {
		super(cooldownDuration, name);
		List<DamageType> damageTypes = new ArrayList<DamageType>();
		damageTypes.add(DamageType.Blunt);
		damageTypes.add(DamageType.Piercing);
		buffing = new Buffer(modifiers,damageTypes);
		this.isBuffer = true;
	}

	@Override
	public Buffer getBuff() {
		return buffing;
	}

}
