package com.project.ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import com.project.Crew;
import com.project.StatID;

public abstract class Room {
	private int health;
	private int maxHealth;
	private String roomName;
	private Point location;
	private int damageableRadius;
	private int damageMod;
	private int efficiency;
	private Crew roomLeader;
	private int size = 9;
	ArrayList<Crew> crewInRoom = new ArrayList<Crew>();
	ArrayList<Ellipse2D> sensorSpheres = new ArrayList<>();
	private int sensorSphereRadius = 50;
	
	public Room(String name, int health) {
		this.roomName = name;
		this.health = health;
		this.maxHealth = health;
	}
	public Room() {

	}
	
	public void generateSensorSpheres(Sensor sensor) {
		if(sensorSpheres.size()>0) {return;}
		Random rand = new Random();		
		
		for(int i= 0;i<30;i++) {
			
			 
			int index = 0;
			if(rand.nextBoolean()&&i!=0) {
				if(rand.nextBoolean()) {
				index =rand.nextInt(i);
				}
				else {
					index = i-1;
				}
			}
			
			if(sensorSpheres.size()==0) {
				sensorSpheres.add(new Ellipse2D.Double(location.x,location.y,(0.5+rand.nextFloat()/2f)*sensorSphereRadius*(1-sensor.getEfficiency()),(0.5+rand.nextFloat()/2f)*sensorSphereRadius*(1-sensor.getEfficiency())));
			
			}
			else {
				sensorSpheres.add(new Ellipse2D.Double(sensorSpheres.get(index).getCenterX()+((rand.nextInt(3)-2)*sensorSpheres.get(index).getWidth()*(0.5+rand.nextFloat()/2f)/2f),sensorSpheres.get(index).getCenterY()+(rand.nextInt(3)-2)*sensorSpheres.get(index).getHeight()*(0.5+rand.nextFloat()/2f)/2f,(0.5+rand.nextFloat()/2f)*sensorSphereRadius*(1-sensor.getEfficiency()),(0.5+rand.nextFloat()/2f)*sensorSphereRadius*(1-sensor.getEfficiency())));	
			}
		}
	}
	
	public BufferedImage getIcon() {
		return null;
	}
	
	public void addCrew(Crew crew) {
		crew.setRoomIn(this);
		crewInRoom.add(crew);
	}
	public void removeCrew(Crew crew) {
		crewInRoom.remove(crew);
	}
	public void moveCrew(Crew crew, Room moveTo) {
		this.removeCrew(crew);
		moveTo.addCrew(crew);
	}
	
	public ArrayList<Crew> getCrewInRoom() {
		return crewInRoom;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public Point getLocation() {
		return location;
	}
	public int getDamageableRadius() {
		return damageableRadius;
	}
	public void setDamageableRadius(int damagableRadius) {
		this.damageableRadius = damagableRadius;
	}
	public int getDamageMod() {
		return damageMod;
	}
	public void setDamageMod(int damageMod) {
		this.damageMod = damageMod;
	}
	public int getEfficiency() {
		return efficiency;
	}
	public void setEfficiency(int efficiency) {
		this.efficiency = efficiency;
	}
	public Crew getRoomLeader() {
		return roomLeader;
	}
	public void setRoomLeader(Crew roomLeader) {
		roomLeader.setRoomLeader(true, this);
		this.roomLeader = roomLeader;
		this.crewInRoom.add(roomLeader);
	}
	public void UseRoom() {
		
	}
	public ArrayList<String> getOptions() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setStatModifiers(StatID dictKey, float modifier) {
		// TODO Auto-generated method stub
		
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getSensorSphereRadius() {
		return sensorSphereRadius;
	}
	public void setSensorSphereRadius(int sensorSphereRadius) {
		this.sensorSphereRadius = sensorSphereRadius;
	}
	public void renderSensorSpheres(Graphics g,Ship ship) {
		Graphics2D g2d  = (Graphics2D)g.create();
		g2d.setColor(new Color(1f,0f,0f,0.5f));
		Area composite = new Area();
		g2d.setClip(ship.getClip());
		for(int i =0; i<sensorSpheres.size();i++) {
			Ellipse2D e = sensorSpheres.get(i);
			Ellipse2D e1 = new Ellipse2D.Float((float) (ship.getLayeredImage().getLargestLayer().getxCoordinate()+ship.getLayeredImage().getLargestLayer().getxScale()*(e.getCenterX()+getSize()/2-e.getWidth())),(int)(ship.getLayeredImage().getLargestLayer().getyCoordinate()+ship.getLayeredImage().getLargestLayer().getyScale()*(e.getCenterY()+getSize()/2-e.getHeight())),(int)(ship.getLayeredImage().getLargestLayer().getxScale()*e.getWidth()),(int)(ship.getLayeredImage().getLargestLayer().getyScale()*e.getHeight()));
			Area temp = new Area(e1);
			composite.add(temp);
		}
		g2d.fill(composite);
	}
	public String getRoomName() {
		return roomName;
	}	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public int takeDamage(int damage) {
		health-=damage;
		//
		//Do Roll table stuff here
		//
		return 1;
	}
	public int getHealth() {
		return health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
}
