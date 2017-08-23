package com.project.battle;


import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
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
import com.project.ProjectileInfo;
import com.project.ResourceLoader;
import com.project.ScrollableList;
import com.project.Star;
import com.project.Text;
import com.project.TooltipSelectionID;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Ship;
import com.project.weapons.Buffer;
import com.project.weapons.Weapon;
import com.project.weapons.weapon_types.FireableWeapon;

public class BattleScreen extends Main {

	private static final long serialVersionUID = -6523236697457665386L;

	public Ship chasedShip;

	private String selectedRoom;

	private ImageHandler overlay;
	private ImageHandler chaserHealthbar;
	private ImageHandler chasedHealthbar;
	private ImageHandler loadingScreen;
	
	
	private List<ProjectileInfo> projectileInfo = new ArrayList<ProjectileInfo>();

	
	private DistanceSystem ds;
	private ScrollableList sl;
	private Point chaserShotLocation;
	private Point chasedShotLocation = new Point(100,200);
	private int currentPhasePointer = 0;
	private BattlePhases currentPhase = BattlePhases.phases[currentPhasePointer];
	private int chaserWeaponChoice;
	private int chaserEngineChoice;
	private int chasedWeaponChoice;
	private int chasedEngineChoice;
	private Random rand;
	private boolean playerIsChaser = true;
	private boolean isPlayersTurn = playerIsChaser;// chased goes first
	private Text phase;
	private Button graphButton;
	
