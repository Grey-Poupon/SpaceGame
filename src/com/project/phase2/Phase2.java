package com.project.phase2;

import com.project.Handler;
import com.project.KeyInput;
import com.project.Main;
import com.project.MouseInput;

public class Phase2 extends Main{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Handler handler = new Handler();
	private KeyInput keyIn;
	private MouseInput mouseIn;
	
	
	public Phase2() {
		
		Map map = new Map();
		MapShip ship = new MapShip(true);
		map.generateHexMap(ship);
		handler.addHighPriorityEntity(map);
		keyIn   = new Phase2KeyInput();
		mouseIn = new Phase2MouseInput(handler,map,ship);
		
		this.addKeyListener(keyIn);
		this.addMouseListener(mouseIn);
		this.addMouseMotionListener(mouseIn);
		this.addMouseWheelListener(mouseIn);
	}
	
	public void tick() {
		handler.tick(null);
	}
	
	
	
}
