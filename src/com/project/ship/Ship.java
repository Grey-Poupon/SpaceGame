package com.project.ship;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.project.Crew;
import com.project.DamageType;
import com.project.EntityID;
import com.project.Handleable;
import com.project.ImageHandler;
import com.project.LayeredImage;
import com.project.ResourceLoader;
import com.project.battle.BattleScreen;
import com.project.button.Button;
import com.project.button.ButtonID;
import com.project.ship.rooms.Cockpit;
import com.project.ship.rooms.WeaponsRoom;
import com.project.weapons.Weapon;

public class Ship implements Handleable{
	private LayeredImage lImage;
	private int health;
	private int maxHealth;
	private int currHealth;
	private int speed = 200;
	private int distanceToEnd = 250; // for distance system
	private int speedChange;
	private int power = 0;
	private Engine engine;
	private ArrayList<String> flavourTexts = new ArrayList<String>();
	private Generator generator;
	private List<Room> rooms = new ArrayList<Room>();
	private List<Slot>	   shipBackSlots           = new ArrayList<Slot>();
	private List<Slot>	   shipFrontSlots           = new ArrayList<Slot>();
	private List<Crew>     crew                = new ArrayList<Crew>();
	private List<Handleable> sprites = new ArrayList<Handleable>();
	private Sensor sensor;
	private boolean isChased;
	
	
	public boolean isChased() {
		return isChased;
	}

	
	public void setChased(boolean isChased) {
		this.isChased = isChased;
	}

	Map<DamageType,Double> damageTakenModifier = new HashMap<DamageType,Double>();
	Map<DamageType,Double> damageDealtModifier = new HashMap<DamageType,Double>();	
	
	public Ship(int x,int y,float z, float zPerLayer, String path, boolean visible, EntityID id, int health,float scale, boolean generateCrew,boolean isChased){
		lImage = new LayeredImage(x, y, path,  z,zPerLayer,scale);
		this.currHealth = this.maxHealth = health;
		shipBackSlots= lImage.getBackSlots();
		shipFrontSlots= lImage.getFrontSlots();
		this.isChased = isChased;
		setSensors();
		generateFlavourText();
		
		
		Weapon defaultWeapon = ResourceLoader.getShipWeapon("default");

		for(DamageType dmg : DamageType.values()){
			damageTakenModifier.put(dmg, 1d);
			damageDealtModifier.put(dmg, 1d);
		}
		for(int i=0;i<shipBackSlots.size();i++){
			shipBackSlots.get(i).setSlotItem(defaultWeapon);

		}
		for(int i=0;i<shipFrontSlots.size();i++){
			shipFrontSlots.get(i).setSlotItem(defaultWeapon);

		}
		generateRooms();
		sortSprites();
		if(generateCrew){
			for(int i =0; i<10;i++) {
				Crew crewie = Crew.generateRandomCrew();
				crewie.setRoomIn(rooms.get(Crew.getRand().nextInt(rooms.size())));
				crew.add(crewie);
			}
		}	
	}
	
	
	

	private void generateRooms() {
		rooms.add(new WeaponsRoom(getFrontWeapons(),getBackWeapons(),isChased, new Point(50,50)));
		rooms.add(new Cockpit(new Point(70,70)));
	}
	
	private void sortSprites() {
		List<Handleable> temp = new ArrayList<Handleable>();
		List<Handleable> all = new ArrayList<Handleable>();
		all.addAll(shipBackSlots);
		all.addAll(lImage.getLayers());
		all.addAll(shipFrontSlots);
		int index = 0;
		float largestZ = all.get(0).getZ();
		while(all.size()>0) {
			largestZ = all.get(0).getZ();
			for(int i =0;i<all.size();i++) {
				if(all.get(i).getZ()>=largestZ) {
					index = i;
					largestZ = all.get(i).getZ();
				}
			}

			temp.add(all.get(index));
			all.remove(index);
		}
		sprites = temp;
	}




