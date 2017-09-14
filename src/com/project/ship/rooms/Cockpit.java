package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.project.Crew;
import com.project.CrewAction;
import com.project.ResourceLoader;
import com.project.ship.Room;

public class Cockpit extends Room{
	private List<CrewAction> manoeuvres = new ArrayList<CrewAction>();
	public Cockpit(Point location,List<CrewAction> manoeuvres) {
		super(location);
		this.manoeuvres = manoeuvres;
	}
	
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/cockpitIcon.png");
	}

	public List<CrewAction> getManoeuvres() {
		return manoeuvres;
	}

}
