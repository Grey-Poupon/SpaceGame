

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {
	
	
	
	
	public KeyInput(){
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_W) {}
		if(key==KeyEvent.VK_S){}
		if(key==KeyEvent.VK_A){}
		if(key==KeyEvent.VK_D){}		
		
		if(key==KeyEvent.VK_ESCAPE) System.exit(1);
		
	}
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_W){}
		if(key==KeyEvent.VK_S){}
		if(key==KeyEvent.VK_A){}
		if(key==KeyEvent.VK_D){}
			
			

	}
}
