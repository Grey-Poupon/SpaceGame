package com.project;

import java.awt.event.KeyEvent;

public class BattleKeyInput extends KeyInput {
	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsPressed(key);
		if (key == KeyEvent.VK_Q){
			BattleUI.changeTootlipSelection("q");
			System.out.println("1");
		}
		if (key == KeyEvent.VK_W){}
		if (key == KeyEvent.VK_E){}
		if (key == KeyEvent.VK_R){}

	}
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsReleased(key);
	}
}
