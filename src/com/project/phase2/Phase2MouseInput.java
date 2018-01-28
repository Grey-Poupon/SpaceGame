package com.project.phase2;

import java.awt.event.MouseEvent;

import com.project.Handler;
import com.project.MouseInput;

public class Phase2MouseInput extends MouseInput{
	
	public Map map;
	public MapShip ship;
	
	
	public Phase2MouseInput(Handler hans, Map map,MapShip ship) {
		super(hans);
		this.map= map;
		this.ship = ship;
	}
	
	public void movePlayerShip() {
		for(int x = 0; x<map.width;x++) {
			for(int y= 0; y<map.height;y++) {
				MapTile t = map.hexes.get(x).get(y);
				if(t.hex.contains(mousePosition)) {
					ship.moveTile(t);
				}
			}
		}
	}
	
	public void mouseClicked(MouseEvent arg0){
		movePlayerShip();
	}
	
	
	

}
