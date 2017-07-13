package com.project.weapons.weapon_types;

import java.util.ArrayList;
import java.util.List;

import com.project.DamageType;
import com.project.weapons.Buffing;
import com.project.weapons.Weapon;

public class Shield extends Weapon {

	private Buffing buffing;
	public Shield(int cooldownDuration,double modifier) {
		super(cooldownDuration);
		List<DamageType> damageTypes = new ArrayList<DamageType>();
		damageTypes.add(DamageType.Laser);
		buffing = new Buffing(modifier,damageTypes);
		
	}

}
