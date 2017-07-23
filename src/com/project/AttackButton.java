package com.project;

import com.project.weapons.Weapon;

public class AttackButton   {
	
	private Weapon weapon;
	private boolean activated;

	public void pressed(Ship ship){
		ship.takeDamage(5, DamageType.Laser);
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	public void activate(BattleScreen bs, Ship pShip, Ship eShip){
		
	}
	public void activated(){
		activated = true;
	}
	public boolean isActivated(){
		return activated;
	}

	public void setActivated(boolean act){
		this.activated =act;
	}
}
