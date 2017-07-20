package com.project;

import com.project.weapons.Weapon;

public class AttackButton extends Button {
	
	private Weapon weapon;
	private boolean activated;
	public AttackButton(int x, int y, int width, int height, Weapon weapon) {
		super(x, y, width, height);
		this.weapon = weapon;
		activated =false;
	}
	public static void pressed(Ship ship){
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
