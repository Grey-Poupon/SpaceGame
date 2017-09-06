package com.project;

import java.util.List;

import com.project.ship.Ship;

public interface Actionable {

	public List<CrewAction> getActions();
	public void doAction(int index,Ship ship);
	public String getName();
}
