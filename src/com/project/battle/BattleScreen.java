package com.project.battle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.project.CrewAction;
import com.project.CrewActionID;
import com.project.DistanceSystem;
import com.project.EntityID;
import com.project.Handler;
import com.project.ImageHandler;
import com.project.Main;
import com.project.MouseInput;
import com.project.Player;
import com.project.ProjectileAnimation;
import com.project.ResourceLoader;
import com.project.ScrollableList;
import com.project.Star;
import com.project.Text;
import com.project.TooltipSelectionID;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.Room;
import com.project.ship.Ship;
import com.project.thrusters.Thruster;
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
	private List<Weapon> chaserWeaponChoice = new ArrayList<Weapon>();
	private List<Weapon> chasedWeaponChoice = new ArrayList<Weapon>();
	private Thruster chaserThrusterChoice;
	private Thruster chasedThrusterChoice;
	private int chasedSpeedChoice;
	private int chaserSpeedChoice;
	private Random rand;
	private boolean playerIsChaser = true;
	private boolean isPlayersTurn = playerIsChaser;// chaser goes first
	private Text phase;
	private Button graphButton;
	private Player player;
	public BattleScreen() {
		player = new Player(100);
		handler = new BattleHandler(this);
		loadingScreen = new ImageHandler(0, 0, "res/loadingScreen.png", true, 1, 1, EntityID.UI);
		Handler.addHighPriorityEntity(loadingScreen);
		rand = new Random();
		// grab ships
		if (playerIsChaser) {
			chaserShip = ResourceLoader.getShip("defaultPlayer");
			chasedShip = ResourceLoader.getShip("defaultEnemy");
			chaserShip.setPlayer(true);
		} else {
			chasedShip = ResourceLoader.getShip("defaultPlayer");
			chasedShip.setPlayer(true);
			chaserShip = ResourceLoader.getShip("defaultEnemy");
		}
		chaserShip.setChased(false);
		chasedShip.setChased(true);

		for (int i = 0; i < 40; i++) {
			Star starp = new Star(rand.nextInt(WIDTH), rand.nextInt(HEIGHT), "res/star.png", true, 0, Main.WIDTH / 2, 0,
					Main.HEIGHT, chaserShip);
			Star stare = new Star(rand.nextInt(WIDTH), rand.nextInt(HEIGHT), "res/star.png", true, Main.WIDTH / 2,
					Main.WIDTH, 0, Main.HEIGHT, chasedShip);
			Handler.addLowPriorityEntity(starp);
			Handler.addLowPriorityEntity(stare);
		}
		Handler.addLowPriorityEntity(chaserShip);
		Handler.addLowPriorityEntity(chasedShip);
		// place ships
		chaserShip.setX(-200);
		chaserShip.setY(150);
		chasedShip.setX(WIDTH - 430);
		chasedShip.setY(110);
		phase 				 = new Text    ("Current Phase: "+currentPhase.toString(),true,150,150,this);
 				
		ds 					 = new DistanceSystem(500, chaserShip.getDistanceToEnd(), chasedShip.getDistanceToEnd());
		overlay 			 = new ImageHandler  (0,0,"res/drawnUi2.png",true,EntityID.UI);
		//Set buttons 
		chaserShip.setCaptain(player.getPlayerCrew());
		List<Button> temp = chaserShip.getPhaseLeaderButtons(this);
		
		sl = new ScrollableList(temp, 0, Main.HEIGHT - (temp.size() * 50), 50, (temp.size() * 50), 50, 50, true);
		// Animation anim = new Animation("res/octiodLazer1Anim.png", 97, 21, 4,
		// 2,1,3,3,9, 12, 670,
		// 347,1f,-1,true,AdjustmentID.None,Collections.<Animation>emptyList());

		ui = new BattleUI(this, chaserShip, chasedShip);

		keyIn = new BattleKeyInput(this);
		mouseIn = new BattleMouseInput(handler);
		chaserHealthbar = new ImageHandler(2, 2, "res/healthseg.png", true, 1, 1, EntityID.UI);
		chasedHealthbar = new ImageHandler(797, 2, "res/healthseg.png", true, 1, 1, EntityID.UI);
		// graphButton = new Button(150, 350, 150, 150, ButtonID.Graph, true, new
		// Graph(MathFunctions.square,0,0,150,150), this);
		// graphButton.setDraggable(true);
		Handler.addLowPriorityEntity(overlay);
		Handler.addLowPriorityEntity(chaserHealthbar);
		Handler.addLowPriorityEntity(chasedHealthbar);

		this.addKeyListener(keyIn);
		this.addMouseListener(mouseIn);
		this.addMouseMotionListener(mouseIn);
		this.addMouseWheelListener(mouseIn);
	}

	public void selectRoom(String room) {
		selectedRoom = room;
	}

	private void nextTurn() {
		// if its the chased's turn, next phase
		if (isPlayersTurn != playerIsChaser || currentPhase == BattlePhases.Final) {
			currentPhasePointer++;
		}
		// loop phases if necessary
		if (currentPhasePointer >= BattlePhases.phases.length) {
			currentPhasePointer = 0;
			// turn = chaser, which gets flipped l8r
			isPlayersTurn = playerIsChaser;
		}
		// set phase ,next turn
		currentPhase = BattlePhases.phases[currentPhasePointer];
		isPlayersTurn = !isPlayersTurn;
		// Turn Textual output
		String phase = "";
		if (currentPhase == BattlePhases.WeaponsButton) {
			phase = "Weapons Button";
		}
		if (currentPhase == BattlePhases.WeaponsClick) {
			phase = "Weapons Click";
		}
		if (currentPhase == BattlePhases.Engine) {
			phase = "Engine";
		}
		if (currentPhase == BattlePhases.Final) {
			phase = "final";
		}
		String turn = isPlayersTurn ? "Players" : "Enemys";
		System.out.println("\nIts the " + phase + " phase and the " + turn + " turn");
	}

	public void tick() {
		if (!isPaused()) {
			super.tick();
			// AI turns

			if(!isPlayersTurn) {
				if(currentPhase == BattlePhases.WeaponsButton) {
					if(playerIsChaser) {
						chasedWeaponChoice.add(chasedShip.getBackWeapons().get(0));
					}
					else {
						chaserWeaponChoice.add(chaserShip.getBackWeapons().get(0));
					}
					System.out.println("Enemy Weapon Reveal");
				} else if (currentPhase == BattlePhases.WeaponsClick) {
					if (playerIsChaser) {
						chasedShotLocation = new Point(350, 350);
					} else {
						chaserShotLocation = new Point(1000, 450);
					}
					System.out.println("Enemy Click (not revealed)");
				} else if (currentPhase == BattlePhases.Engine) {
					if (playerIsChaser) {
						chasedThrusterChoice = chasedShip.getThrusters().get(0);
					} else {
						chaserThrusterChoice = chaserShip.getThrusters().get(0);
					}
					System.out.println("Enemy Engine Reveal");
				} else if (currentPhase == BattlePhases.Cockpit) {
					if (playerIsChaser) {
						chasedSpeedChoice = 200;
						chasedShip.setEndSpeed(chasedSpeedChoice);
					}
				}
				nextTurn();
			}

			// Final phase aka do stuff
			if (currentPhase == BattlePhases.Final) {
				System.out.println("Weapons Firing");
				// apply speed setting parameters for end of turn based on remain power or
				// assigned power
				chaserShip.generate();
				chasedShip.generate();
				chaserShip.accelerate();
				chasedShip.accelerate();
				// chaserShip.setSpeed(300);
				// chasedShip.setSpeed(200);
				// chaserShip.accelerate();
				// chasedShip.accelerate();
				ds.calculateDistances(chaserShip, chasedShip);
				UseWeapon(chasedShip, chaserShip, chasedWeaponChoice, chasedShotLocation);
				UseWeapon(chaserShip, chasedShip, chaserWeaponChoice, chaserShotLocation);
				chasedWeaponChoice.clear();
				chaserWeaponChoice.clear();
				currentPhase = BattlePhases.Wait;
			}

			if (currentPhase == BattlePhases.Wait) {
				if (!ProjectileAnimation.areAnimationsRunning()) {
					currentPhase = BattlePhases.Final;
					chaserShip.getGenerator().getEfficiencyGraph().setGraphPoint(0);
					chasedShip.getGenerator().getEfficiencyGraph().setGraphPoint(0);
					currentPhase = BattlePhases.phases[0];
				}
			}

			if (chaserShip != null && chasedShip != null) {
				if (currentPhase != null && phase != null) {
//					fuel.setText("Fuel: " + Integer.toString(chaserShip.getResource("fuel")) + " Power: "
//							+ Integer.toString(chaserShip.getResource("power")));
					phase.setText("Current Phase: " + currentPhase.toString());
				}
				loadingScreen.setVisible(false);
				chaserShip.tickLayers();
				chasedShip.tickLayers();
			}
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



	public void UseWeapon(Ship primary, Ship secondary,List<Weapon> weapons,Point shot){
		for(Weapon weapon:weapons) {
			new ProjectileAnimation(primary, secondary, 200, true, weapon.fire(), shot,weapon.getSlot()).start();
		}
	}
	
	public void update(ButtonID ID,int index,int button) {// this gets notified by the click function inside button		
		Ship playerShip = playerIsChaser ? chaserShip : chasedShip;	
		if(button == MouseEvent.BUTTON1) {
			if(ID == ButtonID.BattleWeaponsChoice){
				
				List<Weapon> weapons = playerShip.getFrontWeapons();
				Room         room    = playerShip.getWeaponRoom();
				
				BattleUI.generateActionList(weapons, room);
			}

			if(ID == ButtonID.Go) {
				if(isPlayersTurn && currentPhase == BattlePhases.Go) {
					nextTurn();
				}
			}
			
			if(ID == ButtonID.Back) {
				BattleUI.back();
			}
			


			if (ID == ButtonID.BattleCockpitChoice) {
				if (isPlayersTurn && currentPhase == BattlePhases.Cockpit) {
					
					BattleUI.generateActionList(playerShip.getThrusters().get(0), playerShip.getCockpit(), false);
				}
			}
			
			if(ID == ButtonID.WeaponInfo) {
				BattleUI.generateWeaponInfo(playerShip.getFrontWeapons().get(index));
			}

			if (ID == ButtonID.BattleThrusterChoice) {

				Thruster thruster = playerShip.getThrusters().get(index);
				Room room = playerShip.getGeneratorRoom();
				//
				BattleUI.generateActionList(thruster, room, true);

				if (isPlayersTurn && currentPhase == BattlePhases.Cockpit) {

					if (playerIsChaser) {
						chaserThrusterChoice = playerShip.getThrusters().get(index);
					} else {
						chasedThrusterChoice = playerShip.getThrusters().get(index);
					}

					System.out.println("Player Engine choice made");
					
				}
			}
			if (ID == ButtonID.BattleThrusterActionChoice) {
				if (isPlayersTurn && currentPhase == BattlePhases.CockpitActions) {
					if(playerIsChaser) {
						chaserSpeedChoice = playerShip.getThrusters().get(0).getSpeeds().get(index);
						
					}
					else {
						chasedSpeedChoice = playerShip.getThrusters().get(0).getSpeeds().get(index);
					}
					playerShip.setEndSpeed(playerShip.getThrusters().get(0).getSpeeds().get(index));
					nextTurn();
				}
			}
				if(ID == ButtonID.EndPhase) {
					if(isPlayersTurn && currentPhase==BattlePhases.WeaponActions ) {
						// intalise variables
						List<Weapon> weapons = playerIsChaser ? playerShip.getFrontWeapons():playerShip.getFrontWeapons();
						
						List<CrewAction> actions  = new ArrayList<CrewAction>();;
						List<CrewAction> refinedActions;
						CrewAction action;
						List<CrewAction> actionsNeeded;
						HashMap<CrewActionID,List<CrewAction>> actionMap;

						boolean complete;
						
						// check which weapons are fired
						for(int i = 0;i<weapons.size();i++) {
							
							// initalise variables
							refinedActions = new ArrayList<CrewAction>();
							actionMap = new HashMap<>();
							actions = weapons.get(i).getActions();
						
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
							
							// sort actions based on the amount of actions needed
							Collections.sort(refinedActions);
							for(int j = 0; j<refinedActions.size(); j++) {
								action = refinedActions.get(j);
								
								// if this actions doesnt need anymore actions to be completed
								if(refinedActions.get(j).getActionsNeeded().size()==0) {
									actions = actionMap.get(action.getActionType());
									
									//remove it from every other actionNeeded
									if(actions!=null) {
										for(int k = 0;k<actions.size();k++) {
											actions.get(k).removeActionNeeded(action);
										}
									}									
									// do action
									weapons.get(i).doAction(action, this);
									action.resetActions();
									action.removeActor();
								}
							}
						}									
					}	
					nextTurn();
				}
			
			if (ID == ButtonID.Crew) {
				BattleUI.generateRoomButtons(chaserShip.getPhaseLeaders().get(index), TooltipSelectionID.Room);
			}
			if (ID == ButtonID.Graph) {
				graphButton.getGraph().setPoint(MouseInput.mousePosition);
			}
			if (ID == ButtonID.BattleGeneratorChoice) {
				if (isPlayersTurn && currentPhase == BattlePhases.GeneratorActions) {
					playerShip.getGenerator().doAction(playerShip.getGenerator().getActions().get(index), this);
					nextTurn();
				}
			}
			if (button == MouseEvent.BUTTON3) {
				if (ID == ButtonID.Crew) {
					BattleUI.generateRoomButtons(chaserShip.getAllCrew().get(index), TooltipSelectionID.Stats);
				}
			}
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
		if (currentPhase == BattlePhases.WeaponsClick) {
			if (playerIsChaser) {
				chaserShotLocation = new Point(x, y);
			} else {
				chasedShotLocation = new Point(x, y);
			}
			nextTurn();
			return true;
		}
		return false;
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
		return isPlayersTurn;
	}

	public void setPlayersTurn(boolean isPlayersTurn) {
		this.isPlayersTurn = isPlayersTurn;
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

	

}
