package com.project;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.project.weapons.Weapon;

public class BattleScreen extends Main implements Observer{
	private Ship enemyShip;

	private String selectedRoom;
	private Entity overlay;
	private Entity playerHealthbar;
	private Entity enemyHealthbar;
	private ArrayList<Entity> playerHealthContainer = new ArrayList<Entity>();
	private ArrayList<Entity> enemyHealthContainer = new ArrayList<Entity>();
	
	private int currentPhasePointer = 0;
	private BattlePhases currentPhase = BattlePhases.phases[currentPhasePointer];
	private int playersWeaponChoice;
	private int playersEngineChoice;
	private int enemyWeaponChoice;
	private int enemyEngineChoice;
	private Random rand;
	private boolean playerIsChaser = true;
	private boolean isPlayersTurn = playerIsChaser;// chaser goes first
	
	public BattleScreen(){
		
		rand = new Random();
		for(int i=0; i<40;i++) {
			Star star = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true);
		}
		
		playerShip			 = new Ship    (0,200,0.05f,16f,"res/Matron",true,EntityID.ship,50,3);
		enemyShip 			 = new Ship    (WIDTH-200,200,0.05f,16f,"res/Matron",true,EntityID.ship,50,3);
		overlay 			 = new Entity  (0,0,"res/Drawn UI.png",true,EntityID.UI);
		playerHealthbar 	 = new Entity  (0,0,"res/healthbar.png",true, EntityID.UI);
		enemyHealthbar 		 = new Entity  (500,0,"res/healthbar.png",true, EntityID.UI);
		Animation anim       = new Animation("res/spritesheetTest.png", 80, 80, 2, 5, 1, 200, 200);
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
	}
	
	public void selectRoom(String room){
		
		selectedRoom = room;
		
	}
	private void nextTurn() {
		
		if(isPlayersTurn ^ playerIsChaser || currentPhase == BattlePhases.Final ) { // if its the chasers turn
			currentPhasePointer++;
		}
		if(currentPhasePointer >= BattlePhases.phases.length) {
			currentPhasePointer -= BattlePhases.phases.length;
			isPlayersTurn = !playerIsChaser;
		}
		currentPhase= BattlePhases.phases[currentPhasePointer];
		isPlayersTurn = !isPlayersTurn;
		
		String phase = BattlePhases.Weapons == currentPhase ? "Weapon":"Engine";
		if(currentPhase == BattlePhases.Final) {phase = "final";}
		String turn = isPlayersTurn ? "Players" : "Enemys";
		System.out.println("\nIts the "+phase+" phase and the "+turn+" turn");
		
		

	}
	public void tick(){
		super.tick();

		if(!isPlayersTurn) {
			if(currentPhase == BattlePhases.Weapons) {
				enemyWeaponChoice=0;
				System.out.println("Enemy Weapon Reveal");
				nextTurn();
			}
			else {
				enemyEngineChoice=0;
				System.out.println("Enemy Engine Reveal");
				nextTurn();
			}
		}
		if(currentPhase == BattlePhases.Final) {
			System.out.println("Weapons Firing");
			UseWeapon(playerShip, enemyShip, playersWeaponChoice, true);
			UseWeapon(enemyShip, playerShip, enemyWeaponChoice, true);
			nextTurn();

		}
	
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
		try {
			playerShip.tickLayers();
		}catch(Exception e) {e.printStackTrace();}
		try {
			enemyShip.tickLayers();

		}catch(Exception e) {}
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
				if(isPlayersTurn && currentPhase==BattlePhases.Weapons ) {
					playersWeaponChoice =0;
					System.out.println("Player is about to use weapon"+playersWeaponChoice+"!");
					nextTurn();
				}
				//UseWeapon(playerShip,enemyShip,0,true);
			}
			if(arg1 == ButtonID.BattleWeaponsMenuSelection2){
				if(isPlayersTurn && currentPhase==BattlePhases.Weapons ) {
					playersWeaponChoice = 1;
					System.out.println("Player is about to use weapon"+playersWeaponChoice+"!");
					nextTurn();
				}
				//UseWeapon(playerShip,enemyShip,1,true);

			}
			if(arg1 == ButtonID.BattleWeaponsMenuSelection3){
				if(isPlayersTurn && currentPhase==BattlePhases.Weapons ) {
					playersWeaponChoice = 2;
					System.out.println("Player is about to use weapon"+playersWeaponChoice+"!");
					nextTurn();
				}
				//UseWeapon(playerShip,enemyShip,2,true);

			}
			if(arg1 == ButtonID.BattleWeaponsMenuSelection4){
				if(isPlayersTurn && currentPhase==BattlePhases.Weapons ) {
					playersWeaponChoice = 3;
					System.out.println("Player is about to use weapon"+playersWeaponChoice+"!");
					nextTurn();
				}
				//UseWeapon(playerShip,enemyShip,3,true);

			}
			if(arg1 == ButtonID.BattleEngineChoice){
				if(isPlayersTurn && currentPhase==BattlePhases.Engine ) {
					playersEngineChoice = 0;
					System.out.println("Player Engine choice made");
					nextTurn();
				}
				//UseWeapon(playerShip,enemyShip,3,true);

			}
		}
		
	}
}