	public BattleScreen(){
		handler = new BattleHandler(this);
		loadingScreen 		 = new ImageHandler(0,0,"res/loadingScreen.png",true,1,1,EntityID.UI);
		Handler.addHighPriorityEntity(loadingScreen);
		rand = new Random();
		
		// grab ships

		chaserShip = ResourceLoader.getShip("defaultPlayer");
		chasedShip  = ResourceLoader.getShip("defaultEnemy");
		Handler.addLowPriorityEntity(chaserShip);
		Handler.addLowPriorityEntity(chasedShip);

		
		
		// place ships
		chaserShip.setX(-200);
		chaserShip.setY(150);
		chasedShip.setX(WIDTH-430);
		chasedShip.setY(110);

		
		phase 				 = new Text    ("Current Phase: "+currentPhase.toString(),true,150,150);
		
		for(int i=0; i<40;i++) {
			Star starp = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true,0,Main.WIDTH/2,0,Main.HEIGHT,chaserShip);
			Star stare = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true,Main.WIDTH/2,Main.WIDTH,0,Main.HEIGHT,chasedShip);
			Handler.addLowPriorityEntity(starp);
			Handler.addLowPriorityEntity(stare);
		}
		ds 					 = new DistanceSystem(500, chaserShip.getDistanceToEnd(), chasedShip.getDistanceToEnd());
		overlay 			 = new ImageHandler  (0,0,"res/drawnUi2.png",true,EntityID.UI);
		sl					 = new ScrollableList(chaserShip.getCrewButtons(this), 2, 55, 100, 664,100,100,true);
		//Animation anim       = new Animation("res/octiodLazer1Anim.png", 97, 21, 4, 2,1,3,3,9, 12, 670, 347,1f,-1,true,AdjustmentID.None,Collections.<Animation>emptyList());
		ui 					 = new BattleUI(chaserShip.getFrontWeapons(),this,chaserShip,chasedShip);
		keyIn				 = new BattleKeyInput(this);
		mouseIn				 = new BattleMouseInput(handler);
		chaserHealthbar		 = new ImageHandler  (2,2,"res/healthseg.png",true,1,1,EntityID.UI);
		chasedHealthbar		 = new ImageHandler  (797,2,"res/healthseg.png",true,1,1,EntityID.UI); 
		graphButton   		 = new Button(150, 350, 150, 150, ButtonID.Graph, true, new Graph(MathFunctions.square,150,350,150,150), this);
		graphButton.setDraggable(true);
		
		Handler.addLowPriorityEntity(overlay);
		Handler.addLowPriorityEntity(chaserHealthbar);
		Handler.addLowPriorityEntity(chasedHealthbar);
		
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
		// if its the chased's turn, next phase
		if(isPlayersTurn ^ playerIsChaser || currentPhase == BattlePhases.Final ) { 
			currentPhasePointer++;
		}
		// loop phases if necessary
		if(currentPhasePointer  >= BattlePhases.phases.length) {
			currentPhasePointer -= BattlePhases.phases.length;
			// turn = chaser, which gets flipped l8r
			isPlayersTurn = playerIsChaser;
		}
		// set phase ,next turn
		currentPhase= BattlePhases.phases[currentPhasePointer];
		isPlayersTurn = !isPlayersTurn;
		
		// Turn Textual output
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
			
			// AI turns
			if(!isPlayersTurn) {
				if(currentPhase == BattlePhases.WeaponsButton) {
					chasedWeaponChoice=0;
					System.out.println("Enemy Weapon Reveal");
				}
				else if (currentPhase == BattlePhases.WeaponsClick){
					chasedShotLocation = new Point(350,450);
					System.out.println("Enemy Click (not revealed)");
				}
				else if (currentPhase == BattlePhases.Engine){
					chasedEngineChoice=0;
					System.out.println("Enemy Engine Reveal");
				}	
				nextTurn();
			}
			
			// Final phase aka do stuff
			if(currentPhase == BattlePhases.Final) {
				System.out.println("Weapons Firing");
				chaserShip.setSpeed(100);
				chasedShip.setSpeed(200);
				ds.calculateDistances(chaserShip, chasedShip);
				UseWeapon(chasedShip, chaserShip, chasedWeaponChoice, true,chasedShotLocation);
				UseWeapon(chaserShip, chasedShip, chaserWeaponChoice, true,chaserShotLocation);
			}
			// wait for animations
			if(currentPhase == BattlePhases.Wait) {
				for(int i = 0 ;i<projectileInfo.size();i++) {
					// counter = ticks before next Animation turn
					int projectileWaitCounter = projectileInfo.get(i).getWaitCounter();
					// turn = which Animation set: Prefired,fired,inbetween ships, hitting ship
					int projectileWaitTurn = projectileInfo.get(i).getTurn();
					// tick counter
					projectileWaitCounter--;

					// next animation group
					if(projectileWaitCounter <= 0) {
						// if final animation group,do damage
						if(projectileWaitTurn == 4) {
							if(projectileInfo.get(i).getIsChaser()) {
								chasedShip.takeDamage(projectileInfo.get(i).getDamage(), projectileInfo.get(i).getDamageType());
							}
							else {
								chaserShip.takeDamage(projectileInfo.get(i).getDamage(), projectileInfo.get(i).getDamageType());
							}
							// if Animations complete, next turn
							if(i==projectileInfo.size()-1) {
								projectileInfo.clear();
								currentPhase = BattlePhases.Final;
								nextTurn();
								break;
							}
						}
						// reset counter, next turn
						Ship ship = projectileInfo.get(i).getIsChaser() ? chaserShip : chasedShip;
						projectileWaitCounter = weaponFireAnimation(projectileWaitTurn, ship, chaserWeaponChoice);
						projectileWaitTurn++;
					}					
					// update counters
					projectileInfo.get(i).setWaitCounter(projectileWaitCounter);
					projectileInfo.get(i).setTurn(projectileWaitTurn);
				}
			}
			if(chaserShip !=null && chasedShip != null) {
				if(currentPhase!=null&&phase!=null) {
					phase.setText("Current Phase: "+currentPhase.toString());
				}
				loadingScreen.setVisible(false);
				chaserShip.tickLayers();
				chasedShip.tickLayers();
			}
			if(chaserHealthbar != null && chasedHealthbar != null) {
				float scale = ((float)chaserShip.getCurrHealth()/(float)chaserShip.getMaxHealth())*1.2f;
				if (scale < 0) {scale = 0;}
				chaserHealthbar.setXScale(scale);
				scale = ((float)chasedShip.getCurrHealth()/(float)chasedShip.getMaxHealth())*1.2f;
				if (scale < 0) {
					scale = 0;
					chasedShip.destruct();
					}
				chasedHealthbar.setXScale(scale);
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
					if(primary == chaserShip) {
						projectileInfo.add(new ProjectileInfo(0, -i+1, extraDmg+(int)damageDealt[3], (DamageType)damageDealt[4],true));
						System.out.println("Chaser Weapon Fire");
					}
					else {
						projectileInfo.add(new ProjectileInfo(0,-i+1, extraDmg+(int)damageDealt[3], (DamageType)damageDealt[4],false));
						System.out.println("Chased Weapon Fire");
					}
					
				}
			}
			
		}
		
