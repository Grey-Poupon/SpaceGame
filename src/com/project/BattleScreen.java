package com.project;


import java.util.List;

import com.project.weapons.Weapon;

public class BattleScreen extends Main{
	private Ship enemyShip;
	private String selectedRoom;

	public BattleScreen(){
		playerShip = new Ship(-150,200,"res/octoBitchShip.png",true,EntityID.ship,50,2);
		enemyShip = new Ship(WIDTH-200,200,"res/octoBitchShip.png",true,EntityID.ship,50,2);
		ui = new BattleUI(playerShip.getFrontWeapons());
		keyIn = new BattleKeyInput();
		this.addKeyListener(keyIn);
	}
	
	public void selectRoom(String room){
		selectedRoom = room;
	}
	
	
	
	public void UseWeapon(Ship primary, Ship secondary,int position,boolean isFrontWeapon){
		Weapon weapon = isFrontWeapon ? primary.getFrontWeapon(position) : primary.getBackWeapon(position);// get the weapon to be fired
		
		if(weapon.isBuffer()){ // if its a buffing weapon apply buff
			
			List<DamageType> dmgTypes = weapon.getBuff().getDamageType();
			List<Double> modifiers = weapon.getBuff().getModifiers();
			for(int i=0; i < dmgTypes.size();i++){
				primary.setDamageTakenModifier(dmgTypes.get(i),modifiers.get(i));
			}
		}
		if(weapon.isDebuffer()){ // if its a debuffing weapon apply debuff
			List<DamageType> dmgTypes = weapon.getBuff().getDamageType();
			List<Double> modifiers = weapon.getBuff().getModifiers();
			for(int i=0; i < dmgTypes.size();i++){
				secondary.setDamageTakenModifier(dmgTypes.get(i),modifiers.get(i));
			}
		}
		if(weapon.isDestructive()){// if its a destructive weapon fire it, apply damage
			Object[] damageDealt = weapon.fire();
			for(int i=0;i<(int)damageDealt[0];i++){
				secondary.takeDamage((int)damageDealt[1],(DamageType)damageDealt[2]);
			}
		}
	}
}
