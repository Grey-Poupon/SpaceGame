package com.project.battle;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import com.project.Actionable;
import com.project.Crew;
import com.project.CrewAction;
import com.project.CrewActionID;
import com.project.DistanceSystem;
import com.project.EntityID;
import com.project.Handler;
import com.project.ImageHandler;
import com.project.KeyInput;
import com.project.Main;
import com.project.MouseInput;
import com.project.Phase;
import com.project.Player;
import com.project.ProjectileAnimation;
import com.project.ProjectileInfo;
import com.project.ResourceLoader;
import com.project.ScrollableList;
import com.project.Star;
import com.project.Text;
import com.project.TooltipSelectionID;
import com.project.WeaponShipInterface;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.phase2.Phase2;
import com.project.ship.Ship;
import com.project.ship.rooms.GeneratorRoom;
import com.project.ship.rooms.WeaponsRoom;
import com.project.slider.SliderID;
import com.project.slider.VerticalSliderHandle;
import com.project.thrusters.Thruster;
import com.project.weapons.Weapon;

public class BattleScreen implements Phase, Observer {

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
	public Main main;
	private ArrayList<Point> chaserShotLocations = new ArrayList<Point>();
	private ArrayList<Point> chasedShotLocations = new ArrayList<Point>();
	private int currentPhasePointer = 0;
	private List<Weapon> chaserWeaponChoice = new ArrayList<Weapon>();
	private List<Weapon> chasedWeaponChoice = new ArrayList<Weapon>();
	private Thruster chaserThrusterChoice;
	private Thruster chasedThrusterChoice;
	private int chasedSpeedChoice;
	private int chaserSpeedChoice;
	private Random rand;
	private boolean playerIsChaser;
	private BattleTurns turn = BattleTurns.PlayerTurn;
	
	private Button graphButton;
	private Player player;
	private int weaponClicksNeeded=0;
	private int currentWeaponClick = 1;
	private Ship playerShip;
	private Ship enemyShip;
	private ImageHandler newUI;
	private ImageHandler uiGraphBox;
	private ImageHandler weaponsTab;
	private ImageHandler enginesTab;
	private ImageHandler movementTab;
	public static BattleHandler handler; 

