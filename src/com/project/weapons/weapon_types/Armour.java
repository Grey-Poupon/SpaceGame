package com.project.weapons.weapon_types;

import java.util.List;

import com.project.Animation;
import com.project.DamageType;
import com.project.weapons.Buffer;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class Armour extends Weapon {

	private Buffer buffing;
	
	public Armour(int cooldownDuration,List<Animation> anims,List<Double> modifiers, String name,List<DamageType> dt,boolean targetSelf,WeaponEffect[] we) {
		super(cooldownDuration, name, anims,targetSelf,we);
		//buffing = new Buffer(modifiers,dt);
		this.isBuffer = true;
	}

	@Override
	public Buffer getBuff() {
		return buffing;
	}

}
