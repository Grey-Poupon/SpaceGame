package com.project;

import java.awt.event.KeyEvent;


public class BattleKeyInput extends KeyInput {
	private BattleUI ui;
	public BattleKeyInput(BattleUI ui) {
		this.ui = ui;
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsPressed(key);
		if (key == KeyEvent.VK_Q){ui.changeTootlipSelection("q");}
		if (key == KeyEvent.VK_W){ui.changeTootlipSelection("w");}
		if (key == KeyEvent.VK_E){ui.changeTootlipSelection("e");}
		if (key == KeyEvent.VK_R){ui.changeTootlipSelection("r");}

	}
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsReleased(key);
	}
}
