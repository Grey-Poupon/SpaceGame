package com.project.phase2;

import java.awt.event.KeyEvent;

import com.project.KeyInput;

public class Phase2KeyInput extends KeyInput{
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyChar()=='p') {
			Phase2.toggleMenu();
		}
	}

}
