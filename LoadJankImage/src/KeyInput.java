package LoadJankImage.src;




import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class KeyInput extends KeyAdapter {
	public static ArrayList<Character> keys; 
	public KeyInput(){
		keys = new ArrayList<Character>();
	}
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
		if(!keys.contains(e.getKeyChar())) {
			keys.add(e.getKeyChar());
		}
		
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsReleased(key);
		if(keys.contains(e.getKeyChar())) {
			keys.remove(keys.indexOf(e.getKeyChar()));
		}
	}
	public void tick() {
		if(keys.contains('z')) {
			LayeredImage.cameraZ+=0.1;
			}
		if(keys.contains('x')){LayeredImage.cameraZ-=0.1;}
		if(keys.contains('.')){System.out.println(LayeredImage.cameraZ);}
		if(keys.contains(',')){}			
		if(keys.contains('a')){LayeredImage.cameraX-=0.5;}
		if(keys.contains('d')){LayeredImage.cameraX+=0.5;}
		if(keys.contains('w')){LayeredImage.cameraY-=0.5;}
		if(keys.contains('s')){LayeredImage.cameraY+=0.5;}
	}
	
}
