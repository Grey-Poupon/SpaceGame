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
	private Point location;
	private int damagableRadius;
	private int damageMod;
	private int efficiency;
	private Crew roomLeader;
	private int size = 15;
	ArrayList<Crew> crewInRoom = new ArrayList<Crew>();
	ArrayList<Ellipse2D> sensorSpheres = new ArrayList<>();
	private int sensorSphereRadius = 50;
	
	public Room(Point location) {
		this.location =location;
	}
	public Room() {
		
	}
	
	public void generateSensorSpheres(Sensor sensor) {
		if(sensorSpheres.size()>0) {return;}
		Random rand = new Random();
		
//		for(int i= 0;i<20*(1-sensor.getEfficiency());i++) {
		for(int i= 0;i<20;i++) {
			int num =rand.nextInt(4);
			 
			int yShift = 1;
			int xShift = 1;
			int index = i-1;
			
			if(rand.nextBoolean()) {
				yShift*=-1;
			}
			if(rand.nextBoolean()) {
				xShift*=-1;
			}

			if(rand.nextBoolean()&&i!=0) {
				index = rand.nextInt(i);
			}
			if(rand.nextBoolean()) {
				index =0;
			}
			if(sensorSpheres.size()==0) {
				sensorSpheres.add(new Ellipse2D.Double(location.x,location.y,(0.5+rand.nextFloat()/2f)*sensorSphereRadius*(1-sensor.getEfficiency()),(0.5+rand.nextFloat()/2f)*sensorSphereRadius*(1-sensor.getEfficiency())));
			}
			else {
				sensorSpheres.add(new Ellipse2D.Double(sensorSpheres.get(index).getCenterX()+(xShift*sensorSpheres.get(index).getWidth()*(0.5+rand.nextFloat()/2f)/2f),sensorSpheres.get(index).getCenterY()+yShift*sensorSpheres.get(index).getHeight()*(0.5+rand.nextFloat()/2f)/2f,(0.5+rand.nextFloat()/2f)*sensorSphereRadius*(1-sensor.getEfficiency()),(0.5+rand.nextFloat()/2f)*sensorSphereRadius*(1-sensor.getEfficiency())));
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
	public void removeCrew(Crew crew,Room staffRoom) {
		crew.setRoomIn(staffRoom);
		crewInRoom.remove(crew);
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
		roomLeader.setRoomIn(this);
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
	

}
