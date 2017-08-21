package com.project.battle;


import java.awt.Point;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.project.Animation;
import com.project.DamageType;
import com.project.DistanceSystem;
import com.project.EntityID;
import com.project.Graph;
import com.project.Handler;
import com.project.ImageHandler;
import com.project.Main;
import com.project.MathFunctions;
import com.project.MouseInput;
import com.project.ScrollableList;
import com.project.Star;
import com.project.Text;
import com.project.TooltipSelectionID;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Ship;
import com.project.weapons.Weapon;
import com.project.weapons.weapon_types.FireableWeapon;

public class BattleScreen extends Main {

	private static final long serialVersionUID = -6523236697457665386L;

	public Ship enemyShip;

	private String selectedRoom;

	private ImageHandler overlay;
	private ImageHandler playerHealthbar;
	private ImageHandler enemyHealthbar;
	private ImageHandler loadingScreen;
	

	private List<Integer> projectileWaitCounters = new ArrayList<Integer>();
	private List<Integer> projectileWaitTurns    = new ArrayList<Integer>();
	private List<Integer> enemyDamageToBeTaken   = new ArrayList<Integer>();
	private List<DamageType> enemyDamageTypeToBeTaken   = new ArrayList<DamageType>();


	
	private DistanceSystem ds;
	private ScrollableList sl;
	private Point playerShotLocation;
	private Point enemyShotLocation = new Point(100,200);
	private int currentPhasePointer = 0;
	private BattlePhases currentPhase = BattlePhases.phases[currentPhasePointer];
	private int playersWeaponChoice;
	private int playersEngineChoice;
	private int enemyWeaponChoice;
	private int enemyEngineChoice;
	private Random rand;
	private boolean playerIsChaser = true;
	private boolean isPlayersTurn = playerIsChaser;// chaser goes first
	private Text phase;
	private Button graphButton;
	
	public BattleScreen(){
		handler = new BattleHandler(this);
		loadingScreen 		 = new ImageHandler(0,0,"res/loadingScreen.png",true,1,1,EntityID.UI);
		Handler.addHighPriorityEntity(loadingScreen);
		rand = new Random();
		
		
		playerShip			 = new Ship    (-200,150,0.05f,16f,"res/matron",true,EntityID.ship,50,3.5f,true);
		enemyShip 			 = new Ship    (WIDTH-430,110,0.05f,16f,"res/matron",true,EntityID.ship,50,3.5f,false);
		
		phase 				 = new Text    ("Current Phase: "+currentPhase.toString(),true,150,150);
		
		for(int i=0; i<40;i++) {
			Star starp = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true,0,Main.WIDTH/2,0,Main.HEIGHT,playerShip);
			Star stare = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true,Main.WIDTH/2,Main.WIDTH,0,Main.HEIGHT,enemyShip);
		}
		ds 					 = new DistanceSystem(500, playerShip.getDistanceToEnd(), enemyShip.getDistanceToEnd());
		overlay 			 = new ImageHandler  (0,0,"res/DrawnUI2.png",true,EntityID.UI);
		sl					 = new ScrollableList(playerShip.getCrewButtons(this), 2, 55, 100, 664,100,100,true);
		//Animation anim       = new Animation("res/octiodLazer1Anim.png", 97, 21, 4, 2,1,3,3,9, 12, 670, 347,1f,-1,true,AdjustmentID.None,Collections.<Animation>emptyList());
		ui 					 = new BattleUI(playerShip.getFrontWeapons(),this,playerShip,enemyShip);
		keyIn				 = new BattleKeyInput(this);
		mouseIn				 = new BattleMouseInput(handler);
		playerHealthbar		 = new ImageHandler  (2,2,"res/healthseg.png",true,1,1,EntityID.UI);
		enemyHealthbar		 = new ImageHandler  (797,2,"res/healthseg.png",true,1,1,EntityID.UI); 
		graphButton   		 = new Button(150, 350, 150, 150, ButtonID.Graph, true, new Graph(MathFunctions.square,150,350,150,150), this);
		graphButton.setDraggable(true);
		
		
