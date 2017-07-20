package com.project;


import java.util.ArrayList;
import java.util.List;

import com.project.weapons.Weapon;

public class BattleScreen extends Main{
	private Ship enemyShip;
	private String selectedRoom;
	private Entity overlay;
	private Entity healthbar;
	private ArrayList<Entity> healthContainer = new ArrayList<Entity>();
	private int ticker;
	public BattleScreen(){
		playerShip = new Ship(-150,200,"res/octoBitchShip.png",true,EntityID.ship,50,2);
		enemyShip = new Ship(WIDTH-200,200,"res/octoBitchShip.png",true,EntityID.ship,50,2);
		overlay = new Entity(0,0,"res/Drawn UI.png",true,EntityID.UI);
		healthbar = new Entity(0,0,"res/healthbar.png",true, EntityID.UI);
		ui = new BattleUI(playerShip.getFrontWeapons());
		keyIn = new BattleKeyInput();
		this.addKeyListener(keyIn);
		this.addMouseListener(new BattleMouseInput());
		this.addMouseMotionListener(new BattleMouseInput());
		
		
		
		for(int i = 0;i<normaliseHealthBar(playerShip.getMaxHealth(),playerShip.getCurrHealth());i++){
			Entity healthseg = new Entity(14+i*7,11,"res/healthseg.png",true,EntityID.UI);
			healthContainer.add(healthseg);
		}
		ticker= 0;
	}
	
	public void selectRoom(String room){
		
		selectedRoom = room;
		
	}
	
	public void tick(){
		for(int i = 0; i<healthContainer.size();i++){
			if(i>normaliseHealthBar(playerShip.getMaxHealth(),playerShip.getCurrHealth())){
				healthContainer.get(i).setVisible(false);
			}
		}
		
		
		//code to check the health bar
		ticker++;
		if(ticker>200){
			playerShip.takeDamage(5, DamageType.Laser);
			ticker = 0;
		}
		
	}

	private int normaliseHealthBar(int maxHealth, int currHealth){
		return currHealth*50/maxHealth;
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
