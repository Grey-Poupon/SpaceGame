package com.project.battle;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

import com.project.CrewAction;
import com.project.DistanceSystem;
import com.project.EntityID;
import com.project.Handler;
import com.project.ImageHandler;
import com.project.Main;
import com.project.MouseInput;
import com.project.ProjectileAnimation;
import com.project.ResourceLoader;
import com.project.ScrollableList;
import com.project.Star;
import com.project.Text;
import com.project.TooltipSelectionID;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.engines.Engine;
import com.project.ship.Room;
import com.project.ship.Ship;
import com.project.ship.Slot;
import com.project.weapons.Weapon;

public class BattleScreen extends Main {

	private static final long serialVersionUID = -6523236697457665386L;

	public Ship chasedShip;
	public Ship chaserShip; 
	
	private String selectedRoom;

	private ImageHandler overlay;
	private ImageHandler chaserHealthbar;
	private ImageHandler chasedHealthbar;
	private ImageHandler loadingScreen;
	private DistanceSystem ds;
	private ScrollableList sl;
	private Point chaserShotLocation;
	private Point chasedShotLocation;
	private int currentPhasePointer = 0;
	private BattlePhases currentPhase = BattlePhases.phases[currentPhasePointer];
	private Weapon chaserWeaponChoice;
	private Engine chaserEngineChoice;
	private Weapon chasedWeaponChoice;
	private Engine chasedEngineChoice;
	private Random rand;
	private Text fuel;
	private boolean playerIsChaser = true;
	private boolean isPlayersTurn = playerIsChaser;// chaser goes first
	private Text phase;
	private Button graphButton;
	
	public BattleScreen(){
		handler = new BattleHandler(this);
		loadingScreen 		 = new ImageHandler(0,0,"res/loadingScreen.png",true,1,1,EntityID.UI);
		Handler.addHighPriorityEntity(loadingScreen);
		rand = new Random();
		// grab ships
		if(playerIsChaser) {
			chaserShip = ResourceLoader.getShip("defaultPlayer");
			chasedShip  = ResourceLoader.getShip("defaultEnemy");
		}
		else {
			chasedShip = ResourceLoader.getShip("defaultPlayer");
			chaserShip  = ResourceLoader.getShip("defaultEnemy");
		}
		chaserShip.setChased(false);
		chasedShip.setChased(true);

		for(int i=0; i<40;i++) {
			Star starp = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true,0,Main.WIDTH/2,0,Main.HEIGHT,chaserShip);
			Star stare = new Star(rand.nextInt(WIDTH),rand.nextInt(HEIGHT),"res/star.png",true,Main.WIDTH/2,Main.WIDTH,0,Main.HEIGHT,chasedShip);
			Handler.addLowPriorityEntity(starp);
			Handler.addLowPriorityEntity(stare);
		}
		Handler.addLowPriorityEntity(chaserShip);
		Handler.addLowPriorityEntity(chasedShip);
		// place ships
		chaserShip.setX(-200);
		chaserShip.setY(150);
		chasedShip.setX(WIDTH-430);
		chasedShip.setY(110);
		phase 				 = new Text    ("Current Phase: "+currentPhase.toString(),true,150,150);
		fuel   		 = new Text    ("Fuel: "+Integer.toString(chaserShip.getResource("fuel"))+" Power: "+Integer.toString(chaserShip.getResource("power")),true,150,180); 				
		ds 					 = new DistanceSystem(500, chaserShip.getDistanceToEnd(), chasedShip.getDistanceToEnd());
		overlay 			 = new ImageHandler  (0,0,"res/drawnUi2.png",true,EntityID.UI);
		List<Button> temp = chaserShip.getLeaderButtons(this);
	    sl  				 = new ScrollableList(temp, 0, Main.HEIGHT - (temp.size()*50), 50, (temp.size()*50),50,50,true);
		//Animation anim       = new Animation("res/octiodLazer1Anim.png", 97, 21, 4, 2,1,3,3,9, 12, 670, 347,1f,-1,true,AdjustmentID.None,Collections.<Animation>emptyList());

		ui 					 = new BattleUI(this,chaserShip,chasedShip);

		keyIn				 = new BattleKeyInput(this);
		mouseIn				 = new BattleMouseInput(handler);
		chaserHealthbar		 = new ImageHandler  (2,2,"res/healthseg.png",true,1,1,EntityID.UI);
		chasedHealthbar		 = new ImageHandler  (797,2,"res/healthseg.png",true,1,1,EntityID.UI); 
//		graphButton   		 = new Button(150, 350, 150, 150, ButtonID.Graph, true, new Graph(MathFunctions.square,0,0,150,150), this);
//		graphButton.setDraggable(true);
		Handler.addLowPriorityEntity(overlay);
		Handler.addLowPriorityEntity(chaserHealthbar);
		Handler.addLowPriorityEntity(chasedHealthbar);		
		
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
		if(isPlayersTurn != playerIsChaser || currentPhase == BattlePhases.Final) { 
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
					if(playerIsChaser) {
						chasedWeaponChoice=chasedShip.getBackWeapons().get(0);
					}
					else {
						chaserWeaponChoice=chaserShip.getBackWeapons().get(0);
					}
					System.out.println("Enemy Weapon Reveal");
				}
				else if (currentPhase == BattlePhases.WeaponsClick){
					if(playerIsChaser) {
						chasedShotLocation = new Point(350,350);
					}
					else {
						chaserShotLocation = new Point(1000,450);
					}
					System.out.println("Enemy Click (not revealed)");
				}
				else if (currentPhase == BattlePhases.Engine){
					if(playerIsChaser) {
						chasedEngineChoice=chasedShip.getEngines().get(0);
					}
					else {
						chaserEngineChoice=chaserShip.getEngines().get(0);
					}
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
				UseWeapon(chasedShip, chaserShip, chasedWeaponChoice,chasedShotLocation);
				UseWeapon(chaserShip, chasedShip, chaserWeaponChoice,chaserShotLocation);
				currentPhase = BattlePhases.Wait;
			}
			
