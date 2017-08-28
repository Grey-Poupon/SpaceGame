package com.project.weapons.weapon_types;

import java.awt.Graphics;
import java.util.List;

import com.project.Animation;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class Armour extends Weapon {


	public Armour(int cooldownDuration,Animation anim,List<Double> modifiers, String name,boolean targetSelf,WeaponEffect[] we,int projectileGap) {
		super(cooldownDuration, name, anim,targetSelf,we,projectileGap);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		
	}


}
