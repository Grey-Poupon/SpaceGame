package com.project.battle;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.project.DistanceSystem;
import com.project.EntityID;
import com.project.Graph;
import com.project.Handler;
import com.project.ImageHandler;
import com.project.Main;
import com.project.MathFunctions;
import com.project.MouseInput;
import com.project.ProjectileAnimation;
import com.project.ResourceLoader;
import com.project.ScrollableList;
import com.project.Star;
import com.project.Text;
import com.project.TooltipSelectionID;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Ship;
import com.project.ship.Slot;
import com.project.weapons.Weapon;

public class BattleScreen extends Main {

	private static final long serialVersionUID = -6523236697457665386L;

	public Ship chasedShip;

	private String selectedRoom;

	private ImageHandler overlay;
	private ImageHandler chaserHealthbar;
	private ImageHandler chasedHealthbar;
	private ImageHandler loadingScreen;
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
		ds 					 = new DistanceSystem(500, chaserShip.getDistanceToEnd(), chasedShip.getDistanceToEnd());
		overlay 			 = new ImageHandler  (0,0,"res/drawnUi2.png",true,EntityID.UI);
		sl					 = new ScrollableList(chaserShip.getCrewButtons(this), 2, 55, 100, 664,100,100,true);
		//Animation anim       = new Animation("res/octiodLazer1Anim.png", 97, 21, 4, 2,1,3,3,9, 12, 670, 347,1f,-1,true,AdjustmentID.None,Collections.<Animation>emptyList());

		ui 					 = new BattleUI(this,chaserShip,chasedShip);

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
		if(isPlayersTurn ^ playerIsChaser || currentPhase == BattlePhases.Final) { 
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
				UseWeapon(chasedShip, chaserShip, chasedWeaponChoice,chasedShotLocation);
				UseWeapon(chaserShip, chasedShip, 2                 ,chaserShotLocation);
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




	public void UseWeapon(Ship primary, Ship secondary,int position,Point shot){
		Weapon weapon = null;
		Slot slot = null;
		if(primary.isChased()) {
			 slot = primary.getBackSlot(position);
			 weapon = (Weapon) primary.getBackSlot(position).getSlotItem();
		}
		else {
			 slot = primary.getBackSlot(position);
			 weapon = (Weapon) primary.getFrontSlot(position).getSlotItem();
		}
		
		
		new ProjectileAnimation(primary, secondary, position, 200, true, weapon.fire(), shot,slot).start();
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
