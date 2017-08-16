package com.project.battle;

import java.awt.event.KeyEvent;

import com.project.KeyInput;
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
		if (key == KeyEvent.VK_Q){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(0),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_W){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(1),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_E){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(2),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_R){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(3),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_T){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(4),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_Y){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(5),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_U){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(6),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_I){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(7),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_O){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(8),TooltipSelectionID.Room);}
		if (key == KeyEvent.VK_P){BattleUI.changeTootlipSelection(bs.playerShip.getCrew().get(9),TooltipSelectionID.Room);}

		//if (key == KeyEvent.VK_P){bs.setPaused(!bs.isPaused());}
	}
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		sharedControlsReleased(key);
	}
}
