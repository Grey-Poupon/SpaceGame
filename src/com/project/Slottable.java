package com.project;

import java.awt.Graphics;

import com.project.ship.Slot;

public interface Slottable {

	void render(Graphics g, Slot slot);
	public Slot getSlot();
	public void setSlot(Slot slot);
	Animation getSlotItemBody();
}