//		for(int i =0;i<playerShip.getCrew().size();i++) {
//			Crew crew = playerShip.getCrew().get(i);
//			System.out.println(crew.getName()+":"+crew.getRaceID().toString()+" "+crew.getGender());
//			for(int j = 0;j<crew.getStats().size();j++) {
//				System.out.println(Crew.statNames[j]+": "+Byte.toString(crew.getStat(Crew.statNames[j])));
//			}
//			System.out.println();
//		}
		
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
				UseWeapon(enemyShip, playerShip, enemyWeaponChoice,   true,enemyShotLocation);
			}
			if(currentPhase == BattlePhases.Wait) {
				for(int i = 0 ;i<projectileWaitCounters.size();i++) {
					
					int projectileWaitCounter = projectileWaitCounters.get(i);
					int projectileWaitTurn = projectileWaitTurns.get(i);
					projectileWaitCounter--;
					if(projectileWaitCounter <= 0) {
						if(projectileWaitTurn == 4) {
							enemyShip.takeDamage(enemyDamageToBeTaken.get(i), enemyDamageTypeToBeTaken.get(i));
							if(i==projectileWaitCounters.size()-1) {
								projectileWaitCounters.clear();
								projectileWaitTurns.clear();
								currentPhase= BattlePhases.Final;
								nextTurn();
								break;
							}
						}
						projectileWaitCounter = weaponFireAnimation(projectileWaitTurn, playerShip, playersWeaponChoice);
						projectileWaitTurn++;
						
					}
					
					
					
					projectileWaitCounters.set(i, projectileWaitCounter);
					projectileWaitTurns.set(i, projectileWaitTurn);
				}
			}
			if(playerShip !=null && enemyShip != null) {
				phase.setText("Current Phase: "+currentPhase.toString());
				loadingScreen.setVisible(false);
				playerShip.tickLayers();
				enemyShip.tickLayers();
			}
			if(playerHealthbar != null && enemyHealthbar != null) {
				float scale = ((float)playerShip.getCurrHealth()/(float)playerShip.getMaxHealth())*1.2f;
				if (scale < 0) {scale = 0;}
				playerHealthbar.setXScale(scale);
				scale = ((float)enemyShip.getCurrHealth()/(float)enemyShip.getMaxHealth())*1.2f;
				if (scale < 0) {
					scale = 0;
					enemyShip.destruct();
					}
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
			FireableWeapon fireWeapon = (FireableWeapon) weapon;
			double[] accuracy = (double[]) damageDealt[1];
			int newX,newY,extraDmg;
			for(int i=0;i<(int)damageDealt[0];i++) {
				if(accuracy[i]!=0) {
					newX = (int) (rand.nextBoolean() ? shot.x+((int)damageDealt[2]*(1/fireWeapon.getAccuracy())):shot.x-((int)damageDealt[2]*(1/accuracy[i])));
					newY = (int) (rand.nextBoolean() ? shot.y+((int)damageDealt[2]*(1/fireWeapon.getAccuracy())):shot.y-((int)damageDealt[2]*(1/accuracy[i])));
					extraDmg = secondary.roomDamage(newX, newY);
					if(primary == playerShip) {
						enemyDamageToBeTaken.add(extraDmg+(int)damageDealt[3]);
						enemyDamageTypeToBeTaken.add((DamageType)damageDealt[4]);
						projectileWaitTurns.add(-i +1);
						projectileWaitCounters.add(0);
						System.out.println("HIT");
					}
					else {
						secondary.takeDamage(extraDmg+(int)damageDealt[3], (DamageType)damageDealt[4]);
					}
					
				}
			}
			currentPhase = BattlePhases.Wait;
			
		}
	}
	private int weaponFireAnimation(int stage,Ship primary, int position ) {
		int ticksToWait = 0;
		if(stage < 1) {
			FireableWeapon weapon = (FireableWeapon) primary.getFrontWeapon(position);
			return (int)(60*weapon.getReloadTime());
		}
		if(stage == 1) {
			Animation projectile = primary.getFrontWeapon(position).getAnimation(position);
			// change length and direction of animation based of player click
			
			// setup variables, slot position,click postion
			int slotY = primary.getSlot(0).getY();
			int slotX = primary.getSlot(0).getX();
			double shotY = playerShotLocation.getY();
			double shotX = playerShotLocation.getX();
			
			int yEnd = (int)(slotY/2 + shotY/2); // = slotY - (slotY-shotY)/2 = halfway between both
			int xEnd = (int)(slotX/2 + shotX/2); // 				^^
			
			// setup projectile mapping
			projectile.setXStart(slotX);
			projectile.setXEnd(xEnd);
			projectile.setYStart(slotY);
			projectile.setYEnd(yEnd);
			
			float yVel = projectile.getYVel();
			float xVel = projectile.getXVel();
			float xPixelsToMove = projectile.getXPixelsToMove();
			float yPixelsToMove = projectile.getYPixelsToMove();
			
			// tickToWait = max number of ticks needed;
			if(xVel == 0 && yVel > 0) {ticksToWait = (int) Math.abs(yPixelsToMove/yVel);}
			if(yVel == 0 && xVel > 0) {ticksToWait = (int) Math.abs(xPixelsToMove/xVel);}
			else {ticksToWait = (int) (Math.abs(yPixelsToMove/yVel) > Math.abs(xPixelsToMove/xVel) ?  Math.abs(yPixelsToMove/yVel): Math.abs(xPixelsToMove/xVel));}
			
			projectile.start();
		}
		if(stage == 2) {
			Animation projectile = primary.getFrontWeapon(position).getAnimation(position);
			// Delete projectile and wait a time proportional to the distance between ships
			Animation.delete(projectile);
			ticksToWait = ds.getShipDistanceCurrent()/4;
		}
		if(stage == 3) {
			Animation projectile = primary.getFrontWeapon(position).getAnimation(1);
			// change length and direction of animation based of player click
			
			// setup variables, slot position,click postion
			int slotY = primary.getSlot(0).getY();
			int slotX = primary.getSlot(0).getX();
			double shotY = playerShotLocation.getY();
			double shotX = playerShotLocation.getX();
			
			int yStart = (int)(slotY/2 + shotY/2); // = slotY - (slotY-shotY)/2 = halfway between both
			int xStart = (int)(slotX/2 + shotX/2);   // 				^^
			
			// setup projectile mapping
			projectile.setXStart(xStart);
			projectile.setXEnd((int)shotX - projectile.getTileWidth()/2);
			projectile.setYStart(yStart);
			projectile.setYEnd((int)shotY -projectile.getTileHeight()/2);

			float yVel = projectile.getYVel();
			float xVel = projectile.getXVel();
			float xPixelsToMove = projectile.getXPixelsToMove();
			float yPixelsToMove = projectile.getYPixelsToMove();
			
			//tickToWait = max number of ticks needed;
			if(xVel == 0 && yVel > 0) {ticksToWait = (int) Math.abs(yPixelsToMove/yVel);}
			if(yVel == 0 && xVel > 0) {ticksToWait = (int) Math.abs(xPixelsToMove/xVel);}
			else {ticksToWait = (int) (Math.abs(yPixelsToMove/yVel) > Math.abs(xPixelsToMove/xVel) ?  Math.abs(yPixelsToMove/yVel): Math.abs(xPixelsToMove/xVel));}
			
			projectile.start();

		}
		return ticksToWait;

	}
	@Override
	public void update(ButtonID ID,int index,int button) {// this gets notified by the click function inside button		
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
					BattleUI.changeTootlipSelection(playerShip.getCrew().get(index),TooltipSelectionID.Room);
				}
				if(ID == ButtonID.Graph){
					graphButton.getGraph().setPoint(MouseInput.mousePosition);
				}
			}
			
			if(button == MouseEvent.BUTTON3) {
				if(ID == ButtonID.Crew) {
					BattleUI.changeTootlipSelection(playerShip.getCrew().get(index),TooltipSelectionID.Stats);
				}
			}
		
	}
	
	public boolean checkShipClick(int x, int y) {
		return enemyShip.isShipClicked(x, y);
	}

	public boolean clickShip(int x, int y) {
		if(currentPhase == BattlePhases.WeaponsClick) {
			playerShotLocation = new Point(x,y);
			nextTurn();
			return true;
		}
		return false;
		
	}
	public int  getLayerClicked(int x, int y) {
		return enemyShip.getLayerClicked(x, y);
	}

}
