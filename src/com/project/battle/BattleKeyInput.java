package com.project.battle;

import java.awt.event.KeyEvent;

import com.project.KeyInput;


public class BattleKeyInput extends KeyInput {
	private BattleScreen bs;
	public BattleKeyInput(BattleScreen bs) {
		this.bs = bs;
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsPressed(key);
		if (key == KeyEvent.VK_Q){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(0));}
		if (key == KeyEvent.VK_W){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(1));}
		if (key == KeyEvent.VK_E){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(2));}
		if (key == KeyEvent.VK_E){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(2));}
		if (key == KeyEvent.VK_P){bs.setPaused(!bs.isPaused());}
	}
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsReleased(key);
	}
}
