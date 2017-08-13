package com.project.battle;

import java.util.ArrayList;
import java.util.List;

import com.project.Handler;
import com.project.button.Button;

public class BattleHandler extends Handler {
	private BattleScreen bs;

	
	public BattleHandler(BattleScreen bs) {
		this.bs=bs;
		
	}
	public boolean checkShip(int x , int y) {
		return bs.checkClick(x,y);
	}
	@Override
	public boolean checkClick(int x, int y) {
		if(checkButtons(x, y)){ return true;}
		return checkShip(x,y);
	}
	
}
