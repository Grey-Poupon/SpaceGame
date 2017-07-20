package com.project;

import java.awt.event.MouseEvent;

public class BattleMouseInput extends MouseInput {
	@Override
	public void mouseClicked(MouseEvent arg0) {
		setpointClicked(arg0.getX(), arg0.getY());
		BattleUI.checkClick(arg0.getX(), arg0.getY());
		//System.out.println(Integer.toString(arg0.getX())+" "+Integer.toString(arg0.getY()));
	}
	
}