	private void generateFlavourText() {
		flavourTexts.add("THIS IS A TEST, To see whether or not text wrapping works it would sure be lovely if it did, though i wouldn't feel too bad as this is the first time ive tried it and you can't be too hard on yourself yanno, it reminds me of the time i was out fishing with my uncle and he accidentally fell into the lake and couldn't swim and i stared as he body turned from manic thrashing to stillness");
		
	}
//	public Ship(int x,int y,float z, float zPerLayer, String path, boolean visible, EntityID id, int health,float scale,Weapon[] frontWeapons,Weapon[] backWeapons,Engine engine,Generator generator,List<Crew> crew){
//		lImage = new LayeredImage(x, y, path,  z,zPerLayer,scale);
//		shipBackSlots= lImage.getBackSlots();
//		shipFrontSlots= lImage.getFrontSlots();
//		setSensors();
//		generateFlavourText();
//		Weapon defaultWeapon = ResourceLoader.getShipWeapon("default");
//		for(int i=0;i<shipBackSlots.size();i++){
//			shipBackSlots.get(i).setSlotItem(defaultWeapon);
//
//		}
//		for(int i=0;i<shipFrontSlots.size();i++){
//			shipFrontSlots.get(i).setSlotItem(defaultWeapon);
//
//		}
//		generateRooms();
//		this.health = health;
//		for(DamageType dmg : DamageType.values()){
//			damageTakenModifier.put(dmg, 1d);
//			damageDealtModifier.put(dmg, 1d);
//		}
//		this.generator=generator;
//		this.engine=engine;
//		this.crew = crew;
//	}
	
	
	public void useEngine(int amountOfFuel) {
		double thrust = engine.getThrust(amountOfFuel);
		if(thrust<0) {System.out.println("your engine exploded, gg boyo");}//engine exploded
		speed = (int) thrust;
	}
	
	public void generatePower(int amountOfFuel) {
		double power = generator.getPower(amountOfFuel);
		if(power<0) {System.out.println("your generator exploded, gg boyo");}//engine exploded
		power = (int) power;
	}
	public List<Button> getCrewButtons(BattleScreen bs){
		List<Button> buttons = new ArrayList<Button>();
		for(int i = 0;i<crew.size();i++) {
			buttons.add(new Button(0, 0, 120, 120, ButtonID.Crew, i,true,crew.get(i).getName(),"sevensegies",Font.PLAIN,30,Color.WHITE,crew.get(i).getPortrait(), bs));
		}
		return buttons;
	}
	
