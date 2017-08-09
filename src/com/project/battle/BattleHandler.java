package com.project.battle;

import com.project.Handler;
import com.project.Ship;

public class BattleHandler extends Handler {
	private BattleScreen bs;
	public BattleHandler(BattleScreen bs) {
		this.bs=bs;
		
	}
	@Override
	public void checkShip(int x , int y) {
		bs.checkClick(x,y);
	}
	@Override
	public void checkClick(int x, int y) {
		checkButtons(x, y);
		checkShip(x,y);
	}
}
