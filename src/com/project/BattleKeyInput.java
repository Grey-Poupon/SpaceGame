package com.project;

import java.awt.event.KeyEvent;


public class BattleKeyInput extends KeyInput {
	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsPressed(key);
		if (key == KeyEvent.VK_Q){BattleUI.changeTootlipSelection("q");}
		if (key == KeyEvent.VK_W){BattleUI.changeTootlipSelection("w");}
		if (key == KeyEvent.VK_E){BattleUI.changeTootlipSelection("e");}
		if (key == KeyEvent.VK_R){BattleUI.changeTootlipSelection("r");}

	}
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsReleased(key);
	}
}
