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
	
	
	public Phase2(Main main) {
		this.main = main;
		p = this;
		Map map = new Map();
		MapShip ship = new MapShip(new MapTile(new Polygon()),true);
		map.generateHexMap(ship);
		handler.addLowPriorityEntity(map);
		keyIn   = new Phase2KeyInput();
		mouseIn = new Phase2MouseInput(handler,map,ship);
		
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
	
	
	
}
