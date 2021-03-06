package com.project.phase2;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
		if(!(p2.inShop||p2.menuOpen)) {
			movePlayerShip();
		}else if(p2.inShop&&!p2.menuOpen){
			mouseShopClick(arg0);
		}
		else if(p2.menuOpen) {
			
		}
		
	}
	
	public void mouseMoved(MouseEvent arg0) {
		super.mouseMoved(arg0);
		if(p2.inShop) {
			mouseShop(arg0);
		}
		else {
			Phase2.getP().getCurrentMap().highlightTile(mousePosition);
		}
	}
	
	private void mouseShop(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void mouseShopClick(MouseEvent arg0) {
		ArrayList<Rectangle> buttons;
		if(p2.shop.talking) {
			buttons = p2.shop.TalkButtons;
		}
		else {
			buttons = p2.shop.ShopButtons;
		}
		
		for(int i = 0; i<buttons.size();i++) {
			if(buttons.get(i).contains(mousePosition)) {
				p2.shop.mouseInteract(p2,i);
				
			}
		}
	}
	

}