	private BattleMouseInput mouseIn;
	private BattleKeyInput keyIn;
	private WeaponShipInterface inter;

	
	public BattleScreen(Main main,Ship chaser, Ship chasee,boolean playerIsChaser) {
		
		this.main = main;
		this.handler.setBs(this);
		this.playerIsChaser = playerIsChaser;

		player = main.player;
		main.setPaused(true);

		loadingScreen = new ImageHandler(0, 0, "res/loadingScreen.png", true, 1, 1, EntityID.UI);
		handler.addHighPriorityEntity(loadingScreen);
		rand = new Random();
		
		// grab ships
		if (playerIsChaser) {
			chaserShip = chaser;
			playerShip = chaserShip;
			chasedShip = chasee;
			enemyShip = chasedShip;
			chaserShip.setPlayer(true);
		}else {
			chasedShip = chasee;
			chasedShip.setPlayer(true);
			chaserShip = chaser;
			playerShip = chasedShip;
			enemyShip = chaserShip;
		}
		chaserShip.setChased(false);
		chasedShip.setChased(true);
		chaserShip.setBs(this);
		chasedShip.setBs(this);

		
		// generate start
		for (int i = 0; i < 40; i++) {
			Star starp = new Star(rand.nextInt(main.WIDTH), rand.nextInt(main.HEIGHT/2), "res/star.png", true, 50, Main.WIDTH / 2-50, 50,
					446, chaserShip);
			Star stare = new Star(rand.nextInt(main.WIDTH), rand.nextInt(main.HEIGHT), "res/star.png", true,50+Main.WIDTH / 2,
					Main.WIDTH-50, 50, 446, chasedShip);
			handler.addLowPriorityEntity(starp);
			handler.addLowPriorityEntity(stare);
		}
		handler.addLowPriorityEntity(chaserShip);
		handler.addLowPriorityEntity(chasedShip);
		
		//Place ships
		chaserShip.setX(-150);
		chaserShip.setY(-50);
		chasedShip.setX(main.WIDTH/2-50);
		chasedShip.setY(-50);
		ds = new DistanceSystem(3, 9, 2, 1);
		handler.addHighPriorityEntity(ds);
		
		// set WeaponShipInterface
		this.inter = new WeaponShipInterface(chaserShip, chasedShip);
		
		//Set Captain
		chaserShip.setCaptain(player.getPlayerCrew());
		
		//Set Room Leader Tabs
		main.ui      = new BattleUI(this, chaserShip, chasedShip);
		keyIn   = new BattleKeyInput(this);
		mouseIn = new BattleMouseInput(handler);
		
		//Health bars
		chaserHealthbar = new ImageHandler(2, 2, "res/healthseg.png", true, 1, 1, EntityID.UI);
		chasedHealthbar = new ImageHandler(797, 2, "res/healthseg.png", true, 1, 1, EntityID.UI);
		
		//Biggest UI element
		newUI = new ImageHandler(0,0,"res/ui/ui.png",true,1,1,EntityID.UI);
		handler.addLowPriorityEntity(newUI);
		
		//Add Tabs
		enginesTab = new ImageHandler(0, Main.HEIGHT-85*3,ResourceLoader.getImage("res/ui/enginesTab.png"),true,EntityID.UI);
		enginesTab.start(handler,false);
		enginesTab.setVisible(true);
		weaponsTab = new ImageHandler(0, Main.HEIGHT-85*3,ResourceLoader.getImage("res/ui/weaponsTab.png"),true,EntityID.UI);
		weaponsTab.start(handler,false);
		weaponsTab.setVisible(false);
		movementTab = new ImageHandler(0, Main.HEIGHT-85*3,ResourceLoader.getImage("res/ui/movementTab.png"),true,EntityID.UI);
		movementTab.start(handler,false);
		movementTab.setVisible(false);
		
		Button graph = new Button(0, 0, playerShip.getGenerator().getEfficiencyGraph().getWidth(),playerShip.getGenerator().getEfficiencyGraph().getHeight(), ButtonID.BattleThrusterGraph, true,
				
		playerShip.getGenerator().getEfficiencyGraph(), this);
		graph.setDraggable(true);
		List<Button> graphEnd = new ArrayList<Button>();
		graphEnd.add(graph);
		
		// GO BUTTON
		Button go = new Button(1165, 605, 100,100, ButtonID.EndPhase, graphEnd.size(), true, "GO",
				"sevensegies", Font.PLAIN, 30, Color.WHITE,
				new ImageHandler(0, 0, "res/appIcon.png", true, EntityID.UI), this);
		
		handler.addLowPriorityEntity(go);
		
		BattleUI.graphList = new ScrollableList(graphEnd, BattleUI.graphMonitorXOffset, BattleUI.graphMonitorYOffset,
				playerShip.getGenerator().getEfficiencyGraph().getWidth(),
				2 * playerShip.getGenerator().getEfficiencyGraph().getHeight());
		
		//Graph Box Element
		uiGraphBox = new ImageHandler(1064,370,"res/ui/graphBox.png",true,1,1,EntityID.UI);
		handler.addHighPriorityEntity(uiGraphBox);
		List<Button> temp = chaserShip.getPhaseLeaderButtons(handler,this);
		sl = new ScrollableList(temp, 6, Main.HEIGHT - (temp.size() * 85)+6, 85, (temp.size() * 85), 85, 85, true);
		
		//Handler.addLowPriorityEntity(overlay);
		handler.addLowPriorityEntity(chaserHealthbar);
		handler.addLowPriorityEntity(chasedHealthbar);

		BattleUI.generateRoomButtons(playerShip.getPhaseLeaders().get(0), TooltipSelectionID.Room);
		main.setPaused(false);
	}


	private void nextTurn() {
		if(turn == BattleTurns.PlayerTurn){
			turn = BattleTurns.EnemyTurn;
		}
		else if(turn == BattleTurns.EnemyTurn){
			turn = BattleTurns.Aim;
		}
		else if(turn == BattleTurns.Aim){
			turn = BattleTurns.Reveal;
		}
		else if(turn == BattleTurns.Reveal){
			turn = BattleTurns.PlayerTurn;
		}

	}

