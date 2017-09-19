package com.project.ship.rooms;

import java.awt.image.BufferedImage;

import com.project.ResourceLoader;
import com.project.ship.Room;
import com.project.ship.Sensor;

public class SensorRoom extends Room {

	private Sensor sensor;
	
	public SensorRoom(Sensor sensor) {
		this.sensor= sensor;
		setRoomName("Sensors");
	}
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/sensorRoomIcon.png");
	}
	
	public Sensor getSensor() {
		return sensor;
	}
	
	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

}
