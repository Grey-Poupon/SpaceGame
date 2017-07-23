package com.project;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.project.weapons.Weapon;

public class BattleScreen extends Main implements Observer{
	private Ship enemyShip;
	private String selectedRoom;
	private Entity overlay;
	private Entity playerHealthbar;
	private Entity enemyHealthbar;
	private ArrayList<Entity> playerHealthContainer = new ArrayList<Entity>();
	private ArrayList<Entity> enemyHealthContainer = new ArrayList<Entity>();
	private int ticker;
	
	public BattleScreen(){
		
		playerShip			 = new Ship    (-150,200,"res/octoBitchShip.png",true,EntityID.ship,50,2);
		enemyShip 			 = new Ship    (WIDTH-200,200,"res/octoBitchShip.png",true,EntityID.ship,50,2);
		overlay 			 = new Entity  (0,0,"res/Drawn UI.png",true,EntityID.UI);
		playerHealthbar 	 = new Entity  (0,0,"res/healthbar.png",true, EntityID.UI);
		enemyHealthbar 		 = new Entity  (500,0,"res/healthbar.png",true, EntityID.UI);
		ui 					 = new BattleUI(playerShip.getFrontWeapons(),this,playerShip,enemyShip);
		keyIn				 = new BattleKeyInput((BattleUI) ui);
		mouseIn				 = new BattleMouseInput(ui);
		
		this.addKeyListener(keyIn);	
		this.addMouseListener(mouseIn);
		this.addMouseMotionListener(mouseIn);
		
		for(int i = 0;i<normaliseHealthBar(playerShip.getMaxHealth(),playerShip.getCurrHealth());i++){
			
			Entity healthseg = new Entity(14+i*7,11,"res/healthseg.png",true,EntityID.UI);
			playerHealthContainer.add(healthseg);
		}
		for(int i = 0;i<normaliseHealthBar(enemyShip.getMaxHealth(),enemyShip.getCurrHealth());i++){
			
			Entity healthseg = new Entity(500+14+i*7,11,"res/healthseg.png",true,EntityID.UI);
			enemyHealthContainer.add(healthseg);
		}
		ticker= 0;
	}
	
	public void selectRoom(String room){
		
		selectedRoom = room;
		
	}
	
	public void tick(){
		for(int i = 0; i<playerHealthContainer.size();i++){
			if(i>normaliseHealthBar(playerShip.getMaxHealth(),playerShip.getCurrHealth())){
				playerHealthContainer.get(i).setVisible(false);
			}
		}
		for(int i = 0; i<enemyHealthContainer.size();i++){
			if(i>normaliseHealthBar(enemyShip.getMaxHealth(),enemyShip.getCurrHealth())){
				enemyHealthContainer.get(i).setVisible(false);
			}
		}
		
		//code to check the health bar
		//ticker++;
		//if(ticker>200){
		//	playerShip.takeDamage(5, DamageType.Laser);
		//	enemyShip.takeDamage(7, DamageType.Laser);
		//	ticker = 0;
		//}
		
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

	@Override
	public void update(Observable arg0, Object arg1) {// this gets notified by the click function inside button
		if(arg0 instanceof Button){
			if(arg1 == ButtonID.BattleWeaponsMenuSelection1){
				UseWeapon(playerShip,enemyShip,0,true);
				System.out.println("Fire Weapon 1!");
			}
			if(arg1 == ButtonID.BattleWeaponsMenuSelection2){
				UseWeapon(playerShip,enemyShip,1,true);
				System.out.println("Fire Weapon 2!");

			}
			if(arg1 == ButtonID.BattleWeaponsMenuSelection3){
				UseWeapon(playerShip,enemyShip,2,true);
				System.out.println("Fire Weapon 3!");

			}
			if(arg1 == ButtonID.BattleWeaponsMenuSelection4){
				UseWeapon(playerShip,enemyShip,3,true);
				System.out.println("Fire Weapon 4!");

			}
		}
		
	}
}
