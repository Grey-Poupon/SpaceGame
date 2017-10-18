package com.project.ship.rooms;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.project.Crew;
import com.project.ResourceLoader;
import com.project.Recreation.RecreationalItem;
import com.project.ship.Room;

public class StaffRoom extends Room {
	List<RecreationalItem> items = new ArrayList<>();
	public BufferedImage getIcon() {
		return ResourceLoader.getImage("res/roomIcons/staffRoomIcon.png");
	}
	public StaffRoom(List<RecreationalItem> items,String name, int health) {
		super(name,health);
		this.items = items;
		
		// TODO Auto-generated constructor stub
	}

	public List<RecreationalItem> getItems() {
		return items;
	}
	public void setItems(List<RecreationalItem> items) {
		this.items = items;
	}

}
