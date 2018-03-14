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
	 ** 
	 **/
	private static Phase2 p;
	public static Main main;
	private static final long serialVersionUID = 1L;
	private Handler handler = new Handler();
	private KeyInput keyIn;
	private MouseInput mouseIn;
	private Map currentMap;
	public MapShip ship;
	public boolean inShop = false;
	public ShopMenu shop;
	public Map map;
	
	public Phase2(Main main) {
		Phase2.main = main;
		setP(this);
		ship = new MapShip(new MapTile(new Polygon(),-80,-70,null),true,main.player.getShip());
		map = Map.generateRandomMap();
		
		map.randomlyPlaceShip(ship);
		
		handler.addLowPriorityEntity(map);
		//handler.addLowPriorityEntity(ship.objImg);
		currentMap = map;
		keyIn   = new Phase2KeyInput();
		mouseIn = new Phase2MouseInput(handler,this);
		addListeners(main);

	}
	
	public void tick() {
		handler.tick(null);
	}
	public static void battle(MapShip chaser,MapShip chasee) {
		main.setPhase(new BattleScreen(main,chaser.getShip(),chasee.getShip(),chaser.isPlayerShip));
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
		ship = new MapShip(new MapTile(new Polygon(),-80,-70,null),true,main.player.getShip());
		this.currentMap.randomlyPlaceShip(ship);
		handler.addLowPriorityEntity(this.currentMap);
	}

	public static Phase2 getP() {
		return p;
	}
	
	public static void setShop(ShopMenu shop) {
		Phase2.p.shop= shop;
		Phase2.p.handler.addLowPriorityEntity(shop);
		Phase2.p.inShop = true;
	}
	
	public void leaveShop() {
		inShop = false;
		handler.entitiesLowPriority.remove(Phase2.p.shop);
	}
	

	public static void setP(Phase2 p) {
		Phase2.p = p;
	}

	@Override
	public MouseInput getMouseInput() {
		// TODO Auto-generated method stub
		return mouseIn;
	}

	@Override
	public KeyInput getKeyInput() {
		// TODO Auto-generated method stub
		return keyIn;
	}

	@Override
	public void addListeners(Main main) {
		main.addKeyListener(keyIn);
		main.addMouseListener(mouseIn);
		main.addMouseMotionListener(mouseIn);
		main.addMouseWheelListener(mouseIn);
		
	}
	
}
