package com.project.phase2;

import java.awt.event.MouseEvent;

import com.project.Handler;
import com.project.MouseInput;

public class Phase2MouseInput extends MouseInput{
	

	public Phase2 p2;
	
	public Phase2MouseInput(Handler hans,Phase2 p2) {
		super(hans);
		this.p2 = p2;

	}
	
	public void movePlayerShip() {
		Map map = Phase2.getP().getCurrentMap();
		for(int x = 0; x<map.width;x++) {
			for(int y= 0; y<map.height;y++) {
				MapTile t = map.hexes.get(y).get(x);
				if(t.hex.contains(mousePosition)) {
					p2.ship.moveTile(t);
				}
			}
		}
	}
	
	public void mouseClicked(MouseEvent arg0){
		movePlayerShip();
	}
	
	public void mouseMoved(MouseEvent arg0) {
		Map map = Phase2.getP().getCurrentMap();
		super.mouseMoved(arg0);
		map.highlightTile(mousePosition);
	}
	
	
	

}
