package com.project.battle;


import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.project.Animation;
import com.project.Crew;
import com.project.DamageType;
import com.project.DistanceSystem;
import com.project.EntityID;
import com.project.Handler;
import com.project.ImageHandler;
import com.project.Main;
import com.project.ScrollableList;
import com.project.Star;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Ship;
import com.project.weapons.Weapon;

public class BattleScreen extends Main implements Observer{

	private static final long serialVersionUID = -6523236697457665386L;

	private Ship enemyShip;

	private String selectedRoom;

	private ImageHandler overlay;
	private ImageHandler playerHealthbar;
	private ImageHandler enemyHealthbar;
	private ImageHandler loadingScreen;
	

	private int projectileWaitCounter;
	private int projectileWaitTurn = 1;
	private DistanceSystem ds;
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
		
		
		playerShip			 = new Ship    (-200,150,0.05f,16f,"res/Matron",true,EntityID.ship,50,3.5f,true);
		enemyShip 			 = new Ship    (WIDTH-430,110,0.05f,16f,"res/Matron",true,EntityID.ship,50,3.5f,false);
		
		for(int i=0; i<40;i++) {
			Star star = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true,0,Main.WIDTH/2,0,Main.HEIGHT,playerShip);
		}
		for(int i=0; i<40;i++) {
			Star star = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true,Main.WIDTH/2,Main.WIDTH,0,Main.HEIGHT,enemyShip);
		}
		
		ds 					 = new DistanceSystem(500, playerShip.getDistanceToEnd(), enemyShip.getDistanceToEnd());
		overlay 			 = new ImageHandler  (0,0,"res/Drawn UI 2.png",true,EntityID.UI);
		sl					 = new ScrollableList(playerShip.getCrewButtons(this), 2, 55, 100, 664,100,100,true);
		Animation anim       = new Animation("res/octiod_lazer_1_Anim.png", 97, 21, 4, 2,1,3,3,9, 12, 670, 347,1f,-1,true,Collections.emptyList());
		ui 					 = new BattleUI(playerShip.getFrontWeapons(),this,playerShip,enemyShip);
		keyIn				 = new BattleKeyInput(this);
		mouseIn				 = new BattleMouseInput(handler);
		playerHealthbar		 = new ImageHandler  (2,2,"res/healthseg.png",true,1,1,EntityID.UI);
		enemyHealthbar		 = new ImageHandler  (797,2,"res/healthseg.png",true,1,1,EntityID.UI);
		
		for(int i =0;i<playerShip.getCrew().size();i++) {
			Crew crew = playerShip.getCrew().get(i);
			System.out.println(crew.getName()+":"+crew.getRaceID().toString()+" "+crew.getGender());
			for(int j = 0;j<crew.getStats().size();j++) {
				System.out.println(Crew.statNames[j]+": "+Byte.toString(crew.getStat(Crew.statNames[j])));
			}
			System.out.println();
		}
		
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
		if(currentPhase == BattlePhases.Engine) {phase = "Engine";}
		if(currentPhase == BattlePhases.Final) {phase = "final";}
		String turn = isPlayersTurn ? "Players" : "Enemys";
		System.out.println("\nIts the "+phase+" phase and the "+turn+" turn");
		
		
	}
	@Override
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
				playerShip.setSpeed(100);
				enemyShip.setSpeed(200);
				ds.calculateDistances(playerShip, enemyShip);
				UseWeapon(playerShip, enemyShip, playersWeaponChoice, true,playerShotLocation);
				UseWeapon(enemyShip, playerShip, enemyWeaponChoice, true,enemyShotLocation);
				currentPhase = BattlePhases.Wait;
			}
			if(currentPhase == BattlePhases.Wait) {
				if(projectileWaitCounter==0) {
				projectileWaitCounter = weaponFireAnimation(projectileWaitTurn, playerShip, playersWeaponChoice);
				}
				projectileWaitCounter--;
				if(projectileWaitCounter == 0) {
					projectileWaitTurn++;
					projectileWaitCounter = weaponFireAnimation(projectileWaitTurn, playerShip, playersWeaponChoice);
					if(projectileWaitTurn > 3) {
						projectileWaitTurn = 1;
						currentPhase = BattlePhases.Final;
						nextTurn();
					}
				}
			}
			if(playerShip !=null && enemyShip != null) {
				
				loadingScreen.setVisible(false);
				playerShip.tickLayers();
				enemyShip.tickLayers();
			}
			if(playerHealthbar != null && enemyHealthbar != null) {
				float scale = ((float)playerShip.getCurrHealth()/(float)playerShip.getMaxHealth())*1.2f;
				if (scale < 0) {scale = 0;}
				playerHealthbar.setXScale(scale);
				scale = ((float)enemyShip.getCurrHealth()/(float)enemyShip.getMaxHealth())*1.2f;
				if (scale < 0) {scale = 0;}
				enemyHealthbar.setXScale(scale);
			}
		}
		
		
	
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
	private int weaponFireAnimation(int stage,Ship primary, int position) {
		int ticksToWait = 0;
		if(stage == 1) {
			Animation projectile = primary.getFrontWeapon(0).getAnimation(0);
			int yVel = projectile.getYVel();
			int xVel = projectile.getXVel();;
			int xPixelsToMove = projectile.getXPixelsToMove();
			int yPixelsToMove = projectile.getYPixelsToMove();
			
			// tickToWait = max number of ticks needed;
			if(xVel == 0 && yVel > 0) {ticksToWait = Math.abs(yPixelsToMove/yVel);}
			if(yVel == 0 && xVel > 0) {ticksToWait = Math.abs(xPixelsToMove/xVel);}
			else {ticksToWait = Math.abs(yPixelsToMove/yVel) > Math.abs(xPixelsToMove/xVel) ?  Math.abs(yPixelsToMove/yVel): Math.abs(xPixelsToMove/xVel);}
			
			projectile.start();
		}
		if(stage == 2) {
			ticksToWait = ds.getShipDistanceCurrent()/4;
		}
		if(stage == 3) {
			Animation projectile = primary.getFrontWeapon(position).getAnimation(1);
			projectile.start();
			int yVel = projectile.getYVel();
			int xVel = projectile.getXVel();;
			int xPixelsToMove = projectile.getXPixelsToMove();
			int yPixelsToMove = projectile.getYPixelsToMove();
			// tickToWait = max number of ticks needed;
			if(xVel == 0 && yVel > 0) {ticksToWait = Math.abs(yPixelsToMove/yVel);}
			if(yVel == 0 && xVel > 0) {ticksToWait = Math.abs(xPixelsToMove/xVel);}
			else {ticksToWait = Math.abs(yPixelsToMove/yVel) > Math.abs(xPixelsToMove/xVel) ?  Math.abs(yPixelsToMove/yVel): Math.abs(xPixelsToMove/xVel);}
		}
		return ticksToWait;

	}
	@Override
	public void update(Observable arg0, Object arg1) {// this gets notified by the click function inside button

		if(arg0 instanceof Button){
			
			ButtonID ID = (ButtonID) Array.get(arg1, 0);
			int index   = (int)Array.get(arg1, 1);
			int button  = (int)Array.get(arg1, 2);
			if(button == MouseEvent.BUTTON1) {
				
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
					BattleUI.changeTootlipSelection(playerShip.getCrew().get(index),"room");
				}
			}
			
			if(button == MouseEvent.BUTTON3) {
				if(ID == ButtonID.Crew) {
					BattleUI.changeTootlipSelection(playerShip.getCrew().get(index),"stats");
				}
			}
		}
	}
	
	public boolean checkClick(int x, int y) {
		boolean clicked = enemyShip.isShipClicked(x, y);
		if( clicked && currentPhase == BattlePhases.WeaponsClick) {
			playerShotLocation = new Point(x,y);
			nextTurn();
		}
		return clicked;
	}
}
