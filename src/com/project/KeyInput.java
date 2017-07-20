package com.project;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	public KeyInput(){}
	// can use inherited functions here for any control that are shared in and out of battle 
	
	protected void sharedControlsPressed(int key){
		if(key==KeyEvent.VK_ESCAPE) System.exit(1);
	}
	protected void sharedControlsReleased(int key){
		if(key==KeyEvent.VK_ESCAPE) System.exit(1);
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsPressed(key);
		if(key==KeyEvent.VK_Q) {}
		if(key==KeyEvent.VK_S){}
		if(key==KeyEvent.VK_A){}
		if(key==KeyEvent.VK_D){}			
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsReleased(key);
		if(key==KeyEvent.VK_W){}
		if(key==KeyEvent.VK_S){}
		if(key==KeyEvent.VK_A){}
		if(key==KeyEvent.VK_D){}
			
			

	}
}
