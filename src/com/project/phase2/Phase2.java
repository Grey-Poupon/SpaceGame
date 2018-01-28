package com.project.phase2;

import java.awt.Graphics;
import java.awt.Polygon;

import com.project.Handler;
import com.project.KeyInput;
import com.project.Main;
import com.project.MouseInput;
import com.project.Phase;
import com.project.battle.BattleScreen;

public class Phase2 implements Phase{
	/**
	 * 
	 */
	private static Phase2 p;
	public static Main main;
	private static final long serialVersionUID = 1L;
	private Handler handler = new Handler();
	private KeyInput keyIn;
	private MouseInput mouseIn;
	private Map currentMap;
	public MapShip ship;
	
	
	public Phase2(Main main) {
		this.main = main;
		
		
		setP(this);
		ship = new MapShip(new MapTile(new Polygon()),true);
		
//		Map map = new Map();
		Map map = Map.generateRandomMap();
		
		
		map.randomlyPlaceShip(ship);
		
		handler.addLowPriorityEntity(map);
		//handler.addLowPriorityEntity(ship.objImg);
		currentMap = map;
		keyIn   = new Phase2KeyInput();
		mouseIn = new Phase2MouseInput(handler,this);
		
		main.addKeyListener(keyIn);
		main.addMouseListener(mouseIn);
		main.addMouseMotionListener(mouseIn);
		main.addMouseWheelListener(mouseIn);
	}
	
	public void tick() {
		handler.tick(null);
		
	}
	public static void battle() {
		main.setPhase(new BattleScreen(main));
		
	}
	public void render(Graphics g) {
		handler.render(g);
		
	}

	public Map getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(Map currentMap) {
		handler.entitiesLowPriority.remove(this.currentMap);
		this.currentMap = currentMap;
		ship = new MapShip(new MapTile(new Polygon()),true);
		this.currentMap.randomlyPlaceShip(ship);
		//System.out.println(this.currentMap.hexes.get(0).get(0).objects.get(0));
		
		handler.addLowPriorityEntity(this.currentMap);
	}

	public static Phase2 getP() {
		return p;
	}

	public static void setP(Phase2 p) {
		Phase2.p = p;
	}
	
	
	
}