	public void tick() {
		handler.tick(main.ui);
		if (!main.isPaused()) {
			
			// AI turns
			if(turn == BattleTurns.EnemyTurn) {
				// weapon selection
				System.out.println("Enemy Weapon Choice");
					if(playerIsChaser) {
						List<Weapon> weapons = chasedShip.getBackWeapons();
						if(weapons.size() > 0){
							chasedWeaponChoice.add(weapons.get(0));
						}
						else{
							System.out.println("Error - 001");
						}
					}
					else {
						List<Weapon> weapons = chaserShip.getBackWeapons();
						if(weapons.size() > 0){
							chaserWeaponChoice.add(weapons.get(0));
						}	
						else{
							System.out.println("Error - 002");
						}
					}
					
					// weapon aiming
					//Need to add some method to make the enemy fire more than once... 
					if (playerIsChaser) {
						chasedShotLocations.add(new Point(350, 250)) ;
					} else {
						chaserShotLocations.add(new Point(1000, 450));
					}
					System.out.println("Enemy Click");
					if (playerIsChaser) {
						chasedSpeedChoice = 100;
						enemyShip.setEndSpeed(chasedSpeedChoice);
					}
				
				nextTurn();
			}
			if(turn == BattleTurns.Aim){
				if(weaponClicksNeeded==0){
					turn = BattleTurns.Reveal;
				}
				else{
					loadAimingMouseIcon();
				}

			}
			if (turn == BattleTurns.Reveal){	
				moveCrew();
				BattleUI.refreshRoomUI();
				
				checkEnd();
				
				chaserShip.generate();
				chasedShip.generate();
				chaserShip.accelerate();
				chasedShip.accelerate();
				
				System.out.println("Chaser Speed: "+chaserSpeedChoice+"\nChased Speed: "+chasedSpeedChoice);
				ds.changeSpeed(1, 0);
				ds.moveShips();
								
				/**add fired weapons to simulation **/
				List<List<ProjectileInfo>> chaserWeaponProj = inter.addCreateProj(chasedShip, chaserShip, chaserWeaponChoice, chaserShotLocations);
			//	List<List<ProjectileInfo>> chasedWeaponProj = inter.addCreateProj(chaserShip, chasedShip, chasedWeaponChoice, chasedShotLocations);

				/**outbound projectiles Animation**/
			//	useWeapon(chasedShip, chasedWeaponProj);
				useWeapon(chaserShip, chaserWeaponProj);
				
				/**inbound projectiles Animation**/
				nearbyProjectileAnimation(chasedShip, chaserShip,inter.simulate());
				
				chasedWeaponChoice.clear();
				chaserWeaponChoice.clear();
				chaserShotLocations.clear();
				chasedShotLocations.clear();
				weaponClicksNeeded = 0;				
				
				/**Reset Generators**/
				getPlayerShip().getGenerator().setCanGenerate(false);
				getEnemyShip() .getGenerator().setCanGenerate(false);
				
				/*Do Animations*/
				if (!ProjectileAnimation.areAnimationsRunning()) {
					chaserShip.getGenerator().getEfficiencyGraph().setGraphPoint(0);
					chasedShip.getGenerator().getEfficiencyGraph().setGraphPoint(0);
				}
				
				nextTurn();

		
		}
			
		/*kill loading screen*/
		if (chaserShip != null && chasedShip != null) {
			loadingScreen.setVisible(false);
			chaserShip.tickLayers();
			chasedShip.tickLayers();
		}
		/**Update Healthbars**/
		if (chaserHealthbar != null && chasedHealthbar != null) {
			
			float scale = ((float) chaserShip.getCurrHealth() / (float) chaserShip.getMaxHealth()) * 1.2f;
			if (scale < 0) {
				scale = 0;
			}
			chaserHealthbar.setXScale(scale);
			
			scale = ((float) chasedShip.getCurrHealth() / (float) chasedShip.getMaxHealth()) * 1.2f;
			if (scale < 0) {
				scale = 0;
				chasedShip.destruct();
			}
			chasedHealthbar.setXScale(scale);
		}
		
	}
}

	private void nearbyProjectileAnimation(Ship chased, Ship chaser, List<List<ProjectileInfo>> projectiles) {
		if(projectiles.get(0).size()>0){
			new ProjectileAnimation(chaser, projectiles.get(0),false);
		}
		if(projectiles.get(1).size()>0){
			new ProjectileAnimation(chased, projectiles.get(1),false);
		}
	}

	public void useWeapon(Ship primary,List<List<ProjectileInfo>> weaponsProj){
		
		for(int i = 0;i<weaponsProj.size();i++){
			new ProjectileAnimation(primary,weaponsProj.get(i),true);
		}
	}
	
	public void checkEnd() {
		if(enemyShip.getGeneratorRoom().getHealth() <= 0 || playerShip.getGeneratorRoom().getHealth() <= 0) {
			endCombat();
		}
	}
	