//			//put in above for loop so to apply buff on each successful hit
//			if(weapon.getEffects().size()>1) {
//				if(weapon.isTargetSelf()) {
//					for(int i = 0; i<weapon.getEffects().size();i++) {
//						((Buffer) weapon.getEffects().get(i)).applyBuff(primary,shot.x,shot.y);
//					}
//				}else {
//					for(int i = 0; i<weapon.getEffects().size();i++) {
//						((Buffer) weapon.getEffects().get(i)).applyBuff(secondary,shot.x,shot.y);
//					}
//				}
//			}
			
			currentPhase = BattlePhases.Wait;
	}
	
	private int weaponFireAnimation(int stage,Ship primary, int position ) {
		int ticksToWait = 0;
		// preFiredStage
		if(stage < 1) {
			FireableWeapon weapon = (FireableWeapon) primary.getFrontWeapon(position);
			return (int)(500*weapon.getReloadTime()*(Math.abs(position)));
		}
		// Firing stage
		if(stage == 1) {
			Animation projectile = primary.getFrontWeapon(position).getAnimation(position);
			// change length and direction of animation based of player click
			
			// setup variables, slot position,click position
			int slotY = primary.getSlot(0).getY();
			int slotX = primary.getSlot(0).getX();
			double shotY;
			double shotX;
			if(primary == chaserShip) {
				slotY = primary.getSlot(3).getY();
				slotX = primary.getSlot(3).getX();
				shotY = chaserShotLocation.getY();
				shotX = chaserShotLocation.getX();
				projectile.setMask(new Rectangle2D.Double(0, 0, Main.WIDTH/2, Main.HEIGHT)); 

			}
			else {
				slotY = primary.getSlot(1).getY();
				slotX = primary.getSlot(1).getX();
				shotY = chasedShotLocation.getY();
				shotX = chasedShotLocation.getX();
				projectile.setMask(new Rectangle2D.Double(Main.WIDTH/2, 0,Main.WIDTH/2, Main.HEIGHT));

			}
			int yEnd = (int)(slotY/2 + shotY/2); // = slotY - (slotY-shotY)/2 = halfway between both
			int xEnd = (int)(slotX/2 + shotX/2); // 				^^
			int tileWidth = projectile.getTileWidth();

			// setup projectile mapping
			projectile.setXStart(slotX);
			projectile.setXEnd(xEnd);
			projectile.setYStart(slotY);
			projectile.setYEnd(yEnd);

			
			float yVel = projectile.getYVel();
			float xVel = projectile.getXVel();			

//			while(xEnd < Main.WIDTH/2 + tileWidth) {
//				xEnd+=xVel;
//				yEnd+=yVel;
//			}
//			projectile.setXEnd(xEnd);
//			projectile.setYEnd(yEnd);

			
			float xPixelsToMove = projectile.getXPixelsToMove();
			float yPixelsToMove = projectile.getYPixelsToMove();
			
			// tickToWait = max number of ticks needed;
			if(xVel == 0 && yVel > 0) {ticksToWait = (int) Math.abs(yPixelsToMove/yVel);}
			if(yVel == 0 && xVel > 0) {ticksToWait = (int) Math.abs(xPixelsToMove/xVel);}
			else {ticksToWait = (int) (Math.abs(yPixelsToMove/yVel) > Math.abs(xPixelsToMove/xVel) ?  Math.abs(yPixelsToMove/yVel): Math.abs(xPixelsToMove/xVel));}
			
			projectile.start();
		}
		// Inbetween stage
		if(stage == 2) {
			Animation projectile = primary.getFrontWeapon(position).getAnimation(position);
			// Delete projectile and wait a time proportional to the distance between ships
			Animation.delete(projectile);
			ticksToWait = ds.getShipDistanceCurrent()/4;
		}
		// Hitting stage
		if(stage == 3) {
			Animation projectile = primary.getFrontWeapon(position).getAnimation(1);
			// change length and direction of animation based of player click
			
			// setup variables, slot position,click postion
			int slotY = primary.getSlot(0).getY();
			int slotX = primary.getSlot(0).getX();
			//int slotY = 350;
			//int slotX = 250;
			double shotY;
			double shotX;
			int xEnd;
			int yEnd;
			if(primary == chaserShip) {
				slotY = primary.getSlot(3).getY();
				slotX = primary.getSlot(3).getX();
				shotY = chaserShotLocation.getY();
				shotX = chaserShotLocation.getX();
				yEnd  = (int)shotY - projectile.getTileHeight()/2;
				xEnd  = (int)shotX - projectile.getTileWidth()/2;
				projectile.setMask(new Rectangle2D.Double(Main.WIDTH/2, 0, Main.WIDTH/2, Main.HEIGHT)); 

			}
			else {
				slotY = primary.getSlot(1).getY();
				slotX = primary.getSlot(1).getX();
				shotY = chasedShotLocation.getY();
				shotX = chasedShotLocation.getX();
				yEnd  = (int)shotY + projectile.getTileHeight()/2;
				xEnd  = (int)shotX + projectile.getTileWidth()/2;
				projectile.setMask(new Rectangle2D.Double(0, 0,Main.WIDTH/2, Main.HEIGHT));
				//projectile.setMask(new Rectangle2D.Double(0, 0, Main.WIDTH, Main.HEIGHT)); 
				}
			
			int yStart = (int)(slotY/2 + shotY/2); // = slotY - (slotY-shotY)/2 = halfway between both
			int xStart = (int)(slotX/2 + shotX/2); // 				^^
			int tileWidth = projectile.getTileWidth();
			
			
			// setup projectile mapping
			projectile.setXStart(xStart);
			projectile.setXEnd(xEnd);
			projectile.setYStart(yStart);
			projectile.setYEnd(yEnd);

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
						chaserWeaponChoice = index;
						System.out.println("Player is about to use weapon"+chaserWeaponChoice+"!");
						nextTurn();
					}
				}
		
				if(ID == ButtonID.BattleEngineChoice){
					if(isPlayersTurn && currentPhase==BattlePhases.Engine ) {
						chaserEngineChoice = index;
						System.out.println("Player Engine choice made");
						nextTurn();
					}
				}
				if(ID == ButtonID.Crew){
					BattleUI.changeTootlipSelection(chaserShip.getCrew().get(index),TooltipSelectionID.Room);
				}
				if(ID == ButtonID.Graph){
					graphButton.getGraph().setPoint(MouseInput.mousePosition);
				}
			}
			
			if(button == MouseEvent.BUTTON3) {
				if(ID == ButtonID.Crew) {
					BattleUI.changeTootlipSelection(chaserShip.getCrew().get(index),TooltipSelectionID.Stats);
				}
			}
		
	}
	
	public boolean checkShipClick(int x, int y) {
		return chasedShip.isShipClicked(x, y);
	}

	public boolean clickShip(int x, int y) {
		if(currentPhase == BattlePhases.WeaponsClick) {
			chaserShotLocation = new Point(x,y);
			nextTurn();
			return true;
		}
		return false;
		
	}
	public int  getLayerClicked(int x, int y) {
		return chasedShip.getLayerClicked(x, y);
	}

}
