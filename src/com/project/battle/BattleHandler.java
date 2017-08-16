package com.project.battle;

import com.project.Handler;

public class BattleHandler extends Handler {
	private BattleScreen bs;

	
	public BattleHandler(BattleScreen bs) {
		this.bs=bs;
		
	}
	public boolean checkShip(int x , int y) {
		return bs.checkClick(x,y);
	}
	@Override
	public boolean checkClick(int x, int y, int button) {
		if(checkButtons(x, y,button)){ return true;}
		return checkShip(x,y);
	}
	public void updateMouse(int x,int y) {
		super.updateMouse(x, y);
		if(checkShip(x,y)) {
			mousePointer.setImg("res/attackMousePointer.png");
		}
		else {mousePointer.setImg("res/mousePointer.png");}
		
	}
	
}
