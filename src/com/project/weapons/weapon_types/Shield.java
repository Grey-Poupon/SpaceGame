package com.project.weapons.weapon_types;

import java.util.ArrayList;
import java.util.List;

import com.project.DamageType;
import com.project.weapons.Buffer;
import com.project.weapons.Weapon;

public class Shield extends Weapon {

	private Buffer buffing;
	@Override
	public Buffer getBuff() {
		return buffing;
	}
	public Shield(int cooldownDuration,List<Double> modifiers) {
		super(cooldownDuration);
		List<DamageType> damageTypes = new ArrayList<DamageType>();
		damageTypes.add(DamageType.Laser);
		buffing = new Buffer(modifiers,damageTypes);
		this.isBuffer = true;
	}

}