			if(currentPhase == BattlePhases.Wait) {
				if(!ProjectileAnimation.areAnimationsRunning()) {
					currentPhase = BattlePhases.Final;
					nextTurn();
				}
			}
			
			
			if(chaserShip !=null && chasedShip != null) {
				if(currentPhase!=null&&phase!=null) {
					fuel.setText("Fuel: "+Integer.toString(chaserShip.getResource("fuel"))+" Power: "+Integer.toString(chaserShip.getResource("power")));
					phase.setText("Current Phase: "+currentPhase.toString());
				}
				loadingScreen.setVisible(false);
				chaserShip.tickLayers();
				chasedShip.tickLayers();
			}
			if(chaserHealthbar != null && chasedHealthbar != null){
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




	public void UseWeapon(Ship primary, Ship secondary,Weapon weapon,Point shot){
		
		
		
		
		new ProjectileAnimation(primary, secondary, 200, true, weapon.fire(), shot,weapon.getSlot()).start();
		

	}

	
	public void update(ButtonID ID,int index,int button) {// this gets notified by the click function inside button		
		Ship playerShip = playerIsChaser ? chaserShip : chasedShip;	
		if(button == MouseEvent.BUTTON1) {
				if(ID == ButtonID.BattleWeaponsChoice){
					
					Weapon weapon = playerShip.getFrontWeapons().get(index);
					Room room = playerShip.getWeaponRoom();
					
					BattleUI.generateActionList(weapon, room);
					
					if(isPlayersTurn && currentPhase==BattlePhases.WeaponsButton ) {
						
						chaserWeaponChoice = playerShip.getBackWeapons().get(index);
					
						System.out.println("Player is about to use weapon "+index+"!");
						nextTurn();
					}
				}
				if(ID == ButtonID.BattleEngineChoice){		
					
					Engine engine = playerShip.getEngines().get(index);
					Room room = playerShip.getGeneratorRoom();
					
					BattleUI.generateActionList(engine, room,true);
					//System.out.println("PEantu");
					if(isPlayersTurn && currentPhase==BattlePhases.Engine ) {
				
						if (playerIsChaser) {
							chaserEngineChoice = playerShip.getEngines().get(index);
						}
						else {
							chasedEngineChoice = playerShip.getEngines().get(index);
						}
						
						System.out.println("Player Engine choice made");
						nextTurn();
					}
				}
				if(ID == ButtonID.BattleEngineActionChoice) {
					if(isPlayersTurn && currentPhase==BattlePhases.EngineActions ) {
						if(playerIsChaser) {chaserEngineChoice.doAction(index,chaserShip);}
						else {chasedEngineChoice.doAction(index, chasedShip);}
						nextTurn();
					}
				}
				if(ID == ButtonID.BattleWeaponActionChoice) {
					if(isPlayersTurn && currentPhase==BattlePhases.WeaponActions ) {
						Weapon weapon = playerIsChaser ? chaserWeaponChoice:chasedWeaponChoice;
						List<CrewAction> actions = weapon.getActions();
						boolean complete = true;
						// check that the actions have been completed
						for(CrewAction action : actions) {
							if(action.getActor() == null) {
								complete = false;
							}
						}
						if (complete){
							weapon.giveXP();
							nextTurn();
						}
					}
				}
				if(ID == ButtonID.Crew){
					BattleUI.generateRoomButtons(chaserShip.getRoomLeaders().get(index),TooltipSelectionID.Room);
				}
				if(ID == ButtonID.Graph){
					graphButton.getGraph().setPoint(MouseInput.mousePosition);
				}
			}
			
			if(button == MouseEvent.BUTTON3) {
				if(ID == ButtonID.Crew) {
					BattleUI.generateRoomButtons(chaserShip.getAllCrew().get(index),TooltipSelectionID.Stats);
				}
			}
	}
	
	public boolean checkShipClick(int x, int y) {
		if (playerIsChaser) {
			return chasedShip.isShipClicked(x, y);
		}
		else {
			return chaserShip.isShipClicked(x, y);
		}
	}

	public boolean clickShip(int x, int y) {
		if(currentPhase == BattlePhases.WeaponsClick) {
			if(playerIsChaser) {
				chaserShotLocation = new Point(x,y);
			}
			else {
				chasedShotLocation = new Point(x,y);
			}
			nextTurn();
			return true;
		}
		return false;
		
	}
	public int  getLayerClicked(int x, int y) {
		if (playerIsChaser) {
			return chasedShip.getLayerClicked(x, y);
		}
		else {
			return chaserShip.getLayerClicked(x, y);
		}
	}
	public Ship getChasedShip() {
		return chasedShip;
	}

	public void setChasedShip(Ship chasedShip) {
		this.chasedShip = chasedShip;
	}

	public Ship getChaserShip() {
		return chaserShip;
	}

	public void setChaserShip(Ship chaserShip) {
		this.chaserShip = chaserShip;
	}

	public boolean isPlayersTurn() {
		return isPlayersTurn;
	}

	public void setPlayersTurn(boolean isPlayersTurn) {
		this.isPlayersTurn = isPlayersTurn;
	}

	public boolean isPlayerIsChaser() {
		return playerIsChaser;
	}

	public void setPlayerIsChaser(boolean playerIsChaser) {
		this.playerIsChaser = playerIsChaser;
	}

}
