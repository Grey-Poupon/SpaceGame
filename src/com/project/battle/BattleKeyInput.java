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
		if (key == KeyEvent.VK_Q){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(0),"room");}
		if (key == KeyEvent.VK_W){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(1),"room");}
		if (key == KeyEvent.VK_E){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(2),"room");}
		if (key == KeyEvent.VK_R){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(3),"room");}
		if (key == KeyEvent.VK_T){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(4),"room");}
		if (key == KeyEvent.VK_Y){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(5),"room");}
		if (key == KeyEvent.VK_U){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(6),"room");}
		if (key == KeyEvent.VK_I){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(7),"room");}
		if (key == KeyEvent.VK_O){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(8),"room");}
		if (key == KeyEvent.VK_P){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(9),"room");}

		//if (key == KeyEvent.VK_P){bs.setPaused(!bs.isPaused());}
	}
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsReleased(key);
	}
}
