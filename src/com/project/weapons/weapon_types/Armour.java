package com.project.weapons.weapon_types;

import java.util.List;

import com.project.Animation;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class Armour extends Weapon {


	public Armour(int cooldownDuration,Animation[] anims,List<Double> modifiers, String name,boolean targetSelf,WeaponEffect[] we) {
		super(cooldownDuration, name, anims,targetSelf,we);
		this.isBuffer = true;
	}


}
