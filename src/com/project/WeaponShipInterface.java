package com.project;

import java.util.ArrayList;
import java.util.List;

import com.project.ship.Ship;
import com.project.weapons.Weapon;
import com.project.weapons.WeaponEffect;

public class WeaponShipInterface {
	private Ship chaser;
	private Ship chased;
	private int gap;
	private List<WeaponEffect> effects = new ArrayList<WeaponEffect>();
	private List<WeaponEffect> counterEffects = new ArrayList<WeaponEffect>();

	public WeaponShipInterface(Ship chaser, Ship chased) {
		this.chaser = chaser;
		this.chased = chased;
		this.gap = chaser.getDistanceToEnd() - chased.getDistanceToEnd();
	}
	public void add (Ship target, Ship source, Weapon weapon){
		
	}
}
