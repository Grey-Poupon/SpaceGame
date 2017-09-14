package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.project.ResourceLoader;
import com.project.ship.Generator;
import com.project.ship.Room;

public class GeneratorRoom extends Room{

	private Generator generator;
	
	
	public GeneratorRoom(Point location,Generator generator) {
		super(location);
		this.generator = generator;
		
		// TODO Auto-generated constructor stub
	}
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/generatorRoomIcon.png");
	}
	public Generator getGenerator() {
		return generator;
	}
	public void setGenerator(Generator generator) {
		this.generator = generator;
	}

}
