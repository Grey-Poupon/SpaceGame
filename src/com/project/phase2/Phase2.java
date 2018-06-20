package com.project.phase2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

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
	public MapPlayerShip ship;
	public boolean inShop = false;
	public ShopMenu shop;
	public Map map;
	public static boolean menuOpen = false;
	
	public Phase2(Main main) {
		Phase2.main = main;
		setP(this);
		ship = new MapPlayerShip(new MapTile(new Polygon(),-80,-70,null),main.player.getShip());
		map = Map.generateRandomMap();
		
		map.randomlyPlaceShip(ship);
		
		handler.addLowPriorityEntity(map);
		//handler.addLowPriorityEntity(ship.objImg);
		currentMap = map;
		keyIn   = new Phase2KeyInput();
		mouseIn = new Phase2MouseInput(handler,this);

	}
	
	public void tick() {
		handler.tick(null);
	}
	public static void battle(MapShip chaser,MapShip chasee) {
		main.setPhase(new BattleScreen(main,chaser.getShip(),chasee.getShip(),chaser.isPlayerShip));
	}
	
	public void render(Graphics g) {
		handler.render(g);
		if(menuOpen) {
			Graphics2D g2d = (Graphics2D)g.create();
			g.setColor(Color.BLACK);
			
			g.fillRect(50, 50, 500, 500);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setFont(new Font("Sevensegies",Font.PLAIN,36));
			g2d.setColor(Color.WHITE);
			g2d.drawString("ITEMS: ",70,60);
			for(int i = 0; i<ship.getShip().getInventory().size();i++) {
				ShopItem t = ship.getShip().getInventory().get(i);
				g2d.drawString(t.getName(),70,60+(1+i)*(40));
			}
			g2d.drawString("Quests",250,60);
			for(int i= 0; i<ship.getQuests().size();i++) {
				Quest t = ship.getQuests().get(i);
				g2d.drawString(t.getName(),250,60+(1+i)*(40));
			}
		}
		
	}

	public Map getCurrentMap() {
		return currentMap;
	}

	public void setCurrentMap(Map currentMap) {
		System.out.println("MMEEPLE STREEET");
		handler.entitiesLowPriority.remove(this.currentMap);
		this.currentMap = currentMap;
		ship = new MapPlayerShip(new MapTile(new Polygon(),-80,-70,null),main.player.getShip());
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
	
	public static void toggleMenu() {
		menuOpen = !menuOpen;
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