	public void endCombat() {
		System.out.println("Someone won!");
		main.setPhase(Phase2.getP());
	}
	

	public void emptyTurnWarning(){
		System.out.println("You've not selected any actions this turn, thats inadvisable");
	}

	public void update(ButtonID ID,int index,int button) {// this gets notified by the click function inside button		
		
		if(button == MouseEvent.BUTTON1) {

			if(ID == ButtonID.Back) {
				BattleUI.back();
			}
			if(ID == ButtonID.Manoeuvres) {
				//BattleUI.generateManoeuvreActionList((Cockpit)playerShip.getCockpit());
			}
			if(ID == ButtonID.SpeedInput) {
				//BattleUI.generateSpeedInput();
			}
			if (ID == ButtonID.Crew) {
				movementTab.setVisible(false);
				weaponsTab.setVisible(false);
				enginesTab.setVisible(false);
				if(playerShip.getPhaseLeaders().get(index).getRoomLeading() instanceof WeaponsRoom) { weaponsTab.setVisible(true);}
				else if(playerShip.getPhaseLeaders().get(index).getRoomLeading() instanceof GeneratorRoom) { enginesTab.setVisible(true);}
				else if(playerShip.getPhaseLeaders().get(index).isCaptain()) {movementTab.setVisible(true);}
				BattleUI.generateRoomButtons(playerShip.getPhaseLeaders().get(index), TooltipSelectionID.Room);
				
			}
			if(ID == ButtonID.EndPhase) {
								
					
				//Do actions for generator phase
				//start sensor
				if(playerShip.getSensor()!=null) {
					enemyShip.generateSensorSpheres(playerShip.getSensor());
					enemyShip.setBeingSensed(true);
				}

				if(playerIsChaser) {

					if(BattleUI.speedInput!=null) {
						chaserSpeedChoice = BattleUI.speedInput.getGraph().getSpeed();playerShip.setEndSpeed(chaserSpeedChoice);
					}else {
						chaserSpeedChoice=0;}
					
				}else{
					chasedSpeedChoice = BattleUI.speedInput.getGraph().getSpeed();}
				
				doActions(playerShip,playerShip.getGenerator());

				// fire weapons
				// intalise variables
				List<Weapon> weapons = playerIsChaser ? playerShip.getFrontWeapons():playerShip.getBackWeapons();
				for(int i = 0;i<weapons.size();i++) {
					doActions(playerShip,weapons.get(i));
				}
							
				// next turn
				nextTurn();
				
			
			}	
		}
	}
	
	public void doActions(Ship ship,Actionable actionable) {
		
		// intalise variables
		List<CrewAction> actions  = new ArrayList<CrewAction>();;
		List<CrewAction> refinedActions;
		CrewAction action;
		List<CrewAction> actionsNeeded;
		HashMap<CrewActionID,List<CrewAction>> actionMap;
		// check which actions have been completed
		
			// initalise variables
			refinedActions = new ArrayList<CrewAction>();
			actionMap = new HashMap<>();
			actions = actionable.getActions();
		
			// setup HashMaps
			for(int j = 0; j<actions.size(); j++) {
				actionsNeeded = actions.get(j).getActionsNeeded();
				
				// map the CrewActions(Boxes) to the actions they need to be completed
				for(int k = 0; k < actionsNeeded.size();k++) {
					if(!actionMap.containsKey(actionsNeeded.get(k))) {
						List<CrewAction> aa = new ArrayList<CrewAction>();
						aa.add(actions.get(j));
						actionMap.put(actionsNeeded.get(k).getActionType(), aa);
					}
					else {
						actionMap.get(actionsNeeded.get(k)).add(actions.get(j));
					}
				}
				
				// add the actions that have an actor in them to the refined list
				if(actions.get(j).getActor()!=null) {
					refinedActions.add(actions.get(j));
				}
			}
			
			if(refinedActions.size()>0){
				// sort actions based on the amount of actions needed
				Collections.sort(refinedActions);
				for(int j = 0; j<refinedActions.size(); j++) {
					action = refinedActions.get(j);
					
					// if this actions doesn't need anymore actions to be completed
					if(refinedActions.get(j).getActionsNeeded().size()==0) {
						actions = actionMap.get(action.getActionType());
						
						//remove it from every other actionNeeded
						if(actions!=null) {
							for(int k = 0;k<actions.size();k++) {
								actions.get(k).removeActionNeeded(action);
							}
						}									
						// do action
						if(action.isBroken()){
							action.setBroken(false);
						}							
						actionable.doAction(action.getActor(),action,ship, this);
						action.resetActions();
						//action.removeActor();
					}
				}
			}
			else{
				emptyTurnWarning();
			}	
	}
	
