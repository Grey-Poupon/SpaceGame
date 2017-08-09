package com.project.battle;


import java.awt.Graphics;
import java.awt.Point;
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
	
	private final int xDistanceSystemLocation   = 486;
	private final int yDistanceSystemLocation   = 26;
	private final int  distanceSystemLineLength = 100;
	private final int  distanceSystemDotSize    = 16;
	private final int  escapeDistance           = 1000;
	private int        shipDistance             = 500;
	
	private ScrollableList sl;
	private Point playerShotLocation;
	private Point enemyShotLocation;
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
		handler = new BattleHandler(this);
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
		Animation anim       = new Animation("res/octiod_lazer_1_Anim.png", 97, 21, 4, 2,1,3,3,9, 8, 670, 347,1f,-1,true);
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
		
		if(isPlayersTurn ^ playerIsChaser || currentPhase == BattlePhases.Final ) { // if its the chasers turn // fyi chaser goes last
			currentPhasePointer++;
		}
		if(currentPhasePointer >= BattlePhases.phases.length) {
			currentPhasePointer -= BattlePhases.phases.length;
			isPlayersTurn = !playerIsChaser;
		}
		if (currentPhase == BattlePhases.WeaponsButton) {
			isPlayersTurn = !playerIsChaser;
			if(!(playerIsChaser ^ playerIsChaser)) {
				currentPhasePointer++;;
			}
		}
		currentPhase= BattlePhases.phases[currentPhasePointer];
		isPlayersTurn = !isPlayersTurn;
		String phase = "";
		if(currentPhase == BattlePhases.WeaponsButton) {phase = "Weapons Button";}
		if(currentPhase == BattlePhases.WeaponsClick) {phase = "Weapons Click";}
		if(currentPhase == BattlePhases.Final) {phase = "final";}
		String turn = isPlayersTurn ? "Players" : "Enemys";
		System.out.println("\nIts the "+phase+" phase and the "+turn+" turn");
		
		
	}
	public void tick(){
		
		if(!isPaused()) {
			super.tick();

			if(!isPlayersTurn) {
				if(currentPhase == BattlePhases.WeaponsButton) {
					enemyWeaponChoice=0;
					System.out.println("Enemy Weapon Reveal");
					nextTurn();
				}
				else if (currentPhase == BattlePhases.WeaponsClick){
					enemyShotLocation = new Point(800,80);
					nextTurn();
				}
				else if (currentPhase == BattlePhases.Engine){
					enemyEngineChoice=0;
					System.out.println("Enemy Engine Reveal");
					nextTurn();
				}			}
			if(currentPhase == BattlePhases.Final) {
				System.out.println("Weapons Firing");
				moveShips(playerShip,enemyShip);
				UseWeapon(playerShip, enemyShip, playersWeaponChoice, true,playerShotLocation);
				UseWeapon(enemyShip, playerShip, enemyWeaponChoice, true,enemyShotLocation);
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
	private void calculateDistances(Ship chaser, Ship chased) {
		Ship ship  = null;
		Ship ship2 = null;
		if(chaser.getSpeedChange()>0 ^ chased.getSpeedChange()>0) {
			if(chaser.getSpeedChange()>0) {
				ship  = chaser; 
				ship2 = chased;
			}
		}
		else {
			ship  = chaser;
			ship2 = chased;}
		if(ship.getDistanceToEnd()==0 && ship.getSpeedChange()>0) {
			int dist = -(ship.getSpeedChange()+ship2.getSpeedChange());
			ship2.changeDistanceToEnd(-dist);
			this.shipDistance-=-dist;
		}
		else {
			int dist = ship.getSpeedChange()+ship2.getSpeedChange();
			ship2.changeDistanceToEnd(dist);
			this.shipDistance-=dist;
		}
		

	}
	private void drawDistanceSystem(Ship chaser,Ship chased,Graphics g) {
		
	}
	
	private void moveShips(Ship chaser, Ship chased) {
		shipDistance += (chased.getSpeed() - chaser.getSpeed());
		if(shipDistance < 0) {shipDistance = 0;}
	}

	public void UseWeapon(Ship primary, Ship secondary,int position,boolean isFrontWeapon,Point shot){
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
			double[] accuracy = (double[]) damageDealt[1];
			int newX,newY,extraDmg;
			for(int i=0;i<(int)damageDealt[0];i++) {
				if(accuracy[i]!=0) {
					newX = (int) (rand.nextBoolean() ? shot.x+((int)damageDealt[2]*(1/accuracy[i])):shot.x-((int)damageDealt[2]*(1/accuracy[i])));
					newY = (int) (rand.nextBoolean() ? shot.y+((int)damageDealt[2]*(1/accuracy[i])):shot.y-((int)damageDealt[2]*(1/accuracy[i])));
					extraDmg = secondary.roomDamage(newX, newY);
					secondary.takeDamage(extraDmg+(int)damageDealt[3], (DamageType)damageDealt[4]);
				}
			}
			
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {// this gets notified by the click function inside button

		if(arg0 instanceof Button){
			
			ButtonID ID = (ButtonID) Array.get(arg1, 0);
			int index = (int)Array.get(arg1, 1);
			
			if(ID == ButtonID.BattleWeaponsChoice){
				if(isPlayersTurn && currentPhase==BattlePhases.WeaponsButton ) {
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
	
	public void checkClick(int x, int y) {
		if(enemyShip.isShipClicked(x, y) && currentPhase == BattlePhases.WeaponsClick) {
			playerShotLocation = new Point(x,y);
			nextTurn();
		}
	}
}
