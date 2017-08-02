package com.project.battle;


import java.lang.reflect.Array;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.project.Animation;
import com.project.Crew;
import com.project.DamageType;
import com.project.EntityID;
import com.project.Handler;
import com.project.ImageHandler;
import com.project.Main;
import com.project.ScrollableList;
import com.project.Ship;
import com.project.Star;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.weapons.Weapon;

public class BattleScreen extends Main implements Observer{

	private static final long serialVersionUID = -6523236697457665386L;

	private Ship enemyShip;

	private String selectedRoom;

	private ImageHandler overlay;
	private ImageHandler playerHealthbar;
	private ImageHandler enemyHealthbar;
	private ImageHandler loadingScreen;

	private ScrollableList sl;
	
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
		loadingScreen 		 = new ImageHandler(0,0,"res/loadingscreen.png",true,1,1,EntityID.UI);
		Handler.addHighPriorityEntity(loadingScreen);
		rand = new Random();
		for(int i=0; i<40;i++) {
			Star star = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true,0,Main.WIDTH/2,0,Main.HEIGHT,10);
		}
		for(int i=0; i<40;i++) {
			Star star = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true,Main.WIDTH/2,Main.WIDTH,0,Main.HEIGHT,5);
		}
		
		playerShip			 = new Ship    (-200,150,0.05f,16f,"res/Matron",true,EntityID.ship,50,3.5f);
		enemyShip 			 = new Ship    (WIDTH-430,110,0.05f,16f,"res/Matron",true,EntityID.ship,50,3.5f);

		overlay 			 = new ImageHandler  (0,0,"res/Drawn UI 2.png",true,EntityID.UI);
		sl					 = new ScrollableList(playerShip.getCrewButtons(this), 1, 125, 120, 612);
		Animation anim       = new Animation("res/blueFlameSpritesheet.png", 48, 26, 5, 2, 8, 670, 347,4.4f,-1,true);
		ui 					 = new BattleUI(playerShip.getFrontWeapons(),this,playerShip,enemyShip);
		keyIn				 = new BattleKeyInput(this);
		mouseIn				 = new BattleMouseInput(handler,sl);
		playerHealthbar		 = new ImageHandler  (0,3,"res/healthseg.png",true,10,1,EntityID.UI);
		enemyHealthbar		 = new ImageHandler  (750,3,"res/healthseg.png",true,10,1,EntityID.UI);
		
		for(int i =0;i<playerShip.getCrew().size();i++) {
			Crew crew = playerShip.getCrew().get(i);
			System.out.println(crew.getName()+":"+crew.getRaceID().toString()+" "+crew.getGender());
			for(int j = 0;j<crew.getStats().size();j++) {
				System.out.println(Crew.statNames[j]+": "+Byte.toString(crew.getStat(Crew.statNames[j])));
			}
			System.out.println();
		}
		
//		for(int i =0;i<playerShip.getCrew().size();i++) {
//			Crew crew = playerShip.getCrew().get(i);
//			System.out.println(crew.name+":"+crew.getRaceID().toString()+" "+crew.getGender());
//			for(int j = 0;j<crew.getStats().size();j++) {
//				System.out.println(Crew.statNames[j]+": "+Byte.toString(crew.getStat(Crew.statNames[j])));
//			}
//			System.out.println();
//		}
//		
		
		this.addKeyListener(keyIn);	
		this.addMouseListener(mouseIn);
		this.addMouseMotionListener(mouseIn);
		this.addMouseWheelListener(mouseIn);
		
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
		
		if(!isPaused()) {
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
			if(playerShip !=null && enemyShip != null) {
				
				loadingScreen.setVisible(false);
				playerShip.tickLayers();
				enemyShip.tickLayers();
			}
			if(playerHealthbar != null && enemyHealthbar != null) {
				float scale = (float)playerShip.getCurrHealth()/(float)playerShip.getMaxHealth();
				if (scale < 0) {scale = 0;}
				playerHealthbar.setXScale(scale);
				scale = (float)enemyShip.getCurrHealth()/(float)enemyShip.getMaxHealth();
				if (scale < 0) {scale = 0;}
				enemyHealthbar.setXScale(scale);
			}
		}
		
		
	
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
			
			ButtonID ID = (ButtonID) Array.get(arg1, 0);
			int index = (int)Array.get(arg1, 1);
			
			if(ID == ButtonID.BattleWeaponsChoice){
				if(isPlayersTurn && currentPhase==BattlePhases.Weapons ) {
					playersWeaponChoice = index;
					System.out.println("Player is about to use weapon"+playersWeaponChoice+"!");
					nextTurn();
				}
			}
	
			if(ID == ButtonID.BattleEngineChoice){
				if(isPlayersTurn && currentPhase==BattlePhases.Engine ) {
					playersEngineChoice = index;
					System.out.println("Player Engine choice made");
					nextTurn();
				}
			}
			if(ID == ButtonID.Crew){
				BattleUI.changeTootlipSelection(playerShip.getCrew().get(index));
			}
		}
		
	}
}
