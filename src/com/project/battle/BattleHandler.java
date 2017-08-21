package com.project.battle;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.project.Handler;
import com.project.ImageHandler;
import com.project.ResourceLoader;

public class BattleHandler extends Handler {
	private BattleScreen bs;

	
	public BattleHandler(BattleScreen bs) {
		this.bs=bs;
		
	}
	public boolean clickShip(int x , int y) {
		if (bs.checkShipClick(x,y)) {
			return bs.clickShip(x,y);
		}
		return false;
	}
	
	public int getLayerClicked(int x,int y) {
		return bs.getLayerClicked(x,y);
	}
	
	public boolean checkClick(int x, int y, int button) {
		if(checkButtons(x, y,button)){ return true;}
		return clickShip(x,y);
	}
	public void updateMouse(int x,int y) {
		super.updateMouse(x, y);

		if(bs.checkShipClick(x, y)) {		
			mousePointer.setImg("res/attackMousePointer.png");
		}
		else {mousePointer.setImg("res/mousePointer.png");}
		
	}
	
}
