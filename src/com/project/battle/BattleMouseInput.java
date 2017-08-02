package com.project.battle;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import com.project.Handler;
import com.project.MouseInput;
import com.project.ScrollableList;
import com.project.UI;

public class BattleMouseInput extends MouseInput {
	private ScrollableList sl;
	public BattleMouseInput(Handler hans,ScrollableList sl) {
		super(hans);
		this.sl=sl;
	}
	public void mouseClicked(MouseEvent arg0) {
		setpointClicked(arg0.getX(), arg0.getY());
		hans.checkButtons(arg0.getX(), arg0.getY());
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		sl.scroll(arg0.getWheelRotation());
	}

	
}
