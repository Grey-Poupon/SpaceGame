package com.project.ship;

import java.awt.Point;

public class Room {
private Point Location;
private int damagableRadius;
private int damageMod;
public Point getLocation() {
	return Location;
}
public int getDamagableRadius() {
	return damagableRadius;
}
public void setDamagableRadius(int damagableRadius) {
	this.damagableRadius = damagableRadius;
}
public int getDamageMod() {
	return damageMod;
}
public void setDamageMod(int damageMod) {
	this.damageMod = damageMod;
}

}