	public int roomDamage(int x, int y) {
		int dmg =0;
		if(isShipClicked(x, y)) {
			Room room = getClosestRoom(x, y);
			if(room==null) {return 0;}
			int distanceToRoom = (int) (Math.pow(room.getLocation().getX(),2) + Math.pow(room.getLocation().getY(),2));
			if (distanceToRoom < room.getDamagableRadius()) {
				dmg  = (1/distanceToRoom) * room.getDamageMod();
			}
		}
		return dmg;
	}
	public Room getClosestRoom(int x, int y) {
		if(rooms.size()<1) {return null;}
		Room closestRoom = rooms.get(0);
		Point closest = closestRoom.getLocation();
		for(Room r :rooms) {
			Point n=r.getLocation();
			if(closest.x+closest.y>n.x+n.y && closest.x*closest.y > n.x*n.y) {
				closest = n;
				closestRoom=r;
			}
		}
		return closestRoom;
	}
	public List<Room> getRooms() {
		return rooms;
	}




	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}


	public Sensor getSensor() {
		return sensor;
	}




	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}




	public boolean isShipClicked(int x, int y) {
		for(ImageHandler i : lImage.getLayers()) {
			if(i.isClicked(x, y)) {
				return true;
			}
		}
		return false;
	}
	public int getLayerClicked(int x, int y) {
		for(int i = 0; i<lImage.getLayers().size();i++) {
			if(lImage.getLayers().get(i).isClicked(x, y)) {
				return i;
			}
		}
		return -1;
	}
	
	
	public List<Crew> getCrew() {
		return crew;
	}
	public int getHealth() {
		return health;
	}
	public int getMaxHealth(){
		return maxHealth;
	}
	public int getCurrHealth(){
		return currHealth;
	}
	public void takeDamage(int damage, DamageType type){
		this.currHealth-=damage*damageTakenModifier.get(type);
	}
	public Double getDamageTakenModifier(DamageType dt) {
		return damageTakenModifier.get(dt);
	}
	public void setDamageTakenModifier(DamageType dt, Double double1) {
		damageDealtModifier.put(dt, Double.valueOf(double1));
	}
	public Double getDamageDealtModifier(DamageType dt) {
		return damageDealtModifier.get(dt);
	}
	public void setDamageDealtModifier(DamageType dt, int mod) {
		damageDealtModifier.put(dt, Double.valueOf(mod));
	}
	
	public LayeredImage getLayeredImage() {
		return lImage;
	}
	public void tickLayers() {
		lImage.tick();
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speedChange = this.speed - speed;
		this.speed = speed;
	}
	public int getDistanceToEnd() {
		return distanceToEnd;
	}
	public void increaseDistanceToEnd(int distanceToEnd) {
		this.distanceToEnd += distanceToEnd;
	}
	public int getSpeedChange() {
		return speedChange;
	}
	public Slot getBackSlot(int position) {
		return lImage.getBackSlots().get(position);
	}
	public void setSensors() {
		sensor = new Sensor(0.8f);
	}
	public Sensor getSensors() {
		return sensor;
	}
	public ArrayList<String> getFlavourTexts(){
		return flavourTexts;
	}
	public void destruct() {
		lImage.destruct();
	}
	public void setX(int x) {
		lImage.setX(x);
	}
	public void setY(int y) {
		lImage.setY(y);
	}




	public void render(Graphics g) {
		
		for(int i = 0; i<sprites.size();i++) {
			if(sprites.get(i) instanceof Slot) {
				((Slot) sprites.get(i)).getSlotItem().render(g,(Slot) sprites.get(i));
			}
			if(sprites.get(i) instanceof ImageHandler) {
				sprites.get(i).render(g);
			}
		}
		
		
//		for(int i =0;i<lImage.getNoLayers();i++) {
//			lImage.getLayers().get(i).render(g);
//		}
//		
//		for(int i=0;i<shipBackSlots.size();i++) {
//			shipBackSlots.get(i).getSlotItem().render(g,shipBackSlots.get(i));
//		}
//		for(int i=0;i<shipFrontSlots.size();i++) {
//			shipFrontSlots.get(i).getSlotItem().render(g,shipFrontSlots.get(i));
//		}
		
		
//		
//		Graphics2D g2d = (Graphics2D)g.create();
//		for(int i=0;i<shipBackSlots.size();i++) {
//			Slot s = shipBackSlots.get(i);
////			System.out.print(s.getX());
////			System.out.print(",");
////			System.out.print(s.getY());
////			System.out.println();
//			g2d.setColor(Color.white);
//			g2d.fillRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
//		}
//		for(int i=0;i<shipFrontSlots.size();i++) {
//			Slot s = shipFrontSlots.get(i);
////			System.out.print(s.getX());
////			System.out.print(",");
////			System.out.print(s.getY());
////			System.out.println();
//			g2d.setColor(Color.white);
//			g2d.fillRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
//		}
	}




	@Override
	public void tick() {
		lImage.tick();
		for(int i = 0;i<shipBackSlots.size();i++) {
			shipBackSlots.get(i).setX(lImage.getBackSlots().get(i).getX());
			shipBackSlots.get(i).setY(lImage.getBackSlots().get(i).getY());
			shipBackSlots.get(i).setWidth(lImage.getBackSlots().get(i).getWidth());
			shipBackSlots.get(i).setHeight(lImage.getBackSlots().get(i).getHeight());
		}
		for(int i = 0;i<shipFrontSlots.size();i++) {
			shipFrontSlots.get(i).setX(lImage.getFrontSlots().get(i).getX());
			shipFrontSlots.get(i).setY(lImage.getFrontSlots().get(i).getY());
			shipFrontSlots.get(i).setWidth(lImage.getFrontSlots().get(i).getWidth());
			shipFrontSlots.get(i).setHeight(lImage.getFrontSlots().get(i).getHeight());
		}
	}




	public int getPower() {
		return power;
	}




	public void setPower(int power) {
		this.power = power;
	}


	public List<Slot> getShipBackSlots() {
		return shipBackSlots;
	}


	public void setShipBackSlots(List<Slot> shipBackSlots) {
		this.shipBackSlots = shipBackSlots;
	}


	public List<Slot> getShipFrontSlots() {
		return shipFrontSlots;
	}


	public void setShipFrontSlots(List<Slot> shipFrontSlots) {
		this.shipFrontSlots = shipFrontSlots;
	}




	public List<Weapon> getFrontWeapons() {
		List<Weapon> weapons = new ArrayList<Weapon>();
		for(int i = 0; i<shipFrontSlots.size();i++) {
			if(shipFrontSlots.get(i).getSlotItem() instanceof Weapon) {
				weapons.add((Weapon)shipFrontSlots.get(i).getSlotItem());
				System.out.println("SSASD");
			}
		}
		return weapons;
	}
	public List<Weapon> getBackWeapons() {
		List<Weapon> weapons = new ArrayList<Weapon>();
		for(int i = 0; i<shipBackSlots.size();i++) {
			if(shipBackSlots.get(i).getSlotItem() instanceof Weapon) {
				weapons.add((Weapon)shipBackSlots.get(i).getSlotItem());
				System.out.println("SAD");
			}
		}
		return weapons;
	}


	public Slot getFrontSlot(int position) {
		return shipFrontSlots.get(position);
		
	}


	public List<Handleable> getSprites() {
		return sprites;
	}


	public void setSprites(List<Handleable> sprites) {
		this.sprites = sprites;
	}


	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return 0;
	}


	




	
}