	public void addPlayerChoice(Weapon weapon) {
		weaponClicksNeeded++;
		if(playerIsChaser){
			addChaserWeaponChoice(weapon);
		}
		else{
			addChasedWeaponChoice(weapon);
		}
	}

	public boolean checkShipClick(int x, int y) {
		if (playerIsChaser) {
			return chasedShip.isShipClicked(x, y);
		} else {
			return chaserShip.isShipClicked(x, y);
		}
	}

	public boolean clickShip(int x, int y) {
		if (turn == BattleTurns.Aim) {
			ArrayList<Point> shotLocations; 
			if (playerIsChaser) {
				chaserShotLocations.add(new Point(x, y)) ;
				shotLocations = chaserShotLocations;
			} else {
				chasedShotLocations.add(new Point(x,y));
				shotLocations = chasedShotLocations;
			}
			
			if(shotLocations.size()== weaponClicksNeeded) {
				nextTurn();
				currentWeaponClick = 0;
				/**Should reset mouse icon**/
				loadAimingMouseIcon();
			}
			else{
				/**To keep track of which weapon click the player is on**/
				currentWeaponClick++;
				
				/**next mouse icon**/
				loadAimingMouseIcon();
			}
			return true;
		}
		return false;
	}

	/**Uses the global variable currentWeaponClick**/
	private void loadAimingMouseIcon() {		
		/**Reset mouse icon**/
		if (currentWeaponClick == 0){
			Handler.changeMouseIcon("res/mousePointer.png");   
			
			/**reset the current mouse click to 1**/
			currentWeaponClick = 1;
			
		}
		/**change to aiming icon**/
		else{
			int i = currentWeaponClick;
			if(i>3){i=i%3+1;}
			Handler.changeMouseIcon("res/mouseAimingIcon"+i+".png");
		}
		
	}
	
	public int getLayerClicked(int x, int y) {
		if (playerIsChaser) {
			return chasedShip.getLayerClicked(x, y);
		} else {
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
		return turn == BattleTurns.PlayerTurn;
	}

	public void setPlayersTurn(BattleTurns turn) {
		this.turn = turn;
	}

	public boolean playerIsChaser() {
		return playerIsChaser;
	}

	public void setPlayerIsChaser(boolean playerIsChaser) {
		this.playerIsChaser = playerIsChaser;
	}

	public void addChaserWeaponChoice(Weapon weapon) {
		chaserWeaponChoice.add(weapon);
		
	}
	
	public void addChasedWeaponChoice(Weapon weapon) {
		chasedWeaponChoice.add(weapon);
		
	}

	public Ship getPlayerShip() {
		if(playerIsChaser){return chaserShip;}
		return chasedShip;
	}

	public Ship getEnemyShip() {
		if(playerIsChaser){return chasedShip;}
		return chaserShip;
	}	
	
	public void moveCrew(){
		List<Crew> crewList = playerShip.getAllCrew();

		for(int i = 0;i<crewList.size();i++){
			Crew crew = crewList.get(i);
			if(crew.isMoving()){
				crew.getRoomMovingFrom().removeCrew(crew);
				crew.getRoomMovingTo().addCrew(crew);
				crew.setMoving(false);
			}				
		}
	}

	public void render(Graphics g) {
		handler.render(g);
	}

	public MouseInput getMouseInput() {
		// TODO Auto-generated method stub
		return mouseIn;
	}

	public KeyInput getKeyInput() {
		// TODO Auto-generated method stub
		return keyIn;
	}
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof SliderID){
			SliderID id = (SliderID) arg1;
			if (id == SliderID.speed){
				// set speed choice
				if(playerIsChaser){chaserSpeedChoice = ((VerticalSliderHandle)arg0 ).getStep();}
				else{chasedSpeedChoice = ((VerticalSliderHandle)arg0 ).getStep();}
			}
		}
		

	}

	@Override
	public void addListeners(Main main) {
		main.addKeyListener(keyIn);
		main.addMouseListener(mouseIn);
		main.addMouseMotionListener(mouseIn);
		main.addMouseWheelListener(mouseIn);
		
	}
}
