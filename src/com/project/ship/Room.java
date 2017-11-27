package com.project.ship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.project.Actionable;
import com.project.Crew;
import com.project.CrewAction;
import com.project.ImageHandler;
import com.project.RoomSize;
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
	private int actionHealth;
	private int noOfActions;
	private int damageStack = 0; 
	private RoomSize size = RoomSize.Medium;
	ArrayList<Crew> crewInRoom = new ArrayList<Crew>();
	List<CrewAction> allActions = new ArrayList<CrewAction>();
	ArrayList<Ellipse2D> sensorSpheres = new ArrayList<>();
	private int sensorSphereRadius = 50;
	
	public Room(String name, int ActionHealth, int noOfActions, RoomSize size) {
		
		this.roomName     = name;
		this.actionHealth = ActionHealth;
		this.noOfActions  = noOfActions;
		this.maxHealth    = noOfActions;
		this.health       = maxHealth;
		this.size		  = size;
		
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
		int sizeOfCrew = 9;
		Random rand = new Random();
		crew.setRoomIn(this);
		crewInRoom.add(crew);
		crew.setLocationInRoom(new Point(rand.nextInt(size.getLength()-sizeOfCrew),rand.nextInt(size.getLength()-sizeOfCrew)));
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
	public RoomSize getSize() {
		return size;
	}
	public void setSize(RoomSize size) {
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
			Ellipse2D e1 = new Ellipse2D.Float((float) (ship.getLayeredImage().getLargestLayer().getxCoordinate()+ship.getLayeredImage().getLargestLayer().getxScale()*(e.getCenterX()+getSize().getLength()/2-e.getWidth())),(int)(ship.getLayeredImage().getLargestLayer().getyCoordinate()+ship.getLayeredImage().getLargestLayer().getyScale()*(e.getCenterY()+getSize().getMaxPopulation()/2-e.getHeight())),(int)(ship.getLayeredImage().getLargestLayer().getxScale()*e.getWidth()),(int)(ship.getLayeredImage().getLargestLayer().getyScale()*e.getHeight()));
			Area temp = new Area(e1);
			composite.add(temp);
		}
		g2d.fill(composite);
	}
	
	public void renderCrewOnShip(Graphics g, Ship ship) {
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setColor(Color.blue);
		for(int i = 0;i<crewInRoom.size();i++) {
			
			ImageHandler largestLayer = ship.getLayeredImage().getLargestLayer();
			g2d.fillOval(
/*X*/				(int) (largestLayer.getxCoordinate()+(crewInRoom.get(i).getLocationInRoom().x+largestLayer.getxScale()*location.x)),
/*Y*/				(int) (largestLayer.getyCoordinate() +(crewInRoom.get(i).getLocationInRoom().y+largestLayer.getyScale()*location.y)),
/*Width*/			9,
/*Height*/			9);
		}
	}
	
	public void tick() {
		Random rand =new Random();
		int lengthRelToShip = size.getLength();
		int sizeOfCrew = 9;
		for(int i =0;i<crewInRoom.size();i++) {
			if(rand.nextInt(40) == 1) {
				Crew c = crewInRoom.get(i);
				int x = rand.nextInt(RoomSize.getCrewStep()*2)-RoomSize.getCrewStep();
				int y = rand.nextInt(RoomSize.getCrewStep()*2)-RoomSize.getCrewStep();
				if(c.getLocationInRoom().x+x+sizeOfCrew<size.getLength() && c.getLocationInRoom().x+x>0&&c.getLocationInRoom().y+y+sizeOfCrew<size.getLength() && c.getLocationInRoom().y+y>0)
				c.incLocationInRoom(new Point(x,y));
			}
		}
	}
	
	public String getRoomName() {
		return roomName;
	}	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	/**Deals damage returns roll table roll**/
	protected int takeDamage(int damage) {
		damageStack+=damage;
		while(damageStack>=actionHealth){
			damageStack-=actionHealth;
			if(ActionsLeft()){
				CrewAction actionToBeBroken = getLeastDependantAction();
				actionToBeBroken.setBroken(true);
				health--;
			}
			else{
				// room.makeLikeAdamsWillToLiveAndDie();
				break;
			}
			
			
		}
		//
		//Do Roll table stuff here
		//
		return 1;
	}

	protected abstract CrewAction getLeastDependantAction() ;
	protected abstract boolean ActionsLeft() ;
	
	public int getHealth() {
		return health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
}
