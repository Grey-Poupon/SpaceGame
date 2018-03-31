package com.project.battle;

import java.awt.event.KeyEvent;

import com.project.KeyInput;
import com.project.LayeredImage;
import com.project.TooltipSelectionID;


public class BattleKeyInput extends KeyInput {
	private BattleScreen bs;
	public BattleKeyInput(BattleScreen bs) {
		this.bs = bs;
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsPressed(key);
		if (key == KeyEvent.VK_Q){BattleUI.generateRoomButtons(bs.chaserShip.getAllCrew().get(0),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_W){BattleUI.generateRoomButtons(bs.chaserShip.getAllCrew().get(1),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_E){BattleUI.generateRoomButtons(bs.chaserShip.getAllCrew().get(2),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_R){BattleUI.generateRoomButtons(bs.chaserShip.getAllCrew().get(3),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_T){BattleUI.generateRoomButtons(bs.chaserShip.getAllCrew().get(4),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_Y){BattleUI.generateRoomButtons(bs.chaserShip.getAllCrew().get(5),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_U){BattleUI.generateRoomButtons(bs.chaserShip.getAllCrew().get(6),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_I){BattleUI.generateRoomButtons(bs.chaserShip.getAllCrew().get(7),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_O){BattleUI.generateRoomButtons(bs.chaserShip.getAllCrew().get(8),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_P){BattleUI.generateRoomButtons(bs.chaserShip.getAllCrew().get(9),TooltipSelectionID.Room);}
		
		if (key == KeyEvent.VK_M) {bs.endCombat();}
		
		//if (key == KeyEvent.VK_P){bs.setPaused(!bs.isPaused());}
		
		if(key==KeyEvent.VK_UP){LayeredImage.setCameraY(LayeredImage.getCameraY()    + 1f);}
		if(key==KeyEvent.VK_DOWN){LayeredImage.setCameraY(LayeredImage.getCameraY()  - 1f);}
		if(key==KeyEvent.VK_LEFT){LayeredImage.setCameraX(LayeredImage.getCameraX()  + 1f);}
		if(key==KeyEvent.VK_RIGHT){LayeredImage.setCameraX(LayeredImage.getCameraX() - 1f);}
		
		
	}
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsReleased(key);
	}
}
