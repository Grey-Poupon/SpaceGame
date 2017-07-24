package com.project;

import java.awt.event.MouseEvent;

public class BattleMouseInput extends MouseInput {
	public BattleMouseInput(UI ui) {
		super(ui);
	}
	public void mouseClicked(MouseEvent arg0) {
		setpointClicked(arg0.getX(), arg0.getY());
		ui.checkButtons(arg0.getX(), arg0.getY());
	}
	

	
}
