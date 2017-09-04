package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.project.ResourceLoader;
import com.project.ship.Room;

public class Cockpit extends Room{

	public Cockpit(Point location) {
		super(location);
	}
	
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/cockpitIcon.png");
	}

}
