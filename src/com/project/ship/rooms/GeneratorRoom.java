package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.project.ResourceLoader;
import com.project.ship.Room;

public class GeneratorRoom extends Room{

	public GeneratorRoom(Point location) {
		super(location);
		// TODO Auto-generated constructor stub
	}
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/generatorRoomIcon.png");
	}

}
